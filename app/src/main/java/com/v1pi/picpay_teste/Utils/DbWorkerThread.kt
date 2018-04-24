package com.v1pi.picpay_teste.Utils

import android.os.Handler
import android.os.HandlerThread
import android.os.Message

class DbWorkerThread(threadName : String) : HandlerThread(threadName) {
    private lateinit var handler : Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = Handler()
    }

    fun postTask(task : Runnable) {
        handler.post(task)
    }
}