package ast

import lib.Position

/*
Variable declaration expression:    let x: string = "hello";
                                    const x: string = "world";
 */
class DeclareExpr(val name: String, val type: String, val value: Expression?, val mutable: Boolean, val pos: Position) :
    Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeclareExpr) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (value != other.value) return false
        if (mutable != other.mutable) return false
        if (pos != other.pos) return false

        return true
    }
}
