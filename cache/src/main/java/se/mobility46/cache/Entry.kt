package se.mobility46.cache

import java.util.*

fun now(): Date = Date()

data class Entry<T>(val item: T, val timestamp: Date = now())