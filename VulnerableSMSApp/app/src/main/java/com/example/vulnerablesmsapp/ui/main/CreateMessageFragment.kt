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
import com.example.vulnerablesmsapp.ContentProviderController
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

            var contactId =
                this.context?.let { it1 -> ContentProviderController.getIdFromNumber(number, it1) }

            if (contactId == "") {
                context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_CONTACTS, values)
                Toast.makeText(context, "Contact added", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Contact Already exists with that number", Toast.LENGTH_LONG).show()
            }
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