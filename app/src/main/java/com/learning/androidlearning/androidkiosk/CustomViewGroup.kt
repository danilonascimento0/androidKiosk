package com.learning.androidlearning.androidkiosk

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup

class CustomViewGroup(context: Context) : ViewGroup(context) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.v("customViewGroup", "**********Intercepted")
        return true
    }
}