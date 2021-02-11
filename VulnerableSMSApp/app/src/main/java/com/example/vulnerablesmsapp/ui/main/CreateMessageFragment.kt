package com.example.vulnerablesmsapp.ui.main

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vulnerablesmsapp.R
import com.example.vulnerablesmsapp.SMSContentProvider
import kotlinx.android.synthetic.main.fragment_create_message.*
import java.time.LocalDateTime
import java.time.ZoneOffset

class CreateMessageFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_message, container, false)
        val addContactButton = view.findViewById<Button>(R.id.add_contact)
        addContactButton.setOnClickListener {
            val name = text_field_name.editText?.text.toString()
            val number = text_field_number.editText?.text.toString()
            val values =  ContentValues()
            values.put(SMSContentProvider.NAME, name)
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

            val message = text_field_message.editText?.text.toString()
            val messageValues = ContentValues()
            messageValues.put(SMSContentProvider.ID_CONTACT, contact_id)
            messageValues.put(SMSContentProvider.MESSAGE, message)
            messageValues.put(SMSContentProvider.IS_USER, 1)
            messageValues.put(SMSContentProvider.TIMESTAMP, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toInt())
            context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_MESSAGES, messageValues)

            Toast.makeText(context, "Message Created", Toast.LENGTH_LONG).show()
        }
        return view
    }

    companion object {
        fun newInstance() = CreateMessageFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun addContact(view: View) {
        val name = text_field_name.editText?.text.toString()
        val number = text_field_number.editText?.text.toString()
        val values =  ContentValues()
        values.put(SMSContentProvider.NAME, name)
        values.put(SMSContentProvider.NUMBER, number)

        val uri = context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_CONTACTS, values)
        Toast.makeText(context, uri.toString(), Toast.LENGTH_LONG).show()
    }
}