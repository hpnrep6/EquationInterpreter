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
    // Lexical analysis; generate tokens
    val lex : Lexer = Lexer(input)
    lex.tokenize()

    if(lex.errors.size > 0) {
        for(error in lex.errors) {
            error.print()
        }
        return
    }
    val tokens = lex.tokens

    // Parser and interpreters
    val interpreter: Interpreter = Interpreter(input, tokens)
    val total = interpreter.expr()

    if(interpreter.errors.size > 0) {
        for(error in interpreter.errors) {
            error.print()
        }
        return
    }

    // Format total to not include decimal if whole number
    if(hasFract(total)) {
        println("Total: $total")
    } else {
        val wholeTotal = total.toLong()
        println("Total: $wholeTotal")
    }
}

fun hasFract(num: Double) : Boolean {
    val whole = num.toLong()
    val fract = num - whole.toDouble()
    return fract != 0.0
}