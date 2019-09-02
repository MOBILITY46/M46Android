package se.mobility46.cache

import se.mobility46.cache.lib.Entry
import se.mobility46.cache.lib.Expiry

interface StoreAware<T> {

    fun entry(key: String): Entry<T>?

    fun add(item: T, key: String, expiry: Expiry?)

    fun remove(key: String)

    fun removeAll()

    fun removeExpired()

    fun expired(key: String): Boolean = entry(key)?.expiry?.isExpired ?: true

    fun exists(key: String): Boolean = entry(key) != null

}

interface AsyncStoreAware<T> {

    fun entry(key: String, completion: (Entry<T>?) -> Unit)

    fun add(item: T, key: String, expiry: Expiry?, completion: () -> Unit)

    fun remove(key: String, completion: () -> Unit)

    fun removeAll(completion: () -> Unit)

    fun removeExpired(completion: () -> Unit)

    fun expired(key: String, completion: (Boolean) -> Unit) {
        entry(key) {
            if (it != null) {
                completion(it.expiry.isExpired)
            } else {
                completion(true)
            }
        }
    }

    fun exists(key: String, completion: (Boolean) -> Unit) {
        entry(key) { completion(it != null) }
    }

}
