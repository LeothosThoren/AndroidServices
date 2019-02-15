package com.leothos.servicesdemo.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.leothos.servicesdemo.R
import com.leothos.servicesdemo.services.MyMessengerService
import kotlinx.android.synthetic.main.activity_messenger.*

class MessengerActivity : AppCompatActivity() {

    var messengerBound = false
    lateinit var service: Messenger

    private val messengerConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            messengerBound = false
        }

        override fun onServiceConnected(name: ComponentName?, ibinder: IBinder?) {
            service = Messenger(ibinder)
            messengerBound = true
            Log.i("MyMessengerActivity", "onServiceConnected $service")


        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
    }

    fun performAddOperation(v: View) {
        Log.i("MyMessengerActivity", "performAddOperation ok")

       val num1 = edNumOne.text.toString().toInt()
       val num2 = edNumTwo.text.toString().toInt()

        val msgToService: Message = Message.obtain(null, 43)

        val bundle = Bundle()
        bundle.putInt("numOne", num1)
        bundle.putInt("numTwo", num2)

        msgToService.data = bundle

        try {
            service.send(msgToService)
            Log.i("MyService", "show me service = $service")
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

        Log.i("MyMessengerActivity", "msg to service = ${msgToService.describeContents()}")


    }

    fun bindService(v: View) {
        Log.i("MyMessengerActivity", "bindService ok")
        val intent = Intent(this@MessengerActivity, MyMessengerService::class.java)
        bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE)

    }

    fun unbindService(v: View) {
        Log.i("MyMessengerActivity", "unbindService ok")

        if (messengerBound) {
            unbindService(messengerConnection)
            messengerBound = false
        }

    }
}
