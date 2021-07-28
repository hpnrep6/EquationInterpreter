class Visitor {
    fun visitBinary(node : Binary) : Double {
        return node.operator(node.left.visit(this), node.right.visit(this))
    }

    fun visitUnary(node : Unary) : Double {
        return node.operator(node.right.visit(this))
    }

    fun visitNumber(node : Number) : Double {
        return node.value
    }
}