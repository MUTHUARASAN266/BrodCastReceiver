package com.libary.brodcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        // if intent =null its will exit the method uing the keyword return along with elvis
        val airplainMode=intent?.getBooleanExtra("state",false)?:return

        if (airplainMode){
            Toast.makeText(context, "Airplane Mode Enabled", Toast.LENGTH_LONG).show()

        }else{
            Toast.makeText(context, "Airplane Mode Disabled", Toast.LENGTH_LONG).show()

        }
    }
}