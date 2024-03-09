package quark.lang.types.builtin

import quark.lang.objects.QrkArray
import quark.lang.objects.createQrkArray
import quark.lang.types.Type

object ArrayType : Type<QrkArray>(
    QrkArray::class,
    example = createQrkArray(StrType, "apple", "banana", "strawberry"),
    columnNameExample = "favorite fruits"
) {
    override fun serialize(nativeObject: QrkArray): String {
        return nativeObject.joinToString(", ", "[", "]")
    }

    override fun safeCast(nativeObject: Any): QrkArray? {
        return null
    }
}