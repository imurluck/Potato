package com.zzx.potato.task

import com.zzx.potato.ImageRequest
import com.zzx.potato.util.ERROR_UNKNOWN
import com.zzx.potato.util.Logger
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

open class NetworkLoadTask(
    imageRequest: ImageRequest,
    source: NetworkSource
) : LoadTask(imageRequest, source) {
    override fun loadImage(imageRequest: ImageRequest, source: Source) {
        source as NetworkSource
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
                        source.responseError(imageRequest, Source.ERROR_CODE_FILE_TOO_LARGE)
                        return
                    }
                    val `is` = inputStream
                    if (source.readWhole(`is`)) {
                        return
                    }
                    val buffer = ByteArray(1024)
                    var len = 0
                    while (`is`.read(buffer).apply {
                            len = this
                        } != -1) {
                        source.read(buffer, len, totalLength)
                    }
                } else {
                    source.responseError(imageRequest, responseCode)
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


    private fun responseCodeSuccess(responseCode: Int) = responseCode / 100 == 2

    private fun checkFileLength(totalContentLength: Int) = totalContentLength != -1

    private companion object {
        private const val TAG = "NetworkLoadTask"
    }


}