package com.example.vulnerablesmsapp.ui.main

import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vulnerablesmsapp.R
import com.example.vulnerablesmsapp.SMSContentProvider

/**
 * A placeholder fragment containing a simple view.
 */
class MessagesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    companion object {
        fun newInstance() = MessagesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getContacts()
    }

    private fun getContacts() {
        val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
        val contacts = Uri.parse(url)
        val cursor = context?.contentResolver?.query(contacts, null, null, null, "name")

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var test = ""
                    test += cursor.getString(0) + " "
                    test += cursor.getString(1) + " "
                    test += cursor.getString(2)
                    Log.e("Error", "Contacts " + test)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()

        val urlMessage = "content://com.example.vulnerablesmsapp.SMSContentProvider/messages"
        val messages = Uri.parse(urlMessage)
        val cursorMessage = context?.contentResolver?.query(messages, null, null, null, null)

        if (cursorMessage != null) {
            if (cursorMessage.moveToFirst()) {
                do {
                    var test = ""
                    test += cursorMessage.getString(0) + " "
                    test += cursorMessage.getString(1) + " "
                    test += cursorMessage.getString(2) + " "
                    Log.e("Error", "Messages " + test)
                } while (cursorMessage.moveToNext())
            }
        }
        cursor?.close()
    }
}