package com.example.liyayu.myapplication.util

import android.os.Process
import android.util.Log
import java.lang.Thread.UncaughtExceptionHandler
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by liyayu on 2018/12/26.
 * 线程相关操作
 */

/**
 * 新建线程时尽量使用线程池来管理线程的操作，不要在代码中显式创建线程new Thread...
 */
fun startPoolThread(r: Runnable) {
    val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    val KEEP_ALIVE_TIME: Long = 120
    val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    val taskQueue = LinkedBlockingDeque<Runnable>()
    val executorService = ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT, taskQueue, BackgroundThreadFactory(), ThreadPoolExecutor.AbortPolicy())//AbortPolicy 当要创建的线程数量大于线程池的最大线程数的时候，新的任务就会被拒绝

    executorService.execute(r)
}

class BackgroundThreadFactory : ThreadFactory {
    private val sTag = 1

    override fun newThread(runnable: Runnable): Thread {
        val thread = Thread(runnable)
        thread.name = "CustomThread$sTag"
        thread.priority = Process.THREAD_PRIORITY_BACKGROUND

        // A exception handler is created to log the exception from threads
        thread.uncaughtExceptionHandler = UncaughtExceptionHandler { tr: Thread, throwable: Throwable ->
            Log.e("BackgroundThreadFactory", tr.name + " encountered an error: " + throwable.message)
        }
        return thread
    }
}
