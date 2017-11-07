package kscript

import kotlin.system.exitProcess

/**
 * @author Holger Brandl
 */

/**
 * Just used internally to prevent [stopIfNot] to quit the process when running in unit-test mode.
 * It throw an IllegalArgumentException instead.
 */
internal var isTestMode = false


/** Similar to require but without a stacktrace which makes it more suited for CLIs. */
fun stopIfNot(value: Boolean, lazyMessage: () -> Any) {
    if (value) return

    val msg = "[ERROR] " + lazyMessage().toString()

    if (isTestMode) throw  IllegalArgumentException(msg)

    System.err.println(msg)
    exitProcess(1)
}