package com.leothos.servicesdemo.services

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log

class MyIntentService : IntentService("Thread intent worker") {

    companion object {
        val TAG: String = MyIntentService::class.java.simpleName
        const val RESULT_CODE_BUNDLE = 18
        const val BUNDLE_KEY = "myResultIntentService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate, Thread name ${Thread.currentThread().name}")

    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i(TAG, "onHandleIntent, Thread name ${Thread.currentThread().name}")


        val sleepTime = intent?.getIntExtra("SleepTime", 1)
        val resultReceiver: ResultReceiver? = intent?.getParcelableExtra("receiver")

        var inc = 1

        while (inc <= sleepTime!!) {
            Log.i(TAG, "Counter is now : $inc")
            Thread.sleep(1000)
            inc++
        }

        val bundle = Bundle()
        bundle.putString("myResultIntentService", "Counter stop at $inc seconds")
        resultReceiver?.send(RESULT_CODE_BUNDLE, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy, Thread name ${Thread.currentThread().name}")

    }
}