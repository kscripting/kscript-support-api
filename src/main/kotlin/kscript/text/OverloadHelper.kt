package kscript.text

/** Declare another extension on print to faciliate printing of more base split lines.*/

fun Sequence<List<String>>.print(separator: String = "\t") = map { Row(it) }.print(separator)
