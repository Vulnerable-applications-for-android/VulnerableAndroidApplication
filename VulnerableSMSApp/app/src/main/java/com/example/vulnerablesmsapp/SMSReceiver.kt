package com.example.vulnerablesmsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import java.util.*

class SMSReceiver : BroadcastReceiver() {

    companion object {
        private val TAG = SMSReceiver.javaClass.simpleName
        public val pduType = "PDUS"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        Log.e("Text Message", "Running reciever")
        //Mabye a way to make this vulnerable is just get the body of the intent and then use this code as the fix.
        val messages: Array<out SmsMessage>? = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val message = messages?.get(0)?.messageBody
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

}