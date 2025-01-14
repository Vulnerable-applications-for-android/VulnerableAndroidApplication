package com.example.vulnerablesmsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log

class SendSMSBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.extras !=null) {
            val phoneNumber = intent.extras!!.get("number") as String
            val message = intent.extras!!.get("message") as String
            //val smsManager = SmsManager.getDefault()
            //smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.i("smsSend", "SMS sent to $phoneNumber with message: $message")
        }
    }
}