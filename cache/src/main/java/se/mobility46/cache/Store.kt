package se.mobility46.cache

import com.appmattus.layercache.Cache
import com.appmattus.layercache.createDiskLruCache
import com.appmattus.layercache.createLruCache

class Store<T: Any>(config: Config): StoreAware<T> {

    private var cache: Cache<String, Entry<T>>

    init {
        val mem = Cache.createLruCache<String, Entry<T>>(config.maxSize.toInt())
        val disk = Cache.createDiskLruCache(config.directory, config.maxSize)
            .valueTransform(Transformer<T>())

        cache = mem.compose(disk)
    }

    override suspend fun entry(key: String): Entry<T>? {
        return cache.get(key).await()
    }

    override suspend fun add(key: String, item: T) {
        cache.set(key, Entry(item)).await()
    }

    override suspend fun remove(key: String) {
        cache.evict(key).await()
    }

    override suspend fun removeAll() {
        cache.evictAll().await()
    }

}
