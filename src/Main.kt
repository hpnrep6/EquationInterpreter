import java.util.*

fun main(args : Array<String>) {
    while(true) {
        print("Equation interpreter input> ")
        val input = readLine()!!

        if(input.isEmpty()) {
            break
        }
        interpret(input)
    }
}

fun interpret(input: String) {
    var input = input
    var requestAST = false

    if (requestAST(input)) {
        input = input.substring("ast:".length)
        requestAST = true
    }

    // Lexical analysis; generate tokens
    val lex : Lexer = Lexer(input)
    lex.tokenize()

    if (lex.errors.size > 0) {
        for (error in lex.errors) {
            error.print()
        }
        return
    }
    val tokens = lex.tokens

    // Parser
    val parser = Parser(input, tokens)

    val rootNode = parser.expr()

    if (parser.errors.size > 0) {
        for (error in parser.errors) {
            error.print()
        }
        return
    }

    if (requestAST) {
        println("Generated Abstract Syntax Tree:")
        println(rootNode)
        return
    }

    // Interpreter; "walk" AST tree to get total
    val visitor = Visitor()

    var total = rootNode.visit(visitor)

    // Format total to not include decimal if whole number
    if(hasFract(total)) {
        println("Total: $total")
    } else {
        val wholeTotal = total.toLong()
        println("Total: $wholeTotal")
    }
}

fun requestAST(input: String) : Boolean {
    return input.lowercase(Locale.getDefault()).startsWith("ast:")
}

fun hasFract(num: Double) : Boolean {
    val whole = num.toLong()
    val fract = num - whole.toDouble()
    return fract != 0.0
}