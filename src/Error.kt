import java.lang.StringBuilder

class Error(line: String, message: String, xLoc: Int) {
    val line = line
    val message = message
    val xLoc = xLoc

    fun print() {
        var whitespace = StringBuilder()

        for(i in 1..xLoc) {
            whitespace.append(' ')
        }

        println(line)
        println("$whitespace^")
        println("Error: $message")
        println()
    }
}