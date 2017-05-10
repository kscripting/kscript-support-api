package kscript.experimental

import kscript.resolveArgFile

/**
 * @author Holger Brandl
 */

abstract class OneLinerContext(args: Array<String>) {

    val arg by lazy { resolveArgFile(args) }
    //    val stdin by lazy {  kscript.stdin }

    init {
        apply(arg)
    }

    abstract fun apply(lines: Sequence<String>)
}

