package com.leothos.servicesdemo.receivers

import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.widget.TextView
import com.leothos.servicesdemo.services.MyIntentService

//******************
// RESULT RECEIVER
//******************
class MyResultReceiver(val handler: Handler?, val tv: TextView?) : ResultReceiver(handler) {

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        super.onReceiveResult(resultCode, resultData)

        Log.i("MyResultReceiver", Thread.currentThread().name)

        if (resultCode == MyIntentService.RESULT_CODE_BUNDLE && resultData != null) {
            val result: String? = resultData.getString(MyIntentService.BUNDLE_KEY)

            handler?.post {
                Log.i("MyHandler", Thread.currentThread().name)

                tv?.text = result
            }

        }
    }
}