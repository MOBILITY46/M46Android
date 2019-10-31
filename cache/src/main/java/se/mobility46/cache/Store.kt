package se.mobility46.cache

import com.appmattus.layercache.Cache
import com.appmattus.layercache.createDiskLruCache
import com.appmattus.layercache.createLruCache

class Store<T : Any>(config: Config<T>) : StoreAware<T> {

    private var cache: Cache<String, T>

    init {
        val mem = Cache.createLruCache<String, T>(config.maxSize.toInt())
        val disk = Cache.createDiskLruCache(config.directory, config.maxSize)
            .valueTransform(config.transformer)

        cache = disk.compose(mem)
    }

    override suspend fun entry(key: String): T? {
        return cache.get(key).await()
    }

    override suspend fun add(key: String, item: T) {
        cache.set(key, item).await()
    }

    override suspend fun remove(key: String) {
        cache.evict(key).await()
    }

    override suspend fun removeAll() {
        cache.evictAll().await()
    }
}
