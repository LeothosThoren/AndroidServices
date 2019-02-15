package com.leothos.servicesdemo

import android.app.Application
import android.widget.Toast

fun Application.toast(msg: String) = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)