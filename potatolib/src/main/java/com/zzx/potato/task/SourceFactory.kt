package com.zzx.potato.task

import com.zzx.potato.ImageRequest

object SourceFactory {

    fun createNetworkSource(imageRequest: ImageRequest): NetworkSource {
        return NetworkSource()
    }
}