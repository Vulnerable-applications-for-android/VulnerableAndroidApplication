package com.example.vulnerablesmsapp

import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import com.example.vulnerablesmsapp.ui.main.MessagesFragment
import kotlinx.android.synthetic.main.fragment_create_contact.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val message = messages?.get(0)?.messageBody
        val number = messages[0].displayOriginatingAddress
        val id = context?.let { ContentProviderController.getIdFromNumber(number, it) }
        if (id == "") {
            val values =  ContentValues()
            values.put(SMSContentProvider.NAME, number)
            values.put(SMSContentProvider.NUMBER, number)

            context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_CONTACTS, values)
1
            val contactId = ContentProviderController.getIdFromNumber(number, context);
            val messageValues = ContentValues()
            messageValues.put(SMSContentProvider.ID_CONTACT, contactId)
            messageValues.put(SMSContentProvider.MESSAGE, message)
            messageValues.put(SMSContentProvider.IS_USER, 0)
            messageValues.put(SMSContentProvider.TIMESTAMP, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())
            context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_MESSAGES, messageValues)
        } else {
            val messageValues = ContentValues()
            messageValues.put(SMSContentProvider.ID_CONTACT, id)
            messageValues.put(SMSContentProvider.MESSAGE, message)
            messageValues.put(SMSContentProvider.IS_USER, 0)
            messageValues.put(SMSContentProvider.TIMESTAMP, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())
            context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_MESSAGES, messageValues)
        }
        val intent = Intent()
        intent.action = "newMessageAdded"
        context?.sendBroadcast(intent)
    }
}
