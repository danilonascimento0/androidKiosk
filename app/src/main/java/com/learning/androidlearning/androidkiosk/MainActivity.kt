package com.learning.androidlearning.androidkiosk

import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess
import android.annotation.TargetApi
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var kiosk: Kiosk = Kiosk(this)
    //var isKioskActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txAndroidVersion.text = Build.VERSION.SDK_INT.toString()
        txAndroidRelease.text = Build.VERSION.RELEASE.toString()

        checkPermission()

        kiosk.hideSystemUI(window)
        setKioskMode()
    }

    var ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission()
            } else {
                // Do as per your logic
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun setKioskMode() {
        kiosk.startKioskMode()
        btKiosk.text = "Desativar Kiosk"
        //isKioskActive = true

        btKiosk.setOnClickListener {
            removeKioskMode()
        }
    }

    private fun removeKioskMode() {
        kiosk.stopKioskMode()
        btKiosk.text = "Ativar Kiosk"
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
            comeOverHere = false
            //} else {
                //kiosk.showSystemUI(window)
            //}
        }
    }

    var comeOverHere = false
    var comeOverHereThroughHere = false

    override fun onPause() {
        if (comeOverHere && !comeOverHereThroughHere) {
            comeBackToMe()
        } else {
            if (!comeOverHereThroughHere) {
                comeOverHere = true
                comeOverHereThroughHere = true

                GlobalScope.launch {
                    delay(10000)
                    comeBackToMe()
                }
            }
        }
        super.onPause()
    }

    private fun comeBackToMe() {
        val activityManager = applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        activityManager.moveTaskToFront(taskId, 0)
    }

    override fun onResume() {
        resetComeBack()
        super.onResume()
    }

    private fun resetComeBack() {
        comeOverHere = false
        comeOverHereThroughHere = false
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Toast.makeText(this, "ENTRA", Toast.LENGTH_SHORT)
        }
        return super.onKeyUp(keyCode, event)
    }

    fun blockAll(@Suppress("UNUSED_PARAMETER") view: View) {
        if (comeOverHere) {
            comeOverHere = false
            btBlock.text = "Bloquear"
            btOpenCalculator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            btOpenBrowser.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            btSettings.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        } else {
            comeOverHere = true
            btBlock.text = "Desbloquear"
            btOpenCalculator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDisable))
            btOpenBrowser.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDisable))
            btSettings.setBackgroundColor(ContextCompat.getColor(this, R.color.colorDisable))
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

    fun openSettings(@Suppress("UNUSED_PARAMETER") view: View) {
        startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
    }

    /*fun testWhenBlocked(@Suppress("UNUSED_PARAMETER") view: View) {
        Toast.makeText(this, testEditText.text, Toast.LENGTH_LONG).show()
    }*/

    fun closeApplication(@Suppress("UNUSED_PARAMETER") view: View) {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
