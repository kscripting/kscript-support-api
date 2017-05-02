package kscript

import java.io.File
import kotlin.system.exitProcess


// todo maybe  it should also become part of the kscript code repository once ready

// for top-level vs member extensions see https://kotlinlang.org/docs/reference/extensions.html#scope-of-extensions
//object KscriptHelpers {}

val stdin = generateSequence() { readLine() }

fun Sequence<String>.print() = forEach { println(it) }


fun processStdin(trafo: (String) -> String) {
    generateSequence() { readLine() }.map {
        println(trafo(it))
    }
}

@Deprecated("use mapLines instead")
fun File.processLines(trafo: (String) -> String) {
    useLines {
        it.map { println(trafo(it)) }
    }
}

fun <T> File.mapLines(trafo: (String) -> T) {
    return useLines {
        it.map { trafo(it) }
    }
}

fun String.processLines(trafo: (String) -> String) {
    split("\n").map { println(trafo(it)) }
}


public inline fun stopIfNot(value: Boolean, lazyMessage: () -> Any): Unit {
    if (!value) {
        System.err.println("ERROR: " + lazyMessage().toString())
        exitProcess(1)
    }
}