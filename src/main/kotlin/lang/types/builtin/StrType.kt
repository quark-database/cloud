package quark.lang.types.builtin

import quark.lang.types.Type
import quark.util.strings.sanitizeEscapes

object StrType : Type<String>(
    String::class,
    example = "Hello, world!",
    columnNameExample = "user name"
) {

    override fun serialize(nativeObject: String): String {
        return """"${nativeObject.sanitizeEscapes()}""""
    }

    override fun safeCast(nativeObject: Any): String {
        return nativeObject.toString()
    }
}