package com.zzx.potato.task

import java.io.InputStream

open abstract class LoadTask: Runnable {



    abstract fun loadImage(): InputStream
}