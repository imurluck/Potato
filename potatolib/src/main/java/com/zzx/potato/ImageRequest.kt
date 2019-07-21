package com.zzx.potato

import android.os.Handler
import android.os.Looper
import android.support.annotation.DrawableRes
import android.support.annotation.IntDef
import android.text.TextUtils
import android.widget.ImageView
import com.zzx.potato.task.LoadTask
import com.zzx.potato.task.NetworkLoadTask
import com.zzx.potato.task.Source
import com.zzx.potato.task.SourceFactory
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull


class ImageRequest(val imageView: ImageView) {

    var isCanceled = false

    var readTimeout = DEFAULT_READ_TIMEOUT
    var requestMethod = DEFAULT_METHOD

    var imageUrl: String = ""
    @DrawableRes var imageResourcesId: Int = -1

    @LoadWay var imageLoadWay: Int = IMAGE_LOAD_WAY_NETWORK

    fun url(@NotNull imageUrl: String): ImageRequest {
        this.imageUrl = imageUrl
        return this
    }

    fun load() {
        checkImageLoadWay()
        TaskDispatcher.addRequest(getLoadTask())
    }

    private fun getLoadTask(): LoadTask {
        return when (imageLoadWay) {
            IMAGE_LOAD_WAY_NETWORK -> NetworkLoadTask(this, SourceFactory.createNetworkSource(this))
            else -> {
                //TODO replace when other way created
                NetworkLoadTask(this, SourceFactory.createNetworkSource(this))
            }
        }
    }


    private fun checkImageLoadWay() {
        imageLoadWay = if (imageResourcesId > 0) {
            IMAGE_LOAD_WAY_RESOURCES
        } else if (!TextUtils.isEmpty(imageUrl) && imageUrl.startsWith(HTTP_PREFIX, true)
            || imageUrl.startsWith(HTTPS_PREFIX, true)) {
            IMAGE_LOAD_WAY_NETWORK
        } else {
            IMAGE_LOAD_WAY_LOCAL
        }
    }

    fun onLoadComplete(source: Source) {
        Handler(Looper.getMainLooper()).post {
            imageView.setImageBitmap(source.getBitmap())
        }
    }

    @IntDef(STATE_PRE_REQUEST, STATE_REQUESTING, STATE_FINISHED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State {

    }

    @IntDef(IMAGE_LOAD_WAY_RESOURCES, IMAGE_LOAD_WAY_LOCAL, IMAGE_LOAD_WAY_NETWORK)
    @Retention(AnnotationRetention.SOURCE)
    annotation class LoadWay {

    }

    companion object {

        private const val HTTP_PREFIX = "http"
        private const val HTTPS_PREFIX = "https"

        const val STATE_PRE_REQUEST = 0
        const val STATE_REQUESTING = 1
        const val STATE_FINISHED = 2

        const val IMAGE_LOAD_WAY_RESOURCES = 0
        const val IMAGE_LOAD_WAY_LOCAL = 1
        const val IMAGE_LOAD_WAY_NETWORK = 2

        private const val DEFAULT_READ_TIMEOUT = 10000
        private const val DEFAULT_METHOD = "GET"
    }

}