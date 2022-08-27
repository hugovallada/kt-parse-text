import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    val (fileName, newFileName, parsersString) = getArgs(args)
    println(parsersString)
    val parsers = getParsers(parsersString)
    with(File(fileName)) {
        deleteOnExit()
        val parsedStrings = mutableListOf<String>().apply {
            readLines().forEach { line -> add(parseText(parsers, line)) }
        }.toList()
        with(File(newFileName)) { parsedStrings.forEach { text -> appendText("$text\n") } }
    }
}

private fun getArgs(args: Array<String>): List<String> {
    if (args.size > 1) {
        return args.slice(0..2)
    }
    throw Exception("You must pass the name of the file to be parsed and the name of the new file.")
}

private fun getParsers(parserString: String): Map<String, String> {
    val parsers = mutableMapOf<String, String>()
    parserString.split(",").run {
        println(this)
        forEach {
            val (key, value) = it.split("=")
            parsers.put(key, value)
        }
    }
    return parsers
}

private fun parseText(parsers: Map<String, String>, line: String): String {
    var modifiedLine = line
    for (oldValue in parsers.keys) {
        if (modifiedLine.contains(oldValue)) {
            val newValue = if (parsers[oldValue] != "spc")  parsers[oldValue] ?: valueDoesNotExists(oldValue) else  ""
            modifiedLine = modifiedLine.replace(oldValue, newValue )
        }
    }
    return modifiedLine.trim()
}

private fun valueDoesNotExists(oldValue: String): Nothing = throw Exception("No value given to replace $oldValue")

