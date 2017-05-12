package kscript.text


/** A `Sequence<String>` iterator for standard input */
public val stdin by lazy { generateSequence() { readLine() } }

fun linesFrom(file: java.io.File) = java.io.BufferedReader(java.io.FileReader(file)).lineSequence()


/**
 * File argument processor that works similar to awk: If data is available on stdin, use it. If not expect a file argument and read from that one instead.
 * */
fun resolveArgFile(args: Array<String>, position: Int = 0): Sequence<String> {
    //    if (stdin.iterator().hasNext()) return stdin
    if (System.`in`.available() > 0) return kscript.text.stdin

    kscript.stopIfNot(args.isNotEmpty()) { "Missing file or input input stream" }
    kscript.stopIfNot(args.size >= position) { "arg position ${position} exceeds number of arguments ${args.size} " }

    val fileArg = args[position]

    //    stdinNames: List<String> = listOf("-", "stdin")
    //    if (stdinNames.contains(fileArg)) return stdin

    val inputFile = java.io.File(fileArg)

    kscript.stopIfNot(inputFile.canRead()) { "Can not read from '${fileArg}'" }

    // test for compression and uncompress files automatically
    val isCompressedInput = inputFile.name.run { endsWith(".zip") || endsWith(".gz") }

    val lineReader = if (isCompressedInput) {
        java.io.InputStreamReader(java.util.zip.GZIPInputStream(java.io.FileInputStream(inputFile)))
    } else {
        java.io.FileReader(inputFile)
    }


    // todo we don't close the buffer with this approach
    //    BufferedReader(FileReader(inputFile )).use { return it }
    return java.io.BufferedReader(lineReader).lineSequence()
}


/** Endpoint for a kscript pipe. */
fun Sequence<String>.print() = forEach { println(it) }

/** Endpoint for a kscript pipe. */
fun Iterable<String>.print() = forEach { println(it) }



//https://dzone.com/articles/readingwriting-compressed-and
/** Save a list of items into a file. Output can be option ally zipped and a the stringifying operation can be changed from toString to custom operation if needed. */
fun <T> Iterable<T>.saveAs(f: java.io.File,
                           transform: (T) -> String = { it.toString() },
                           separator: Char = '\n',
                           overwrite: Boolean = true,
                           compress: Boolean = f.name.let { it.endsWith(".zip") || it.endsWith(".gz") }) {

    // ensure that file is not yet there or overwrite flag is set
    require(!f.isFile || overwrite) { "$f is present already. Use overwrite=true to enforce file replacement." }

    val p = if (!compress) java.io.PrintWriter(f) else java.io.BufferedWriter(java.io.OutputStreamWriter(java.util.zip.GZIPOutputStream(java.io.FileOutputStream(f))))

    toList().forEach { p.write(transform(it) + separator) }

    p.close()
}


