package kscript.text

import kscript.stopIfNot

/**
 * Utility methods to allow for awk-like data processing using kscript.
 *
 * The general usage pattern is to start with a `Sequence<String>` that is typically provided as `lines` by [kscript.resolveArgFile], and to end with one of the `print` extension methods provided below.
 *
 * In one-liners `lines` is implicitly added by `kscript` and the alle elements from `kscript.*` are imported. This allows for constructs such as
 * ```
 * kscript 'lines.filter{ it.contains("foo") }.print()' some_file.txt
 * kscript 'lines.filter{ it.contains("foo") }.print()'
 * cat some_file.txt  | kscript 'lines.filter{ it.contains("foo") }.print()'
 *
 * ```
 *
 * @author Holger Brandl
 */


// for top-level vs member extensions see https://kotlinlang.org/docs/reference/extensions.html#scope-of-extensions


/** For sake of readability we refer to a list of strings as a row here. */
typealias Row = List<String>

//typealias RowSequence = Sequence<Row>
//class RowSequence(input: Sequence<String>){
//}


/** Splits the lines of an input stream into [Row]s.
 *
 * @param separator The used separator character which defaults to tabs.
 */
fun Sequence<String>.split(separator: String = "\t"): Sequence<Row> {
    return this.map { it.split(separator) }
}

/** awk-like convenience wrapper around split->map->join->print */
fun Sequence<String>.awk(separator: String = "\t", rule: (Row) -> String) = split(separator).map { rule(it) }.print()


fun Sequence<Row>.map(vararg rules: (Row) -> String): Sequence<Row> {
    return map { splitLine -> rules.map { it(splitLine) } }
}

/** Adds a new column to a row. */
fun Sequence<Row>.add(rule: (Row) -> String): Sequence<Row> {
    return map { row -> listOf(*row.toTypedArray(), rule(row)) }
}


//@Deprecated("use kscript.support.awk() instead")
//fun Sequence<String>.splitMap(vararg rules: (Row) -> String, separator: String = "\t", joinWith: String = separator) {
//    map { it.split(separator).let { splitLine -> rules.map { it(splitLine) } } }.print()
//}


fun Sequence<Row>.join(separator: String = "\t") = map { it.joinToString(separator) }

/** Joins rows with the provided `separator` and print them to `stdout`. */
fun Sequence<Row>.print(separator: String = "\t") = join(separator).print()


fun List<Row>.print() = forEach { println(it) }


//
// Column Select
//


/** Internal representations for column selection indices. Usually not use directly but rather via [with] and [without].
 */
abstract class ColSelect(val indices: Array<Int> = emptyArray()) {
    abstract fun and(column: Int): ColSelect
    abstract fun and(range: IntRange): ColSelect
}

class PosSelect(arrayOf: Array<Int>) : ColSelect(arrayOf) {
    override fun and(column: Int) = PosSelect(arrayOf(*indices, column))
    override fun and(range: IntRange) = PosSelect(arrayOf(*indices, *range.toList().toTypedArray()))
}

class NegSelect(arrayOf: Array<Int>) : ColSelect(arrayOf) {
    override fun and(column: Int) = NegSelect(arrayOf(*indices, column))
    override fun and(range: IntRange) = NegSelect(arrayOf(*indices, *range.toList().toTypedArray()))
}

/** Starts building a column selection index. Both positive and negative indices are supported. */
fun with(index: Int) = PosSelect(arrayOf(index))

fun with(range: IntRange) = PosSelect(range.toList().toTypedArray())
fun without(index: Int) = NegSelect(arrayOf(index))
fun without(range: IntRange) = NegSelect(range.toList().toTypedArray())


private fun retainColumn(selectIndex: ColSelect, colIndex: Int): Boolean {
    val indexInRange = selectIndex.indices.contains(colIndex)

    return if (selectIndex is PosSelect) indexInRange else !indexInRange
}

/**
 * Select or remove columns by providing an index-vector. Positive selections are done with [with] and  negative selections with [without]. Both methods implement a [builder][https://en.wikipedia.org/wiki/Builder_pattern] to construct more complex selectors.
 */
fun Sequence<Row>.select(vararg colIndices: Int): Sequence<Row> {
    val isPositive = colIndices.all { it > 0 }
    stopIfNot(isPositive || colIndices.all { it < 0 }) {
        " Can not mix positive and negative selections"
    }

    val selector = if (isPositive) PosSelect(arrayOf(*colIndices.toTypedArray())) else NegSelect(arrayOf(*colIndices.toTypedArray()))

    return select(selector)
}

fun Sequence<Row>.select(columns: ColSelect): Sequence<Row> {
    // more efficient but does not allow to change the order
    //    return map { it.filterIndexed { index, _ -> retainColumn(columns, index + 1) } }

    return if (columns is PosSelect) {
        // positive selection
        map { row -> columns.indices.map { row[it - 1] } }
    } else {
        // negative selection
        map { it.filterIndexed { index, _ -> !columns.indices.contains(index - 1) } }
    }
}


// http://www.thegeekstuff.com/2010/01/8-powerful-awk-built-in-variables-fs-ofs-rs-ors-nr-nf-filename-fnr/?ref=binfind.com/web
//NF Example: Number of Fields in a record
//val LAST = Integer.MIN_VALUE

// todo  add krangl ColNames interface here

