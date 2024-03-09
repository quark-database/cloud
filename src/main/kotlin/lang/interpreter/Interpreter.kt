package quark.lang.interpreter

import quark.lang.NativeType
import quark.lang.functions.Function
import quark.lang.functions.FunctionBundle
import quark.lang.functions.createColumnFunction
import quark.lang.instructions.Instruction
import quark.lang.instructions.InstructionBundle
import quark.lang.instructions.InstructionResult
import quark.lang.lexer.Lexer
import quark.lang.parser.Parser
import quark.lang.types.Thing
import quark.lang.types.TypeBundle

class Interpreter {
    internal val types = TypeBundle()
    internal val functions = FunctionBundle()
    internal val instructions = InstructionBundle()

    private fun parse(query: String): Query {
        val lexer = Lexer(query)
        lexer.lex()

        val parser = Parser(lexer.tokens, this)
        parser.parse()

        return Query(parser.instruction, parser.arguments)
    }

    internal fun generateColumnFunctions() {
        functions += types.map { type -> createColumnFunction(type) }
    }

    internal fun execute(query: String): InstructionResult {
        return parse(query).execute()
    }

    internal fun evaluate(expression: String): Thing<*> {
        val query = parse("evaluate: expression = $expression;")
        val value = query.arguments["expression"]

        return value
    }

    internal inline fun <reified T> wrap(nativeObject: T): Thing<T> where T : NativeType<T> {
        val type = types.typeOf<T>(nativeObject)
        return Thing(type, nativeObject)
    }

    fun getFunction(functionName: String): Function<*> {
        return functions[functionName]
    }

    fun getInstruction(instructionName: String): Instruction {
        return instructions[instructionName]
    }
}