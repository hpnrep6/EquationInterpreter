class Token (type: TokenType, value : Double, xLoc: Int) {
    enum class TokenType {
        NUMBER,
        ADD,
        SUB,
        MULT,
        DIV,
        PAREN_L,
        PAREN_R,
        MOD
    }

    constructor (type: TokenType, xLoc: Int) : this(type, 0.0, xLoc) {

    }

    val type : TokenType = type
    val value : Double = value
    val xLoc : Int = xLoc

    override fun toString(): String {
        return "$type: $value"
    }
}