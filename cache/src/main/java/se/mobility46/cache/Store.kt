package se.mobility46.cache

import com.appmattus.layercache.Cache
import com.appmattus.layercache.createDiskLruCache
import com.appmattus.layercache.createLruCache

class Store<T: Any>(config: Config<T>): StoreAware<T> {

    private var cache: Cache<String, T>

    class Exception(override val message: String): java.lang.Exception()

    init {
        val mem = Cache.createLruCache<String, T>(config.maxSize.toInt())
        val disk = Cache.createDiskLruCache(config.directory, config.maxSize)
            .valueTransform(config.transformer)

        cache = disk.compose(mem)
    }

    override suspend fun entry(key: String): T? {
        try {
            return cache.get(key).await()
        } catch (e: java.lang.Exception) {
            throw Exception(e)
        }
    }

    override suspend fun add(key: String, item: T) {
        try {
            cache.set(key, item).await()
        } catch (e: java.lang.Exception) {
            throw Exception("Item could not be added")
        }
    }

    override suspend fun remove(key: String) {
        try {
            cache.evict(key).await()
        } catch (e: java.lang.Exception) {
            throw Exception("Item could not be removed")
        }
    }

    override suspend fun removeAll() {
        try {
            cache.evictAll().await()
        } catch (e: java.lang.Exception) {
            throw Exception("Items could not be removed")
        }
    }

}
