class Parser(input : String, tokens : MutableList<Token>) {
    val tokens = tokens
    val input = input
    val errors = mutableListOf<Error>()

    var index: Int = 0

    fun error(offset: Int = -1) {
        errors.add(
            Error(input, "Parser error: unexpected token", tokens[index + offset].xLoc)
        )
    }

    fun EOFError(offset: Int = -1) {
        errors.add(
            Error(input, "Parser error: Reached end of line while parsing", input.length)
        )
    }

    fun nextIs(expected: Token.TokenType) {
        if (atEnd()) {
            EOFError()
        } else if (tokens[index++].type == expected) {

        } else {
            error()
        }

    }

    fun atEnd() : Boolean {
        return index >= tokens.size
    }

    fun mult_div() : Node {
        var root = num()

        while (!atEnd()) {
            when (tokens[index].type) {
                Token.TokenType.MULT -> {
                    ++index
                    root = Mult(root, num())
                }

                Token.TokenType.DIV -> {
                    ++index
                    root = Div(root, num())
                }

                Token.TokenType.ADD,
                Token.TokenType.SUB,
                Token.TokenType.PAREN_R -> {
                    return root
                }

                else -> {
                    error(0)
                    return root
                }
            }
        }

        return root
    }

    fun add_sub(node: Node) : Node {
        var root = node

        while (!atEnd()) {
            when (tokens[index].type) {
                Token.TokenType.ADD -> {
                    ++index
                    root = Add(root, Pos(mult_div()))
                }

                Token.TokenType.SUB -> {
                    ++index
                    root = Add(root, Neg(mult_div()))
                }

                else -> {
                    return root
                }
            }
        }

        return root
    }

    fun num() : Node {
        if (atEnd()) {
            error(-1)
            return Number(0.0)
        }

        when (tokens[index].type) {
            Token.TokenType.NUMBER -> {
                return Number(tokens[index++].value)
            }

            Token.TokenType.PAREN_L -> {
                ++index;
                var node = expr()
                nextIs(Token.TokenType.PAREN_R)
                return node
            }

            Token.TokenType.ADD -> {
                ++index

                return Pos(num())
            }

            Token.TokenType.SUB -> {
                ++index

                return Neg(num())
            }

            else -> {
                if (index > 0) {
                    error()
                } else
                    error(0)

                return Number(0.0)
            }
        }
    }

    fun expr() : Node {
        var root = mult_div()
        return add_sub(root)
    }
}