package kscript

import java.io.*
import java.util.zip.GZIPOutputStream


/** a `Sequence<String>` iterator for standard input */
val stdin by lazy { generateSequence() { readLine() } }

fun linesFrom(file: File) = BufferedReader(FileReader(file)).lineSequence()

// just used for testing and development
fun linesFrom(vararg lines: String) = lines.asSequence()


/**
 * File argument processor that works similar to awk. If data is available on stdin, use it. If not expect a file argument and read from that one instead.
 * */
fun resolveArgFile(args: Array<String>, position: Int = 0): Sequence<String> {
    if (stdin.iterator().hasNext()) return stdin

    stopIfNot(args.isNotEmpty()) { "Missing file or input input stream" }
    stopIfNot(args.size >= position) { "arg position ${position} exceeds number of arguments ${args.size} " }

    val fileArg = args[position]

    //    stdinNames: List<String> = listOf("-", "stdin")
    //    if (stdinNames.contains(fileArg)) return stdin

    val inputFile = File(fileArg)

    stopIfNot(inputFile.canRead()) { "Can not read from '${fileArg}'" }

    // todo we don't close the buffer with this approach
    //    BufferedReader(FileReader(inputFile )).use { return it }

    return BufferedReader(FileReader(inputFile)).lineSequence()
}


/** Endpoint for a kscript pipe. */
fun Sequence<String>.print() = forEach { println(it) }

/** Endpoint for a kscript pipe. */
fun Iterable<String>.print() = forEach { println(it) }



//https://dzone.com/articles/readingwriting-compressed-and
/** Save a list of items into a file. Output can be option ally zipped and a the stringifying operation can be changed from toString to custom operation if needed. */
fun <T> Iterable<T>.saveAs(f: File,
                           transform: (T) -> String = { it.toString() },
                           separator: Char = '\n',
                           overwrite: Boolean = true,
                           compress: Boolean = f.name.let { it.endsWith(".zip") || it.endsWith(".gz") }) {

    // ensure that file is not yet there or overwrite flag is set
    require(!f.isFile || overwrite) { "$f is present already. Use overwrite=true to enforce file replacement." }

    val p = if (!compress) PrintWriter(f) else BufferedWriter(OutputStreamWriter(GZIPOutputStream(FileOutputStream(f))))

    toList().forEach { p.write(transform(it) + separator) }

    p.close()
}


