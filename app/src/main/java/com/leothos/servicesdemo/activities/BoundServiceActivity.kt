package com.leothos.servicesdemo.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.leothos.servicesdemo.R
import com.leothos.servicesdemo.services.MyBoundService
import kotlinx.android.synthetic.main.activity_bound_service.*

class BoundServiceActivity : AppCompatActivity() {

    var isBound = false
    private val boundService = MyBoundService()


    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            val localBinder: MyBoundService.MyLocalBinder = iBinder as MyBoundService.MyLocalBinder
            localBinder.getService()
            isBound = true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound_service)
    }

    override fun onStart() {
        super.onStart()

        val intent = Intent(this@BoundServiceActivity, MyBoundService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
    }

    fun onClickEvent(v: View) {

        val numOne = editTextNumOne.text.toString().toInt()
        val numTwo = editTextNumTwo.text.toString().toInt()
        var result = ""

        if (isBound) {
            when (v.id) {
                R.id.buttonAdd -> result = boundService.add(numOne, numTwo).toString()
                R.id.buttonSub -> result = boundService.subtracts(numOne, numTwo).toString()
                R.id.buttonMult -> result = boundService.multiply(numOne, numTwo).toString()
                R.id.buttonDiv -> result = boundService.divide(numOne, numTwo).toString()
                else -> Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()

            }
        }

        textViewResult.text = result

    }
}

