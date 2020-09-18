@file:Suppress("UNCHECKED_CAST")

package se.mobility46.lib


/**
 * A Result monad inspired by Rust
 */

sealed class Result<T> {

    companion object {
        operator fun <T> invoke(func: () -> T): Result<T> =
            try {
                Ok(func())
            } catch (e: Exception) {
                Err(e)
            }
    }

    abstract fun <R> map(transform: (T) -> R): Result<R>
    abstract fun <R> flatMap(func: (T) -> Result<R>): Result<R>
}

class Ok<T>(val value: T) : Result<T>() {

    override fun <R> map(transform: (T) -> R): Result<R> = Result { transform(value) }

    override fun <R> flatMap(func: (T) -> Result<R>): Result<R> =
        Result { func(value) }.let {
            when (it) {
                is Ok -> it.value
                is Err -> it as Result<R>
            }
        }
}

class Err<T>(val error: Exception) : Result<T>() {
    override fun <R> map(transform: (T) -> R): Result<R> = this as Result<R>
    override fun <R> flatMap(func: (T) -> Result<R>): Result<R> = this as Result<R>
}