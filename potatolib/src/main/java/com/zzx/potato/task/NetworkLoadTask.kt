package com.zzx.potato.task

import com.zzx.potato.ImageRequest
import com.zzx.potato.util.ERROR_FILE_TOO_LARGE
import com.zzx.potato.util.ERROR_UNKNOWN
import com.zzx.potato.util.Logger
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

open class NetworkLoadTask(private val imageRequest: ImageRequest,
                           private val loadCallback: LoadCallback?): LoadTask() {


    override fun loadImage(): InputStream {
        return FileInputStream("test")
    }

    private fun responseCodeSuccess(responseCode: Int) = responseCode / 100 == 2

    private fun checkFileLength(totalContentLength: Int) = totalContentLength != -1

    override fun run() {
        try {
            val url = URL(imageRequest.imageUrl)
            (url.openConnection() as HttpURLConnection).apply {
                readTimeout = imageRequest.readTimeout
                requestMethod = imageRequest.requestMethod
                doInput = true
                //return success code 200,201,202,203...
                if (responseCodeSuccess(responseCode)) {
                    val totalLength = contentLength
                    if (!checkFileLength(totalLength)) {
                        loadCallback?.onError(imageRequest, ERROR_FILE_TOO_LARGE)
                        return
                    }
                    val `is` = inputStream
                    val buffer = ByteArray(1024)
                    var len = 0
                    while (`is`.read(buffer).apply {
                            len = this
                        } != -1) {
                        loadCallback?.onProcess(imageRequest, ((len.toFloat() / totalLength) * 100).toInt())

                    }
                } else {
                    loadCallback?.onError(imageRequest, responseMessage)
                }
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            Logger.e(TAG, e.message ?: ERROR_UNKNOWN)
        } catch (e: IOException) {
            e.printStackTrace()
            Logger.e(TAG, e.message ?: ERROR_UNKNOWN)
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.e(TAG, e.message ?: ERROR_UNKNOWN)
        }

    }

    private companion object {
        private const val TAG = "NetworkLoadTask"
    }


}