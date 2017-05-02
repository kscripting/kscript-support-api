package kscript.test

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import kscript.DocOpt

/**
 * @author Holger Brandl
 */


class DocOptTest : StringSpec() { init {

    val args = "-n 7 --pc-only --gtf my.gtf a.fastq b.fastq".split(" ").toTypedArray()

    val usage = """
Use star to align fastq files against a genome
Usage: star_align.kts [options] <igenome> <fastq_files>...

Options:
 --gtf <gtfFile>        Custom gtf file instead of igenome bundled copy
 --pc-only              Use protein coding genes only for mapping and quantification
 -n --num-fragments     Fragment count used for processing [default: 5]
"""

    val docopt = DocOpt(args, usage)

    docopt.getString("gtf") shouldBe "my.gtf"
    docopt.getStrings("fastq_files") shouldBe arrayOf("a.fastq", "b.fastq")

    docopt.getInt("fastq_files")
    docopt.getNumber("num-fragments") shouldBe 7
    docopt.getNumber("num-fragments") shouldBe 7
    docopt.getNumber("-n")
    docopt.getBoolean("pc-only") shouldBe true
}
}