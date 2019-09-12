package se.mobility46.cache

import com.appmattus.layercache.TwoWayTransform

interface Transformer<T>: TwoWayTransform<String, T> {

    fun serialize(value: T): String
    fun deserialize(value: String): T

    override fun inverseTransform(mappedValue: T): String {
        return serialize(mappedValue)
    }

    override fun transform(value: String): T {
        return deserialize(value)
    }
}


