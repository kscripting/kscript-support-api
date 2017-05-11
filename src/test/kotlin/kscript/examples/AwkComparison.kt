package kscript.examples

import kscript.experimental.OneLinerContext
import kscript.text.*

/**
 * @author Holger Brandl
 */

val args = arrayOf("flights.txt")

object AwkExample : OneLinerContext(args) {
    override fun apply(lines: Sequence<String>) {
        lines.split().map({ it[1] }, { it[2] }).print()

        lines.split().filter { it[3].matches("UA".toRegex()) }.print()

        lines.drop(1).split().filter { it[3].matches("UA".toRegex()) }.print()


        // positive selection
        lines.split().select(with(1..3).and(3)).print()
        // negative selection
        lines.split().select(without(7).and(3..4)).print()

        lines.awk { it[1] + it[2] }

        lines.awk { it[1] + it[2] }
        lines.awk { it[3] }

        // http://stackoverflow.com/questions/15361632/delete-a-column-with-awk-or-sed
        lines.split().map { it.toMutableList().apply { removeAt(3) } }.print()



        //        http@ //tuxgraphics.org/~guido/scripts/awk-one-liner.html
        //        Print the next two (i=2) lines after the line matching regexp:
        //        awk '/regexp/{i=2;next;}{if(i){i--; print;}}' file.txt


        fun <T> List<T>.sliding(windowSize: Int): List<List<T>> {
            return this.dropLast(windowSize - 1).mapIndexed { i, s -> this.subList(i, i + windowSize) }
        }

        val regex = "a[bc]+".toRegex()

        resolveArgFile(args).
                toList().sliding(4).
                filter { it[0].matches(regex) }.
                flatten().print()



        // number lines (from http://tuxgraphics.org/~guido/scripts/awk-one-liner.html)
        //        awk '{print FNR "\t" $0}'
        lines.mapIndexed { num, line -> num.toString() + " " + line }.print()

        //        Remove duplicate consecutive lines (uniq):
        //        awk 'a !~ $0{print}; {a=$0}'


        //        Delete trailing white space (spaces, tabs)
        //        awk '{sub(/[ \t]*$/, "");print}' file.txt
        lines.map { it.trim() }.print()

        //        Count lines (wc -l):
        //        awk 'END{print NR}'
        println(lines.fold(0) { cur, _ -> cur + 1 })
        println(lines.mapIndexed { i, _ -> i }.last())
        // don't
        println(lines.toList().size)



        //        Print the lines from a file starting at the line matching "start" until the line matching "stop":
        //        awk '/start/,/stop/' file.txt

        lines.dropWhile { it.startsWith("foo") }.takeWhile { it.startsWith("bar") }.print()


        //        Print the last field in each line:
        //        awk -F: '{ print $NF }' file.txt
        lines.split(":").map { it[it.size - 1] }.print()



        // Prints Record(line) number, and number of fields in that record
        // awk '{print NR,"->",NF}' file.txt
        lines.split().mapIndexed { index, row -> index.toString() + " -> " + row.size }.print()


        val arg by lazy { resolveArgFile(args) }
        arg.filter { true }.print()
    }
}
//file:///Users/brandl/Desktop/awk_cheatsheets.pdf


