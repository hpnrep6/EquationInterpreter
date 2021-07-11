import java.lang.StringBuilder

class Lexer (input : String){
    private val input : String = input

    private var xLoc : Int = 0

    val tokens : MutableList<Token> = mutableListOf<Token>()
    val errors : MutableList<Error> = mutableListOf<Error>()

    private fun reachedEnd() : Boolean {
        return xLoc + 1 >= input.length
    }

    private fun lookAhead(expected: Char) : Boolean {
        if(reachedEnd())
            return false

        return input[xLoc + 1] == expected
    }

    private fun lookAhead(func: (Char) -> Boolean) : Boolean {
        if(reachedEnd())
            return false

        return func(input[xLoc + 1])
    }

    private fun getNext() : Char{
        return input[++xLoc]
    }

    private fun moveAhead() {
        xLoc += 1
    }

    fun tokenize() {
        while(xLoc < input.length) {
            val cur: Char = input[xLoc]

            when(cur) {
                '+' -> tokens.add(
                    Token(Token.TokenType.ADD, xLoc)
                )
                '-' -> tokens.add(
                    Token(Token.TokenType.SUB, xLoc)
                )
                '*' -> tokens.add(
                    Token(Token.TokenType.MULT, xLoc)
                )
                '/' -> tokens.add(
                    Token(Token.TokenType.DIV, xLoc)
                )
                '(' -> tokens.add(
                    Token(Token.TokenType.PAREN_L, xLoc)
                )
                ')' -> tokens.add(
                    Token(Token.TokenType.PAREN_R, xLoc)
                )
                '%' -> tokens.add(
                    Token(Token.TokenType.MOD, xLoc)
                )
                '0','1','2','3','4','5','6','7','8','9' -> numberToken()
                ' ' -> {

                }
                else -> {
                    errors.add(
                        Error(input, "Unexpected character", xLoc)
                    )
                }
            }

            moveAhead()
        }
    }

    fun isNumber(char: Char) : Boolean {
        return char in '0'..'9'
    }

    fun numberToken() {
        val numString : StringBuilder = StringBuilder()

        var decimalConsumed = false

        numString.append(input[xLoc])

        while(true) {
            if(lookAhead(::isNumber)) {
                numString.append(getNext())
            }
            else if(lookAhead('.')) {
                if(decimalConsumed) {
                    break
                }
                decimalConsumed = true
                numString.append(getNext())
            } else {
                break
            }
        }

        var value : Double = (numString.toString()).toDouble()

        tokens.add(
            Token(Token.TokenType.NUMBER, value, xLoc)
        )
    }
}