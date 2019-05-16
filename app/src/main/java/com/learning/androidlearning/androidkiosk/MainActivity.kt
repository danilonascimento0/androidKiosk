package com.learning.androidlearning.androidkiosk

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.Gravity
import android.graphics.PixelFormat
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blockStatusBar()
    }

    @Suppress("DEPRECATION")
    private fun blockStatusBar() {
        val localLayoutParams = WindowManager.LayoutParams()
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        localLayoutParams.height = (30 * resources .displayMetrics.scaledDensity).toInt()
        localLayoutParams.format = PixelFormat.TRANSPARENT

        val manager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        blockSuperiorStatusBar(manager, localLayoutParams)
        //blockBottonStatusBar(manager, localLayoutParams)
    }

    private fun blockSuperiorStatusBar(manager: WindowManager, localLayoutParams: WindowManager.LayoutParams) {
        localLayoutParams.gravity = Gravity.TOP
        val view = CustomViewGroup(this)
        manager.addView(view, localLayoutParams)
    }

    private fun blockBottonStatusBar(manager: WindowManager, localLayoutParams: WindowManager.LayoutParams) {
        localLayoutParams.gravity = Gravity.BOTTOM
        val view = CustomViewGroup(this)
        manager.addView(view, localLayoutParams)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
