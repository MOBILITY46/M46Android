package se.mobility46.cache

import com.appmattus.layercache.TwoWayTransform
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class Transformer<V>: TwoWayTransform<String, Entry<V>> {

    override fun inverseTransform(mappedValue: Entry<V>): String {
        return Gson().toJson(mappedValue)
    }

    override fun transform(value: String): Entry<V> {
        return Gson().fromJson(value, object: TypeToken<Entry<V>>() {}.type)
    }

}
