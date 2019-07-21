package com.zzx.potato

import com.zzx.potato.task.LoadTask
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object TaskDispatcher {

    private const val DEFAULT_THREAD_COUNT = 3

    private var isTaskRunning = false

    private val taskThreadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT)

    private val taskQueue: LinkedBlockingQueue<LoadTask> = LinkedBlockingQueue()

    internal fun addRequest(loadTask: LoadTask) {
        if (!isTaskRunning) {
            taskThreadPool.execute {
                startRequest()
            }
            Thread.sleep(200)
        }
        taskQueue.add(loadTask)
    }

    private fun startRequest() {
        isTaskRunning = true
        try {
            while (true) {
                val task = taskQueue.take()
                if (task.imageRequest.isCanceled) {
                    continue
                }
                taskThreadPool.execute(task)
            }
        } catch (e: Exception) {

        }
    }
}