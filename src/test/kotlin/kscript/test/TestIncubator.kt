package kscript.test

import kscript.text.*

/**
 * @author Holger Brandl
 */

fun flightsHead() = resolveArgFile(arrayOf("flights_head.txt"))

fun flightsZipped() = resolveArgFile(arrayOf("flights.tsv.gz"))
fun flights() = resolveArgFile(arrayOf("flights.txt"))

// just used for testing and development
fun linesFrom(vararg lines: String) = lines.asSequence()


fun main(args: Array<String>) {
    // remove header
    flightsHead().drop(1).take(5)


    // positive selection
    System.out.println("positive selection")
    //    flightsHead().split().select(with(1..3).and(3)).print()
    flightsHead().split().select(1, 10, 12).print()
    flightsHead().split().select(10, 1, 12).print()

    //create column
    //create column
    flightsHead().split().map { listOf(it[1], it[2], "F11-" + it[7]) }.print()

    // negative selection
    System.out.println("negative selection")
    flightsHead().split().select(without(7).and(3..4)).print()
}