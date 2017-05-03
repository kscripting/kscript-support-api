package kscript

import java.io.BufferedReader
import java.io.File
import java.io.FileReader


// for top-level vs member extensions see https://kotlinlang.org/docs/reference/extensions.html#scope-of-extensions
//object KscriptHelpers {}

val stdin = generateSequence() { readLine() }


/** Read lines stdin or a file argument. Example 'argLines(1).map { it+"foo"}'. could be used either with "-" or file argument. */
fun argLines(arg: String, stdinNames: List<String> = listOf("-", "stdin")): Sequence<String> {
    if (stdinNames.contains(arg)) return stdin

    val inputFile = File(arg)

    stopIfNot(inputFile.canRead()) { "Can not read from '${arg}'" }

    // todo we don't close the buffer with this approach
    //    BufferedReader(FileReader(inputFile )).use { return it }
    return BufferedReader(FileReader(inputFile)).lineSequence()
}

fun argMap(arg: String, stdinNames: List<String> = listOf("-", "stdin"), trafo: (String) -> String) =
        argLines(arg, stdinNames).map { trafo(it) }.print()

fun argFilter(arg: String, stdinNames: List<String> = listOf("-", "stdin"), trafo: (String) -> Boolean) =
        argLines(arg, stdinNames).filter { trafo(it) }.print()


fun Sequence<String>.print() = forEach { println(it) }
