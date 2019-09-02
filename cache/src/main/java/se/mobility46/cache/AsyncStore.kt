package se.mobility46.cache

import se.mobility46.cache.lib.Entry
import se.mobility46.cache.lib.Expiry

class AsyncStore<T>(val inner: StoreAware<T>) : AsyncStoreAware<T> {

    override fun entry(key: String, completion: (Entry<T>?) -> Unit) {
        TODO("not implemented")
    }

    override fun add(item: T, key: String, expiry: Expiry?, completion: () -> Unit) {
        TODO("not implemented")
    }

    override fun remove(key: String, completion: () -> Unit) {
        TODO("not implemented")
    }

    override fun removeAll(completion: () -> Unit) {
        TODO("not implemented")
    }

    override fun removeExpired(completion: () -> Unit) {
        TODO("not implemented")
    }

}