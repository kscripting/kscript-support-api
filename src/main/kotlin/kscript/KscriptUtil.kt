package kscript

import kotlin.system.exitProcess

/**
 * @author Holger Brandl
 */

/** Similar to require but without a stacktrace which makes it more suited for CLIs. */
inline fun stopIfNot(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        System.err.println("[ERROR] " + lazyMessage().toString())
        exitProcess(1)
    }
}