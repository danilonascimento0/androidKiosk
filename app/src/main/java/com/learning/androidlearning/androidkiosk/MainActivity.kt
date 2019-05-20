package com.learning.androidlearning.androidkiosk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    var kiosk: Kiosk = Kiosk(this)
    //var isKioskActive: Boolean = false

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            txAndroidVersion.text = Build.VERSION.SDK_INT.toString()
            txAndroidRelease.text = Build.VERSION.RELEASE.toString()

            kiosk.hideSystemUI(window)
            setKioskMode()
        }

        private fun setKioskMode() {
            kiosk.startKioskMode()
            btKiosk.text = "Desativar Kiosk"
            txAtivo.text = "ATIVO"
            //isKioskActive = true

        btKiosk.setOnClickListener {
            removeKioskMode()
        }
    }

    private fun removeKioskMode() {
        kiosk.stopKioskMode()
        btKiosk.text = "Ativar Kiosk"
        txAtivo.text = "INATIVO"
        //isKioskActive = false

        btKiosk.setOnClickListener {
            setKioskMode()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            //if (isKioskActive) {
                kiosk.hideSystemUI(window)
            //} else {
                //kiosk.showSystemUI(window)
            //}
        }
    }

    fun closeApplication(@Suppress("UNUSED_PARAMETER") view: View) {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
