class Interpreter(input: String, tokens: MutableList<Token>) {
    val input = input
    val tokens = tokens

    val errors = mutableListOf<Error>()
    var xLoc: Int = 0

    fun error() {
        error(-1)
    }

    fun error(offset: Int) {
        errors.add(
            Error(input, "Unexpected token", tokens[xLoc + offset].xLoc)
        )
//        for(i in Thread.currentThread().stackTrace) {
//            println(i)
//        }
    }

    fun atEnd() : Boolean {
        return xLoc >= tokens.size
    }

    fun moveAhead(expected: Token.TokenType) {
        if(xLoc >= tokens.size) {
            errors.add(
                Error(input, "Reached end of line while parsing", input.length)
            )
            return
        }

        if(tokens[xLoc++].type == expected) {
        } else {
            error()
        }
    }

    fun mult_div(): Double {
        var total: Double = num()

        while(!atEnd()) {
            when (tokens[xLoc].type) {
                Token.TokenType.MULT -> {
                    ++xLoc
                    total *= num()
                }
                Token.TokenType.DIV -> {
                    ++xLoc
                    total /= num()
                }
                Token.TokenType.MOD -> {
                    ++xLoc
                    total %= num()
                }
                Token.TokenType.ADD,
                Token.TokenType.SUB,
                Token.TokenType.PAREN_R -> {
                    return total
                }
                else -> {
                    error(0)
                    return 0.0
                }
            }
        }
        return total
    }

    fun add_sub(): Double {
        if(atEnd())
            return 0.0

        var total: Double = 0.0

        while(!atEnd()) {
            when (tokens[xLoc].type) {
                Token.TokenType.ADD -> {
                    xLoc++
                    total += mult_div()
                }
                Token.TokenType.SUB -> {
                    xLoc++
                    total -= mult_div()
                }
                else -> {
                    return total
                }
            }
        }
        return total
    }

    fun num(): Double {
        if(atEnd()) {
            error(-1)
            return 0.0
        }

        when(tokens[xLoc].type) {
            Token.TokenType.NUMBER -> {
                return tokens[xLoc++].value
            }
            Token.TokenType.PAREN_L -> {
                xLoc++
                var total = expr()
                moveAhead(Token.TokenType.PAREN_R)
                return total
            }
            else -> {
                if(xLoc > 0)
                    error()
                else
                    error(0)
                return 0.0
            }
        }
    }

    fun expr(): Double {
        var total = mult_div()
        return total + add_sub()
    }

}
