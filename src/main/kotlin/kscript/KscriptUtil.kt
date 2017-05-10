package kscript

import kotlin.system.exitProcess

/**
 * @author Holger Brandl
 */

/** Similar to require but without a stacktrace. */
inline fun stopIfNot(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        System.err.println("[ERROR] " + lazyMessage().toString())
        exitProcess(1)
    }
}