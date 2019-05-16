package com.learning.androidlearning.androidkiosk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    var kiosk: Kiosk = Kiosk(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txAndroidVersion.text = Build.VERSION.SDK_INT.toString()
        txAndroidRelease.text = Build.VERSION.RELEASE.toString()

        btKiosk.setOnClickListener {
            setKioskMode()
        }
    }

    fun closeApplication(@Suppress("UNUSED_PARAMETER") view: View) {
        moveTaskToBack(true)
        exitProcess(-1)
    }

    private fun setKioskMode() {
        kiosk.startKioskMode()
        btKiosk.text = "Desativar Kiosk"

        btKiosk.setOnClickListener {
            removeKioskMode()
        }
    }

    private fun removeKioskMode() {
        kiosk.stopKioskMode()
        btKiosk.text = "Ativar Kiosk"

        btKiosk.setOnClickListener {
            setKioskMode()
        }
    }

    //override fun onWindowFocusChanged(hasFocus: Boolean) {
    //    super.onWindowFocusChanged(hasFocus)
    //    if (hasFocus) hideSystemUI()
    //}

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
