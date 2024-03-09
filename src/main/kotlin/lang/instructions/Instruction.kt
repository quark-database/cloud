package quark.lang.instructions

import quark.dbms.exceptions.DatabaseException
import quark.lang.exceptions.LanguageException
import quark.lang.instructions.InstructionStatus.*
import quark.lang.instructions.exceptions.InstructionArgumentException
import quark.lang.interpreter.Interpreter
import quark.lang.types.Thing
import quark.util.classes.friendlyName
import quark.util.terminal.TerminalColor
import quark.util.terminal.paint

typealias InstructionResultMessage = String

abstract class Instruction(
    private val permission: String,
    private val interpreter: Interpreter,
    vararg parameters: Pair<String, InstructionParameter>,
) {
    private val parameters = InstructionParameters(*parameters)
    internal val name by lazy { friendlyName.replace(" instruction", "") }
    internal val general: String
        get() = parameters.firstOrNull { (_, parameter) -> parameter.general }?.first ?: throw LanguageException("The instruction $name does not have a general parameter.")

    internal abstract fun onInvoke(arguments: InstructionArguments, result: InstructionResultBuilder): InstructionResultMessage

    internal operator fun invoke(vararg arguments: Pair<String, Thing<*>>): InstructionResult {
        return invoke(InstructionArguments(*arguments))
    }

    internal operator fun invoke(arguments: InstructionArguments): InstructionResult {
        ensureArgumentsAreValid(arguments)

        val result = InstructionResultBuilder(interpreter)

        try {
            result.message = onInvoke(arguments, result)
            result.status = OK
        } catch(exception: LanguageException) {
            result.message = exception.message
            result.status = SYNTAX_ERROR
        } catch(exception: DatabaseException) {
            result.message = exception.message
            result.status = DATABASE_ERROR
        } catch(exception: NotImplementedError) {
            print(exception.stackTraceToString().paint(TerminalColor.PURPLE_BRIGHT))
        } catch(exception: Throwable) {
            result.message = exception.message
            result.status = SERVER_ERROR
        }

        return result.build()
    }

    private fun ensureArgumentsAreValid(arguments: InstructionArguments) {
        arguments.firstOrNull { (name) -> name !in parameters }?.let { (name) ->
            throw InstructionArgumentException("The parameter name $name is not in parameter list: $parameters.")
        }

        for((name, parameter) in parameters) {
            if(parameter.required && name !in arguments) {
                throw InstructionArgumentException("The required parameter $name was not provided.")
            }
        }
    }

    override fun toString(): String {
        return "$name: $parameters"
    }
}