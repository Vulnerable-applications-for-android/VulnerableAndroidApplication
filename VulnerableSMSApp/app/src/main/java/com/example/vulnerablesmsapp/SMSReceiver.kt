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
import kotlinx.android.synthetic.main.fragment_create_message.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val message = messages?.get(0)?.messageBody
        val number = messages[0].displayOriginatingAddress
        Toast.makeText(context,message + " " + number,Toast.LENGTH_LONG).show()
        val id = getDataBaseId(number, context)
        Log.e("ID", id + " the id1")
        if (id == null) {
            Log.e("ID", id + " the id")
            val values =  ContentValues()
            values.put(SMSContentProvider.NAME, number)
            values.put(SMSContentProvider.NUMBER, number)

            val uri = context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_CONTACTS, values)

            val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
            val messages = Uri.parse(url)
            val cursor = context?.contentResolver?.query(messages, null, null, null, null)

            var contact_id = ""
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    contact_id = cursor.getString(0)
                }
            }
            cursor?.close()

            val messageValues = ContentValues()
            messageValues.put(SMSContentProvider.ID_CONTACT, contact_id)
            messageValues.put(SMSContentProvider.MESSAGE, message)
            messageValues.put(SMSContentProvider.IS_USER, 0)
            messageValues.put(SMSContentProvider.TIMESTAMP, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())
            context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_MESSAGES, messageValues)
        } else {
            val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
            val messages = Uri.parse(url)
            val cursor = context?.contentResolver?.query(messages, null, null, null, null)

            var contact_id = ""
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    contact_id = cursor.getString(0)
                }
            }
            cursor?.close()
            Log.e("ERRORID", "this is the id " + contact_id)
            val messageValues = ContentValues()
            messageValues.put(SMSContentProvider.ID_CONTACT, contact_id)
            messageValues.put(SMSContentProvider.MESSAGE, message)
            messageValues.put(SMSContentProvider.IS_USER, 0)
            messageValues.put(SMSContentProvider.TIMESTAMP, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())
            context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_MESSAGES, messageValues)
        }
    }

    private fun getDataBaseId(number: String, context: Context?): String? {
        var list: ArrayList<MessagesData> = ArrayList()
        val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
        val contacts = Uri.parse(url)
        val cursor = context?.contentResolver?.query(contacts, null, null, null, "name")
        var id = ""
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(2) == number) {
                        id = cursor.getString(0)
                    }
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        if (id == "") {
            return null
        }
        return id
    }
}
