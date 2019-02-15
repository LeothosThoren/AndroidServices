package com.leothos.servicesdemo.services

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.util.Log

class MyStartedService : Service() {

    companion object {
        val TAG: String = MyStartedService::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate, Thread name: ${Thread.currentThread().name}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand, Thread name: ${Thread.currentThread().name}")


        val sleep = intent?.getIntExtra("SleepTime", 1)

        val myAsync = MyAsyncTask()
        myAsync.execute(sleep)

        // Only short duration task => don't block the UI
        // 3 flags (start sticky, start redeliver intent, start no sticky)
        return START_STICKY

    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG, "onBind, Thread name: ${Thread.currentThread().name}")
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy, Thread name: ${Thread.currentThread().name}")

    }

    //----------------------
    // Async Task
    //----------------------

    class MyAsyncTask : AsyncTask<Int, String, String>() {

        companion object {
            val TAG: String = MyAsyncTask::class.java.simpleName
        }


        override fun onPreExecute() {
            super.onPreExecute()
            Log.i(TAG, "onPreExecute, Thread name: ${Thread.currentThread().name}")
        }

        override fun doInBackground(vararg params: Int?): String? { //do long Running task
            Log.i(TAG, "doInBackground, Thread name: ${Thread.currentThread().name} + ${params[0]}")

            val sleepTime = params[0]
            var inc = 1

            if (sleepTime != null) {
                while (inc <= sleepTime) {
                    publishProgress("Counter is now $inc")
                    Thread.sleep(1000)
                    inc++
                }
            }


            return "Counter stopped at $inc seconds"
        }

        override fun onProgressUpdate(vararg values: String?) {
            super.onProgressUpdate(*values)
            Log.i(TAG, "Counter value: ${values[0]}, onProgressUpdate, Thread name: ${Thread.currentThread().name}")

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.i(TAG, "onPostExecute, Thread name: ${Thread.currentThread().name}")

            val intent = Intent("action.service.to.activity")
            intent.putExtra("startServiceResult", result)
        }

    }
}