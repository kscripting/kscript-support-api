package kscript.test

import io.kotlintest.matchers.*
import kscript.text.resolveArgFile
import kscript.text.select
import kscript.text.split
import kscript.text.with
import org.junit.Test

/**
 * @author Holger Brandl
 */


fun someFlights() = resolveArgFile(arrayOf("src/test/resources/some_flights.tsv"))

fun flightsZipped() = resolveArgFile(arrayOf("src/test/resources/flights.tsv.gz"))
fun flights() = resolveArgFile(arrayOf("src/test/resources/flights.txt"))


class SupportApiTest {

    init {
        kscript.isTestMode = true
    }


    @Test
    fun `extract field with column filter`() {
        someFlights().split().
                filter { it[12] == "N14228" }.
                map { it[13] }.
                toList().
                apply {
                    size shouldEqual 1
                    first() shouldEqual "EWR"
                }
    }


    @Test
    fun `allow to select column`() {
        someFlights().split()
                .select(with(3).and(11..13).and(1))
                .first().data shouldBe listOf("day", "flight", "tailnum", "origin", "year")
    }


    @Test
    fun `is should perform a negative selection`() {
        someFlights().split()
                .select(1, 2, 3)
                .select(-2)
                .first().data shouldBe listOf("year", "day")
    }


    @Test
    fun `rejeced mixed select`() {
        shouldThrow<IllegalArgumentException> {
            someFlights().split().select(1, -2)
        }.message shouldBe "[ERROR] Can not mix positive and negative selections"
    }


    @Test
    fun `compressed lines should be unzipped on the fly`() {
        resolveArgFile(arrayOf("src/test/resources/flights.tsv.gz")).
                drop(1).first() should startWith("2013")
    }
}