package kscript.test

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import kscript.*
import java.io.File

/**
 * @author Holger Brandl
 */
class SupportApiTest : StringSpec() { init {

    "allow to use stdin for filtering and mapping" {
        stdin.filter { "^de0[-0]*".toRegex().matches(it) }.map { it + "foo:" }.print()
    }

    "length should return size of string" {
        //        "hello".length shouldBe 5
        //        stopIfNot("FOO"=="BAR"){"condition not met"}
        println("current dir is " + File(".").absolutePath)
        argMap("src/test/resources/flights_head.txt") { it.split("\t")[7] }
        argFilter("src/test/resources/flights_head.txt") { it.split("\t")[7] == "UA" }
    }
}
}