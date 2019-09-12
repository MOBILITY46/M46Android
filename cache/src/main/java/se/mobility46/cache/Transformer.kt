package se.mobility46.cache

import com.appmattus.layercache.Cache
import com.appmattus.layercache.TwoWayTransform
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

internal class Transformer<V : Any, T : Entry<V>>(private val serializer: KSerializer<T>) :
    TwoWayTransform<String, T> {

    override fun inverseTransform(mappedValue: T): String {
        return Json.stringify(serializer, mappedValue)
    }

    override fun transform(value: String): T {
        return Json.parse(serializer, value)
    }

}

fun <K : Any, V : Any, T : Entry<V>> Cache<K, String>.serializer(serializer: KSerializer<T>) =
    this.valueTransform(Transformer(serializer))

inline fun <K : Any, V : Any, reified T : Entry<V>> Cache<K, String>.serializer() =
    serializer(T::class.serializer())
