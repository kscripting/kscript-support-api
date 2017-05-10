package kscript.text

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

/** awk-like convenience wrapper around columns->map->print */
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


// todo  add krangl ColNames interface here

