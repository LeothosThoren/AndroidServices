package com.leothos.servicesdemo.services

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.leothos.servicesdemo.toast

class MyMessengerService : Service() {

    private val messenger = Messenger(IncomingHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return messenger.binder
    }

    fun addNumbers(a: Int, b: Int): Int = a + b

    // CLASS Handler
    class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {
            Log.i("MyMessengerService", "handleMessage = ${msg.data}, message.what = ${msg.what}")
            if (msg.what == 43) {
                val bundle = msg.data
                val num1 = bundle.getInt("numOne", 1)
                val num2 = bundle.getInt("numTwo", 1)

                val result = MyMessengerService().addNumbers(num1, num2)
                Log.i("MyMessengerService", "result = $result")
                Application().toast("The result is $result")

            } else super.handleMessage(msg)
        }

    }
}
