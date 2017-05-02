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

    val docopt = Docopt(usage)

    private val myDO by lazy {
        docopt.parse(args.toList()).map {
            it.key.removePrefix("--").replace("[<>]".toRegex(), "") to it.value
        }.toMap()
    }

    fun getString(key: String) = myDO[key]!!.toString()
    fun getStrings(key: String) = (myDO[key]!! as List<*>).map { it as String }

    fun getFile(key: String) = File(getString(key))
    fun getFiles(key: String) = getStrings(key).map { File(it) }


    fun getInt(key: String) = myDO[key]!!.toString().toInt()

    fun getNumber(key: String) = myDO[key]!!.toString().toFloat()

    fun getBoolean(key: String) = myDO[key]!!.toString().toBoolean()
}