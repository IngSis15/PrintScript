package interpreter

import ast.AssignExpr
import ast.CallPrintExpr
import ast.DeclareExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import ast.TypeExpr
import ast.VariableExpr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EvaluatorTests {
    @Test
    fun `test number expression`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val expr = NumberExpr(5, 0)

        val result = evaluator.evaluate(expr, scope)
        assertEquals(5, result)
    }

    @Test
    fun `test string expression`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val expr = StringExpr("hello", 0)

        val result = evaluator.evaluate(expr, scope)
        assertEquals("hello", result)
    }

    @Test
    fun `test variable declaration and retrieval`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val declareExpr = DeclareExpr(TypeExpr("x", "Integer", 0), NumberExpr(10, 0), 0)

        evaluator.evaluate(declareExpr, scope)
        val result = evaluator.evaluate(VariableExpr("x", 0), scope)

        assertEquals(10, result)
    }

    @Test
    fun `test variable assignment`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val declareExpr = DeclareExpr(TypeExpr("x", "Integer", 0), NumberExpr(10, 0), 0)
        val assignExpr = AssignExpr(VariableExpr("x", 0), NumberExpr(20, 0), 0)

        evaluator.evaluate(declareExpr, scope)
        evaluator.evaluate(assignExpr, scope)
        val result = evaluator.evaluate(VariableExpr("x", 0), scope)

        assertEquals(20, result)
    }

    @Test
    fun `test undefined variable access throws exception`() {
        val evaluator = Evaluator()
        val scope = Scope()

        val exception =
            assertThrows<IllegalArgumentException> {
                evaluator.evaluate(VariableExpr("y", 0), scope)
            }

        assertEquals("Undefined variable: y", exception.message)
    }

    @Test
    fun `test operator expression addition`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val expr = OperatorExpr(NumberExpr(5, 0), "+", NumberExpr(3, 0), 0)

        val result = evaluator.evaluate(expr, scope)
        assertEquals(8.0, result)
    }

    @Test
    fun `test operator expression division by zero`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val expr = OperatorExpr(NumberExpr(5, 0), "/", NumberExpr(0, 0), 0)

        val exception =
            assertThrows<ArithmeticException> {
                evaluator.evaluate(expr, scope)
            }

        assertEquals("Division by zero", exception.message)
    }

    @Test
    fun `test print expression`() {
        val evaluator = Evaluator()
        val scope = Scope()
        val expr = CallPrintExpr(StringExpr("test", 0), 0)

        val result = evaluator.evaluate(expr, scope)
        assertEquals("test", result)
    }
}
