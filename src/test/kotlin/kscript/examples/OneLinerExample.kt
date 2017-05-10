package kscript.examples

import kscript.add
import kscript.split
import kscript.experimental.OneLinerContext
import kscript.print

/**
 * One-liner kscript example. To ease devlopment simply extend [OneLinerContext] as shown, which will provide
 * the same context as `kscript` when running in single line mode.
 *
 * @author Holger Brandl
 */

fun main(args: Array<String>) {

    object : OneLinerContext(args) {

        override fun apply(lines: Sequence<String>) {
            lines.split().filter { it[3] == "UA" }.add { it[3] + ":" + it[3] }.print()
        }
    }
}