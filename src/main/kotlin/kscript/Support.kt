package kscript

import kotlin.system.exitProcess

/**
 * @author Holger Brandl
 */


fun foo() = "1"

public inline fun stopIfNot(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        System.err.println("[ERROR] " + lazyMessage().toString())
        exitProcess(1)
    }
}