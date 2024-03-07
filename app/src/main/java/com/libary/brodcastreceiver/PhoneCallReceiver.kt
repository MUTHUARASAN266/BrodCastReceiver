package com.libary.brodcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast


class PhoneCallReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING ) {
                val incomingNumber = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                if (incomingNumber!=null){
                    Toast.makeText(context, "Ringing State Number is $incomingNumber", Toast.LENGTH_SHORT).show()

                    val dataIntent = Intent("my.custom.action")
                    dataIntent.putExtra("key", incomingNumber)
                    Log.e("PhoneCallReceiver", "onReceive: -> ${Thread.currentThread().name}")
                    context?.sendBroadcast(dataIntent)

                }
            }else if(state==TelephonyManager.EXTRA_STATE_OFFHOOK){
                Toast.makeText(context, "Call connected State", Toast.LENGTH_SHORT).show()
            }else if (state==TelephonyManager.EXTRA_STATE_IDLE){
                Toast.makeText(context, "call end State", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}