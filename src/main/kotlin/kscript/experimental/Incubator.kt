package kscript.experimental

import java.io.File

/**
 * Experimental support methods. Those may change without further notice.
 *
 * @author Holger Brandl
 */


fun <T> File.mapLines(trafo: (String) -> T) {
    return useLines {
        it.map { trafo(it) }
    }
}

fun String.processLines(trafo: (String) -> String) {
    split("\n").map { println(trafo(it)) }
}


fun processStdin(trafo: (String) -> String) {
    generateSequence() { readLine() }.map {
        println(trafo(it))
    }
}
