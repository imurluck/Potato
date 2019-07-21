package com.zzx.potato.task

import com.zzx.potato.ImageRequest

open abstract class LoadTask(val imageRequest: ImageRequest,
                             val source: Source): Runnable {

    override fun run() {
        loadImage(imageRequest, source)
        imageRequest.onLoadComplete(source)
    }


    abstract fun loadImage(imageRequest: ImageRequest, source: Source)
}