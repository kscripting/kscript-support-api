package kscript.test

import io.kotlintest.specs.FlatSpec
import kscript.print
import kscript.stdin

/**
 * @author Holger Brandl
 */
class SupportApiTest : FlatSpec() { init {

    "it" should "allow to use stdin for filtering and mapping" {
        val longDf = stdin.filter { "^de0[-0]*".toRegex().matches(it) }.map { it + "foo:" }.print()
    }
}
}