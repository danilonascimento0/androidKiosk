package com.learning.androidlearning.androidkiosk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {
    // This is for block android buttons (Back, Home and Last used)
    override fun onReceive(context: Context, intent: Intent) {
        val startServiceIntent = Intent(context, MainActivity::class.java)
        context.startService(startServiceIntent)
    }
}