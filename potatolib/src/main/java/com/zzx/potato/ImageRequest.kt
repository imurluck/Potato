package com.zzx.potato

import android.support.annotation.DrawableRes
import android.support.annotation.IntDef
import android.text.TextUtils
import org.jetbrains.annotations.NonNls


class ImageRequest(val imageUrl: String = "",
                   @DrawableRes val imageResourcesId: Int = -1) {

    var readTimeout = DEFAULT_READ_TIMEOUT
    var requestMethod = DEFAULT_METHOD

    @LoadWay var imageLoadWay: Int = IMAGE_LOAD_WAY_NETWORK

    init {
        checkImageLoadWay()
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

        private const val IMAGE_LOAD_WAY_RESOURCES = 0
        private const val IMAGE_LOAD_WAY_LOCAL = 1
        private const val IMAGE_LOAD_WAY_NETWORK = 2

        private const val DEFAULT_READ_TIMEOUT = 10000
        private const val DEFAULT_METHOD = "GET"
    }

}