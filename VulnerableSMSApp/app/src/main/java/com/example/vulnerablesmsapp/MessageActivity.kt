package com.example.vulnerablesmsapp

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_message.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class MessageActivity : AppCompatActivity() {
    private var id = "";
    private var number = "";
    private lateinit var recycleViewAdapter: MessageRecycleViewAdapter
    //TODO auto update messages
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        name_text_view.text = intent.extras?.get("name").toString()
        id = intent.extras?.get("id").toString()
        Log.e("IDERROR", id);
        number = intent.extras?.get("number").toString()
        number_text_view.text = number
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            recycleViewAdapter = MessageRecycleViewAdapter()
            adapter = recycleViewAdapter
        }
        getMessages()
        val sendButton = findViewById<Button>(R.id.button_send)
        sendButton.setOnClickListener {
            val message = text_field_message.editText?.text.toString()
            if (message != "") {
                sendMessage(message)
                addMessageToDatabase(message)
                text_field_message.editText?.setText("")
                getMessages()
            } else {
                Toast.makeText(this, "Message cannot be blank!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getMessages() {
        val list = ArrayList<MessageData>()
        val urlMessage = "content://com.example.vulnerablesmsapp.SMSContentProvider/messages"
        val messages = Uri.parse(urlMessage)
        val cursorMessage = this.contentResolver?.query(messages, null, null, null, SMSContentProvider.TIMESTAMP)

        if (cursorMessage != null) {
            if (cursorMessage.moveToFirst()) {
                do {
                    if (cursorMessage.getString(1) == id) {
                        list.add(
                                MessageData(
                                        cursorMessage.getString(3).toInt(),
                                        cursorMessage.getString(2)
                                )
                        )
                    }
                } while (cursorMessage.moveToNext())
            }
        }
        cursorMessage?.close()
        recycleViewAdapter.submitList(list)
        recycleViewAdapter.notifyDataSetChanged()
    }

    private fun sendMessage(message: String) {
        //TODO not working atm!!
        val intent = Intent()
        intent.action = "sendSMSBroadcast"
        intent.putExtra("number", number)
        intent.putExtra("message", message)
        //intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        sendBroadcast(intent)
    }

    private fun addMessageToDatabase(message: String) {
        val messageValues = ContentValues()
        messageValues.put(SMSContentProvider.ID_CONTACT, id)
        messageValues.put(SMSContentProvider.MESSAGE, message)
        messageValues.put(SMSContentProvider.IS_USER, 1)
        messageValues.put(SMSContentProvider.TIMESTAMP, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())
        this.contentResolver?.insert(SMSContentProvider.CONTENT_URI_MESSAGES, messageValues)
    }
}