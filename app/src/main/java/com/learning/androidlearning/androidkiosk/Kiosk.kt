package com.learning.androidlearning.androidkiosk

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager

class Kiosk(context: Context) : AppCompatActivity() {
    private var context: Context = context
    private var viewBlockingStatus: CustomViewGroup? = null

    fun startKioskMode() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            this.blockStatusBar()
        }
    }

    fun stopKioskMode() {
        // removing blocking for status bar
        (context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(viewBlockingStatus)
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
        localLayoutParams.height = (30 * context.resources.displayMetrics.scaledDensity).toInt()
        localLayoutParams.format = PixelFormat.TRANSPARENT

        blockSuperiorStatusBar(localLayoutParams)
        //blockBottonStatusBar(manager, localLayoutParams)
    }

    private fun blockSuperiorStatusBar(localLayoutParams: WindowManager.LayoutParams) {
        localLayoutParams.gravity = Gravity.TOP
        viewBlockingStatus = CustomViewGroup(this.context)

        (context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager).addView(viewBlockingStatus, localLayoutParams)
    }

    /*private fun blockBottonStatusBar(manager: WindowManager, localLayoutParams: WindowManager.LayoutParams) {
        localLayoutParams.gravity = Gravity.BOTTOM
        val view = CustomViewGroup(this)
        manager.addView(view, localLayoutParams)
    }*/

    fun hideSystemUI(window: Window) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    /*fun showSystemUI(window: Window) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }*/
}