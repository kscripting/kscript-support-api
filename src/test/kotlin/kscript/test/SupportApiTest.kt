package kscript.test

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import kscript.print
import kscript.stdin

/**
 * @author Holger Brandl
 */
class SupportApiTest : StringSpec() { init {

    "length should return size of string" {
        "hello".length shouldBe 5
    }

    "allow to use stdin for filtering and mapping" {
        stdin.filter { "^de0[-0]*".toRegex().matches(it) }.map { it + "foo:" }.print()
    }
}
}