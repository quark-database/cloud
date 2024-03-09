package quark.lang.functions.builtin

import quark.lang.functions.Function
import quark.lang.functions.FunctionArguments
import quark.lang.functions.FunctionParameter
import quark.lang.types.builtin.StrType

object ReplaceFunction : Function<String>(
    StrType,
    "string" to FunctionParameter(StrType, example = "Hello, world"),
    "entry" to FunctionParameter(StrType, example = "world"),
    "replacement" to FunctionParameter(StrType, example = "everyone")
) {
    override fun onInvoke(arguments: FunctionArguments): String {
        val string = arguments["string", StrType]
        val entry = arguments["entry", StrType]
        val replacement = arguments["replacement", StrType]

        return string.replace(entry, replacement)
    }
}