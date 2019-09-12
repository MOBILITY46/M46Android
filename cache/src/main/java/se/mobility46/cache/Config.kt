package se.mobility46.cache

import java.io.File

data class Config<T>(val directory: File, val maxSize: Long, val transformer: Transformer<T>)
