package com.zzx.potato.task

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.zzx.potato.ImageRequest
import java.io.InputStream

class NetworkSource: Source() {

    private var bitmap: Bitmap? = null

    override fun readWhole(`is`: InputStream): Boolean {
        bitmap = BitmapFactory.decodeStream(`is`)
        return true
    }

    fun readTimeout(imageRequest: ImageRequest) {

    }

    override fun read(byte: ByteArray, length: Int, totalLength: Int) {

    }

    fun responseError(imageRequest: ImageRequest, errorCode: Int) {

    }

    override fun getBitmap(): Bitmap? = bitmap

    override fun close() {

    }
}