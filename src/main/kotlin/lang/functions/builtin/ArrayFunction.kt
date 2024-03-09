package quark.lang.functions.builtin

import quark.lang.functions.Function
import quark.lang.functions.FunctionArguments
import quark.lang.functions.FunctionParameter
import quark.lang.objects.QrkArray
import quark.lang.objects.createQrkArray
import quark.lang.types.builtin.ArrayType
import quark.lang.types.builtin.StrType

object ArrayFunction : Function<QrkArray>(
    ArrayType,
    "elements" to FunctionParameter(ArrayType, varargs = true, example = createQrkArray(StrType, "apples", "bananas", "peach"))
) {
    override fun onInvoke(arguments: FunctionArguments): QrkArray {
        return arguments["elements", ArrayType]
    }
}