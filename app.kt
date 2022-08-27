import java.io.File
import java.lang.Exception

fun main(args: Array<String>) {
    val (fileName, newFileName) = getFileNames(args)
    with(File(fileName)) {
        deleteOnExit()
        val parsedStrings = mutableListOf<String>().apply {
            readLines().forEach { line -> add(line.replace("*", "").trim()) }
        }.toList()
        with(File(newFileName)) { parsedStrings.forEach { text -> appendText("$text\n") } }
    }
}

private fun getFileNames(args: Array<String>): List<String> {
    if (args.size > 1) {
        return args.slice(0..1)
    }
    throw Exception("You must pass the name of the file to be parsed and the name of the new file.")
}