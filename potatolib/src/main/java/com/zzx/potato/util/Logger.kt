package com.zzx.potato.util

import android.util.Log
import com.zzx.potato.BuildConfig
import kotlin.math.log

object Logger {
    /**
     * log e
     */
    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    /**
     * log d
     */
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }
}