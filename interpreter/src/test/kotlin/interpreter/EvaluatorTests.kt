package interpreter

import ast.AssignExpr
import ast.CallPrintExpr
import ast.DeclareExpr
import ast.IdentifierExpr
import ast.NumberExpr
import ast.OperatorExpr
import ast.StringExpr
import ast.TypeExpr
import lib.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EvaluatorTests {
    @Test
    fun `test number expression`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()

        val expr = NumberExpr(5, Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals(5, result)
    }

    @Test
    fun `test string expression`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()

        val expr = StringExpr("hello", Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals("hello", result)
    }

    @Test
    fun `test variable declaration and retrieval`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()
        val declareExpr = DeclareExpr(TypeExpr("x", "number", Position(0, 0)), NumberExpr(10, Position(0, 0)), Position(0, 0))

        evaluator.evaluate(declareExpr, scope)
        val result = evaluator.evaluate(IdentifierExpr("x", Position(0, 0)), scope)

        assertEquals(10, result)
    }

    @Test
    fun `test variable assignment`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()

        val declareExpr = DeclareExpr(TypeExpr("x", "number", Position(0, 0)), NumberExpr(10, Position(0, 0)), Position(0, 0))
        val assignExpr = AssignExpr(IdentifierExpr("x", Position(0, 0)), NumberExpr(20, Position(0, 0)), Position(0, 0))

        evaluator.evaluate(declareExpr, scope)
        evaluator.evaluate(assignExpr, scope)
        val result = evaluator.evaluate(IdentifierExpr("x", Position(0, 0)), scope)

        assertEquals(20, result)
    }

    @Test
    fun `test undefined variable access throws exception`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()

        val exception =
            assertThrows<IllegalArgumentException> {
                evaluator.evaluate(IdentifierExpr("y", Position(0, 0)), scope)
            }

        assertEquals("Undefined variable: y", exception.message)
    }

    @Test
    fun `test operator expression addition`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()
        val expr = OperatorExpr(NumberExpr(5, Position(0, 0)), "+", NumberExpr(3, Position(0, 0)), Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals(8.0, result)
    }

    @Test
    fun `test operator expression division by zero`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()

        val expr = OperatorExpr(NumberExpr(5, Position(0, 0)), "/", NumberExpr(0, Position(0, 0)), Position(0, 0))

        val exception =
            assertThrows<ArithmeticException> {
                evaluator.evaluate(expr, scope)
            }

        assertEquals("Division by zero", exception.message)
    }

    @Test
    fun `test print expression`() {
        val printEmitter = PrintCollector()
        val evaluator = Evaluator(printEmitter)
        val scope = Scope()

        val expr = CallPrintExpr(StringExpr("test", Position(0, 0)), Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals("test", result)
    }

    @Test
    fun `test string variable declaration`() {
        val evaluator = Evaluator(PrintCollector())
        val scope = Scope()

        val expr =
            DeclareExpr(
                TypeExpr("x", "string", Position(0, 0)),
                StringExpr("hello", Position(0, 0)),
                Position(0, 0),
            )

        evaluator.evaluate(expr, scope)
        val result = evaluator.evaluate(IdentifierExpr("x", Position(0, 0)), scope)
        assertEquals("hello", result)
    }

    @Test
    fun `test sum of strings`() {
        val printCollector = PrintCollector()
        val evaluator = Evaluator(printCollector)
        val scope = Scope()

        val left = StringExpr("Hello", Position(0, 0))
        val right = StringExpr(", World", Position(0, 0))
        val expr = OperatorExpr(left, "+", right, Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals("Hello, World", result)
    }

    @Test
    fun `test sum of string and number`() {
        val printCollector = PrintCollector()
        val evaluator = Evaluator(printCollector)
        val scope = Scope()

        val left = StringExpr("The number is ", Position(0, 0))
        val right = NumberExpr(42, Position(0, 0))
        val expr = OperatorExpr(left, "+", right, Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals("The number is 42", result)
    }

    @Test
    fun `test sum of number and string`() {
        val printCollector = PrintCollector()
        val evaluator = Evaluator(printCollector)
        val scope = Scope()

        val left = NumberExpr(42, Position(0, 0))
        val right = StringExpr(" is the answer", Position(0, 0))
        val expr = OperatorExpr(left, "+", right, Position(0, 0))

        val result = evaluator.evaluate(expr, scope)
        assertEquals("42 is the answer", result)
    }
}
