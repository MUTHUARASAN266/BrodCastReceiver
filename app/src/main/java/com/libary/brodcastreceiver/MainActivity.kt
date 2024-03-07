package com.libary.brodcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    lateinit var receiver:SmsReceiver
    lateinit var phoneCallReceiver: PhoneCallReceiver
    //lateinit var connectionReceiver: ConnectionReceiver
    lateinit var intentFilter: IntentFilter
    private var  TAG= ""
    val PERMISSIONS_REQUEST_CODE = 123
    val PERMISSION_REQUEST_CAMERA = 0
    val permissions = arrayOf(
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.READ_CALL_LOG
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TAG = applicationContext.packageName

        // ACTION_AIRPLANE_MODE_CHANGED
        receiver = SmsReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }

        // PhoneCallReceiver
        phoneCallReceiver = PhoneCallReceiver()
        intentFilter = IntentFilter().apply {
            addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        }
        registerReceiver(phoneCallReceiver, intentFilter)
        Log.e(TAG, "phoneCallReceiver: ${registerReceiver(phoneCallReceiver, intentFilter)}")


        /* intentFilter.also {
             it.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
             registerReceiver(phoneCallReceiver, it)
         }
 */

        /*  val buttonSend =findViewById<Button>(R.id.button_send_bordcast)
                 buttonSend.setOnClickListener {
                  val intent = Intent("com.libary.brodcastreceiver.CUSTOM_ACTION")
                  sendBroadcast(intent)
              }*/
        /*connectionReceiver=ConnectionReceiver()
        intentFilter = IntentFilter("com.libary.brodcastreceiver.CUSTOM_ACTION")*/

        findViewById<Button>(R.id.button_send_bordcast).setOnClickListener {
            checkMyPermission()
           // checkMyRequestPermission()
            //showCameraPreview()
        }
        findViewById<Button>(R.id.button_toast).setOnClickListener {
            Toast.makeText(this, "It's working", Toast.LENGTH_SHORT).show()
        }


        val filter = IntentFilter("my.custom.action")
        val broadcastReceiver= object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val value = intent!!.getStringExtra("key")
                Log.e(TAG, "${TAG}onReceive: -> ${Thread.currentThread().name}")
                findViewById<TextView>(R.id.textView).text=value
            }

        }
        registerReceiver(broadcastReceiver,filter)

    }

    private fun showCameraPreview() {
        // Check if the Camera permission has been granted
        if (checkSelfPermission(android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Log.e(TAG, "showCameraPreview: camera_permission_available" )

        } else {
            // Permission is missing and must be requested.
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {

        if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Log.e(TAG, "requestCameraPermission: camera_access_required")
         requestPermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)

        } else {
            Log.e(TAG, "requestCameraPermission:camera_permission_not_available" )

            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun checkMyRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE)
    }

    private fun checkMyPermission() {
        if (ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "checkMyPermission:already granted")
        } else {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "checkMyPermission:PERMISSION_GRANTED")

                } else {
                    Log.e(TAG, "checkMyPermission:PERMISSION_NOT_GRANTED")
                }
                return
            }
        }
       /* if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Log.e(TAG, "onRequestPermissionsResult:camera_permission_granted ", )
            } else {
                // Permission request was denied.
                Log.e(TAG, "onRequestPermissionsResult: camera_permission_denied", )
            }
        }*/
    }


    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ")
    }
    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop: ")
    }

    override fun onResume() {
        super.onResume()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it)
        }
        Log.e(TAG, "onResume: ")

    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
        unregisterReceiver(phoneCallReceiver)

    }
}