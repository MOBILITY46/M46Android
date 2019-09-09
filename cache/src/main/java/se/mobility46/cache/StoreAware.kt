package se.mobility46.cache

internal interface StoreAware<T: Any> {

    suspend fun entry(key: String): Entry<T>?

    suspend fun add(key: String, item: T)

    suspend fun remove(key: String)

    suspend fun removeAll()

    suspend fun exists(key: String): Boolean = entry(key) != null

}