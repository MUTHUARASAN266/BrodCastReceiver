package com.libary.brodcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast

class ConnectionReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("API123",""+intent?.action)
        if (intent?.action.equals("com.libary.brodcastreceiver.CUSTOM_ACTION")){
            Toast.makeText(context, "SOME_ACTION is received", Toast.LENGTH_SHORT).show()
        }else{
           /* val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
            if (isConnected) {
                try {
                    Toast.makeText(context, "Network is connected", Toast.LENGTH_LONG).show();
                } catch (e:Exception) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(context, "Network is changed or reconnected", Toast.LENGTH_LONG).show();
            }*/
            // if intent =null its will exit the method uing the keyword return along with elvis
            val airplainMode=intent?.getBooleanExtra("state",false)?:return

            if (airplainMode){
                Toast.makeText(context, "Airplane Mode Enabled", Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(context, "Airplane Mode Disabled", Toast.LENGTH_LONG).show()

            }
        }
        }
    }
