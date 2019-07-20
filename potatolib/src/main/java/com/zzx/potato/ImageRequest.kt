package com.zzx.potato

import android.support.annotation.IntDef
import org.jetbrains.annotations.NonNls


class ImageRequest {



    @IntDef(STATE_PRE_REQUEST, STATE_REQUESTING, STATE_FINISHED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State {

    }

    companion object {
        const val STATE_PRE_REQUEST = 0
        const val STATE_REQUESTING = 1
        const val STATE_FINISHED = 2
    }

}