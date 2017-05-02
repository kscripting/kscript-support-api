package kscript

import org.docopt.Docopt
import java.io.File

/**
 * Helpers to build CLIs with kscript and docopt
 *
 * @author Holger Brandl
 */

/** Simple Kotlin facade for org.docopt.Docopt.Docopt(java.lang.String) .*/
class DocOpt(args: Array<String>, val usage: String) {

    val myDO by lazy {
        Docopt(usage).parse(args.toList()).map {
            it.key.removePrefix("--").replace("[<>]".toRegex(), "") to it.value?.toString()
        }.toMap()
    }

    fun getString(key: String) = myDO[key]!!
    fun getStrings(key: String) = myDO[key]!!

    fun getInt(key: String) = myDO[key]!!.toInt()

    fun getNumber(key: String) = myDO[key]!!.toFloat()

    fun getBoolean(key: String) = myDO[key]!!.toBoolean()
}