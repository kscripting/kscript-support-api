package kscript.examples

import kscript.experimental.OneLinerContext
import kscript.text.add
import kscript.text.print
import kscript.text.split

/**
 * One-liner kscript example. To ease development simply extend [OneLinerContext] as shown, which will provide
 * the same context as `kscript` when running in single line mode.
 *
 * @author Holger Brandl
 */

fun main(args: Array<String>) {

    object : OneLinerContext(args) {

        override fun apply(lines: Sequence<String>) {
            lines.split().drop(1).filter { it[9] == "UA" }.add { it[3] + ":" + it[3] }.print()
            // kscript 'lines.split().drop(1).filter { it[9] == "UA" }.add { it[3] + ":" + it[3] }.print()'
        }
    }
}