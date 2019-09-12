package se.mobility46.cache

import kotlinx.serialization.Serializable
import java.util.*

fun now(): Date = Date()

@Serializable
data class Entry<T>(val item: T, val timestamp: Long = now().time)