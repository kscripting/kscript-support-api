package kscript.util

import kscript.text.resolveArgFile

/**
 * @author Holger Brandl
 */

abstract class OneLinerContext(args: Array<String>) {

    val arg by lazy { resolveArgFile(args) }
    //    val stdin by lazy {  kscript.support.getStdin }

    init {
        apply(arg)
    }

    abstract fun apply(lines: Sequence<String>)
}

