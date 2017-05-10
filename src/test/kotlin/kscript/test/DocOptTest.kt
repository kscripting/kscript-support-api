package kscript.test

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec
import kscript.util.DocOpt
import java.io.File

/**
 * @author Holger Brandl
 */


class DocOptTest : StringSpec() { init {


    val usage = """
Use star to align fastq files against a genome
Usage: star_align.kts [options] <igenome> <fastq_files>...

Options:
 --gtf <gtfFile>                   Custom gtf file instead of igenome bundled copy
 --pc-only                         Use protein coding genes only for mapping and quantification
 -n --num-fragments <num_frags>    Fragment count used for processing [default: 5]
"""

    val args = "-n 7 --pc-only --gtf my.gtf genome.fasta a.fastq b.fastq".split(" ").toTypedArray()


    val docopt = DocOpt(args, usage)


    // optional arg
    docopt.getString("gtf") shouldBe "my.gtf"
    docopt.getFile("gtf") shouldBe File("my.gtf")

    // mandatory arg
    docopt.getString("igenome") shouldBe "genome.fasta"
    docopt.getFile("igenome") shouldBe File("genome.fasta")

    docopt.getStrings("fastq_files") shouldBe listOf("a.fastq", "b.fastq")
    docopt.getFiles("fastq_files") shouldBe listOf(File("a.fastq"), File("b.fastq"))


    docopt.getInt("num-fragments") shouldBe 7
    docopt.getNumber("num-fragments").toString() shouldBe "7.0"
    docopt.getBoolean("pc-only") shouldBe true
}
}