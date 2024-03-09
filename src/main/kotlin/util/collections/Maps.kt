package quark.util.collections

fun <K, V> Map<K, V>.flip(): Map<V, K> =
    entries.associate { (key, value) -> value to key }