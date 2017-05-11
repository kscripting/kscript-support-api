package kscript.test

import io.kotlintest.matchers.*
import io.kotlintest.specs.StringSpec
import kscript.isTestMode
import kscript.text.*

/**
 * @author Holger Brandl
 */


fun someFlights() = resolveArgFile(arrayOf("src/test/resources/some_flights.tsv"))

fun flightsZipped() = resolveArgFile(arrayOf("src/test/resources/flights.tsv.gz"))
fun flights() = resolveArgFile(arrayOf("src/test/resources/flights.txt"))


class SupportApiTest : StringSpec() { init {

    isTestMode = true


    "extract field with column filter" {
        someFlights().split().
                filter { it[12] == "N14228" }.
                map { it[13] }.
                toList().
                apply {
                    size shouldEqual 1
                    first() shouldEqual "EWR"
                }
    }


    "allow to select columsn" {
        someFlights().split()
                .select(with(3).and(11..13).and(1))
                .first() shouldBe listOf("day", "flight", "tailnum", "origin", "year")
    }


    "rejeced mixed select" {
        shouldThrow<IllegalArgumentException> {
            someFlights().split().select(1, -2)
        }.message shouldBe "[ERROR] Can not mix positive and negative selections"
    }


    "compressed lines should be unzipped on the fly"{
        resolveArgFile(arrayOf("src/test/resources/flights.tsv.gz")).
                drop(1).first() should startWith("2013")
    }
}
}