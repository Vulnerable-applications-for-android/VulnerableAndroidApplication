package com.example.vulnerablesmsapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {
    private var id = "";
    private var number = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        name_text_view.text = intent.extras?.get("name").toString()
        id = intent.extras?.get("id").toString();
        number = intent.extras?.get("number").toString();
        getMessages();
    }

    private fun getMessages() {
        val urlMessage = "content://com.example.vulnerablesmsapp.SMSContentProvider/messages/$id"
        val messages = Uri.parse(urlMessage)
        val cursorMessage = this.contentResolver?.query(messages, null, null, null, SMSContentProvider.TIMESTAMP)

        if (cursorMessage != null) {
            if (cursorMessage.moveToFirst()) {
                do {
                    var test = ""
                    test += cursorMessage.getString(0) + " "
                    test += cursorMessage.getString(1) + " "
                    test += cursorMessage.getString(2) + " "
                    test += cursorMessage.getString(3) + " "
                    test += cursorMessage.getString(4) + " "
                    Log.e("Error", "Messages " + test)
                } while (cursorMessage.moveToNext())
            }
        }
        cursorMessage?.close()
    }
}