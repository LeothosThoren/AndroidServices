package com.leothos.servicesdemo.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.leothos.servicesdemo.R
import com.leothos.servicesdemo.receivers.MyResultReceiver
import com.leothos.servicesdemo.services.MyIntentService
import com.leothos.servicesdemo.services.MyStartedService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    //***************
    //ACTIONS
    //***************

    fun startService(v: View) {

        val intent = Intent(this@MainActivity, MyStartedService::class.java)
        intent.putExtra("SleepTime", 10)
        startService(intent)
    }

    fun stopService(v: View) {
        val intent = Intent(this@MainActivity, MyStartedService::class.java)
        stopService(intent)
    }

    fun moveToSecondActivity(v: View) {
        val intent = Intent(this@MainActivity, BoundServiceActivity::class.java)
        startActivity(intent)
    }

    fun moveToMessengerActivity(v: View) {
        val intent = Intent(this@MainActivity, MessengerActivity::class.java)
        startActivity(intent)
    }

    //**************
    //SERVICE
    //**************

    // Broadcast receiver
    private val myStartedServiceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val str = intent?.getStringExtra("startServiceResult")
            textViewStartdServiceResult.text = str
            sendBroadcast(intent)
        }
    }

    //Result receiver
    fun startIntentService(v: View) {
        val myResultReceiver: ResultReceiver =
            MyResultReceiver(handler, textViewIntentResult)

        val intent = Intent(this@MainActivity, MyIntentService::class.java)
        intent.putExtra("SleepTime", 10)
        intent.putExtra("receiver", myResultReceiver)
        startService(intent)
    }

    //*******************
    //OVERRIDE FUNCTIONS
    //*******************

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("action.service.to.activity")
        registerReceiver(myStartedServiceReceiver, intentFilter)

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myStartedServiceReceiver)
        Log.d("MyMainActivity onPause", "${myStartedServiceReceiver.resultCode}")
    }

}
