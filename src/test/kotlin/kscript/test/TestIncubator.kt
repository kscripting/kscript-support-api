package kscript.test

import kscript.text.*

/**
 * @author Holger Brandl
 */


// just used for testing and development
fun linesFrom(vararg lines: String) = lines.asSequence()


fun main(args: Array<String>) {
    someFlights().split().map { listOf(it[1], it[2], "F11-" + it[7]) }.print()
    someFlights().split().map { Row(it[1], it[2], "F11-" + it[7]) }.print()
    //  kscript 'lines.split().map { listOf(it[1], it[2], "F11-"+ it[7]) }.print()' some_flights.tsv
    //  kscript 'lines.split().map { Row(it[1], it[2], "F11-"+ it[7]) }.print()' some_flights.tsv

    someFlights().split()
            .select(with(3).and(11..13).and(1))
            .first().print() //shouldBe listOf("day", "flight", "tailnum", "origin", "year")

    // remove header
    someFlights().drop(1).take(5)


    // positive selection
    System.out.println("positive selection")
    //    flightsHead().split().select(with(1..3).and(3)).print()
    someFlights().split().select(1, 10, 12).print()
    someFlights().split().select(10, 1, 12).print()

    //create column
    //create column
    someFlights().split().map { listOf(it[1], it[2], "F11-" + it[7]) }.print()

    // negative selection
    System.out.println("negative selection")
    someFlights().split().select(without(7).and(3..4)).print()
}