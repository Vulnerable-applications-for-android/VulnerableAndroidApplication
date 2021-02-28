package com.example.maliciousapplication

import android.content.ComponentName
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_content_provider.*
import kotlinx.android.synthetic.main.activity_exported_service.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class ContentProviderActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
    }

    fun buttonSendSMSOnClick(view: View) {
        val PROVIDER_NAME = "com.example.vulnerablesmsapp.SMSContentProvider"
        val URL_CONTACTS = "content://$PROVIDER_NAME/contacts"
        val URL_MESSAGES = "content://$PROVIDER_NAME/messages"
        val name = text_field_name.editText?.text.toString()
        val number = text_field_number.editText?.text.toString()
        val message = text_field_message.editText?.text.toString()

        val id = getIdFromNumber(number, this)
        if (id == "") {
            val values = ContentValues()
            values.put("name", name)
            values.put("number", number)

            val contentUriContacts = Uri.parse(URL_CONTACTS)
            contentResolver.insert(contentUriContacts, values)

            val contactId = getIdFromNumber(number, this);
            val messageValues = ContentValues()
            messageValues.put("id_contact", contactId)
            messageValues.put("message", message)
            messageValues.put("is_user", 0)
            messageValues.put("timestamp", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())

            val contentUriMessage = Uri.parse(URL_MESSAGES)
            contentResolver.insert(contentUriMessage, messageValues)
        } else {
            val messageValues = ContentValues()
            messageValues.put("id_contact", id)
            messageValues.put("message", message)
            messageValues.put("is_user", 0)
            messageValues.put("timestamp", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())

            val contentUriMessage = Uri.parse(URL_MESSAGES)
            contentResolver.insert(contentUriMessage, messageValues)
        }
    }

    fun getIdFromNumber(number: String, context: Context): String {
        val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
        val contacts = Uri.parse(url)
        val cursor = context.contentResolver?.query(contacts, null, null, null, "name")
        var id = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(2) == number) {
                        id = cursor.getString(0);
                        Log.e("getID", "id: " + cursor.getString(0))
                        Log.e("getID", "db num: " + cursor.getString(2))
                        Log.e("getID", "name: " + cursor.getString(1))

                    }
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        return id
    }

}