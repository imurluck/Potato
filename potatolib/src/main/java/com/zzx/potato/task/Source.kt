package com.zzx.potato.task

import android.graphics.Bitmap
import java.io.Closeable
import java.io.InputStream

abstract class Source : Closeable {

    abstract fun readWhole(`is`: InputStream): Boolean

    abstract fun read(byte: ByteArray, length: Int, totalLength: Int)

    abstract fun getBitmap(): Bitmap?

    companion object {

        internal const val ERROR_CODE_FILE_TOO_LARGE = 0x01

        private const val ERROR_FILE_TOO_LARGE = "error image file is larger than ${Integer.MAX_VALUE / 1024 / 1024} M"


    }

}