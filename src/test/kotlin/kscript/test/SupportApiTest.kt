package kscript.test

import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldEqual
import io.kotlintest.matchers.startWith
import io.kotlintest.specs.StringSpec
import kscript.text.linesFrom
import kscript.text.print
import kscript.text.resolveArgFile
import kscript.text.split
import java.io.File

/**
 * @author Holger Brandl
 */
class SupportApiTest : StringSpec() { init {

    //    "allow to use stdin for filtering and mapping" {
    //        kscript.text.stdin.filter { "^de0[-0]*".toRegex().matches(it) }.map { it + "foo:" }.print()
    //    }

    "extract field with column filter" {
        linesFrom(File("src/test/resources/flights_head.txt")).split().
                filter { it[8] == "N14228" }.
                map { it[10] }.
                toList().
                apply {
                    size shouldEqual 1
                    first() shouldEqual "EWR"
                }

    }

    "compressed lines should be unzipped on the fly"{
        resolveArgFile(arrayOf("src/test/resources/flights.tsv.gz")).
                drop(1).first() should startWith("2013")
    }
}
}