package com.learning.androidlearning.androidkiosk

import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
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

    fun openCalculator(@Suppress("UNUSED_PARAMETER") view: View) {
        val items = ArrayList<HashMap<String, Any>>()
        val packs = packageManager.getInstalledPackages(0)
        for (pi in packs) {
            if (pi.packageName.toString().toLowerCase().contains("calculator")) {
                val map = HashMap<String, Any>()
                map["appName"] = pi.applicationInfo.loadLabel(packageManager)
                map["packageName"] = pi.packageName
                items.add(map)
            }
        }

        if (items.size >= 1) {
            val packageName = items[0]["packageName"] as String
            val i = packageManager.getLaunchIntentForPackage(packageName)
            if (i != null)
                startActivity(i)
        } else {
            // Application not found
        }
    }

    fun openBrowser(@Suppress("UNUSED_PARAMETER") view: View) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.tutorialkart.com/")
        startActivity(openURL)
    }

    fun closeBackgroundApplication(@Suppress("UNUSED_PARAMETER") view: View) {
        // TODO
    }

    fun closeApplication(@Suppress("UNUSED_PARAMETER") view: View) {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
