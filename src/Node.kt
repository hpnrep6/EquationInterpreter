abstract class Node {
    abstract fun visit(visitor : Visitor) : Double
}

class Number(value : Double) : Node() {
    val value = value

    override fun visit(visitor: Visitor) : Double {
        return visitor.visitNumber(this)
    }

    override fun toString(): String {
        return "Number: $value"
    }
}

abstract class Binary(left : Node, operator : (Double, Double) -> Double, right : Node) : Node() {
    val right = right
    val left = left
    val operator = operator

    override fun visit(visitor: Visitor) : Double {
        return visitor.visitBinary(this)
    }

    override fun toString(): String {
        return "Binary ( $left, $right )"
    }
}

abstract class Unary(operator : (Double) -> Double, right : Node) : Node() {
    val right = right
    val operator = operator

    override fun visit(visitor: Visitor) : Double {
        return visitor.visitUnary(this)
    }

    override fun toString(): String {
        return "Unary ( $right )"
    }
}

fun add(a : Double, b : Double) : Double {
    return a + b
}

fun sub(a : Double, b : Double) : Double {
    return a - b
}

fun mult(a : Double, b : Double) : Double {
    return a * b
}

fun div(a : Double, b : Double) : Double {
    return a / b
}

class Add(left: Node, right: Node) : Binary(left, ::add, right) {

}

class Sub(left: Node, right: Node) : Binary(left, ::sub, right) {

}

class Mult(left: Node, right: Node) : Binary(left, ::mult, right) {

}

class Div(left: Node, right: Node) : Binary(left, ::div, right) {

}

fun neg(a : Double) : Double {
    return -a
}

fun pos(a : Double) : Double {
    return a
}

class Neg(right : Node) : Unary(::neg, right) {

}

class Pos(right : Node) : Unary(::pos, right) {

}