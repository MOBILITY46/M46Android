package se.mobility46.cache

import com.appmattus.layercache.TwoWayTransform

interface Transformer<T>: TwoWayTransform<String, T>
