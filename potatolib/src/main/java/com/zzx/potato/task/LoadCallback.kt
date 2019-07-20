package com.zzx.potato.task

import com.zzx.potato.ImageRequest

interface LoadCallback {

    fun preLoad(imageRequest: ImageRequest)

    fun onProcess(imageRequest: ImageRequest, process: Int)

    fun onComplete(imageRequest: ImageRequest)

    fun onError(imageRequest: ImageRequest, errorMsg: String)
}