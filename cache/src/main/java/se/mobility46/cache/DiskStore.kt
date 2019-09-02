package se.mobility46.cache

import se.mobility46.cache.lib.Entry
import se.mobility46.cache.lib.Expiry

class DiskStore<T> : StoreAware<T> {

    override fun entry(key: String): Entry<T>? {
        TODO("not implemented")
    }

    override fun add(item: T, key: String, expiry: Expiry?) {
        TODO("not implemented")
    }

    override fun remove(key: String) {
        TODO("not implemented")
    }

    override fun removeAll() {
        TODO("not implemented")
    }

    override fun removeExpired() {
        TODO("not implemented")
    }

}