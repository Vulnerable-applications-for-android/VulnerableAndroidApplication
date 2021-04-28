package com.example.vulnerablesmsapp.ui.main

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vulnerablesmsapp.ContentProviderController
import com.example.vulnerablesmsapp.R
import com.example.vulnerablesmsapp.SMSContentProvider
import kotlinx.android.synthetic.main.fragment_create_contact.*

class CreateContactFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_contact, container, false)
        val addContactButton = view.findViewById<Button>(R.id.add_contact)
        addContactButton.setOnClickListener {
            val name = text_field_name.editText?.text.toString()
            val number = text_field_number.editText?.text.toString()
            if (name != "" && number != "") {
                val values =  ContentValues()
                values.put(SMSContentProvider.NAME, name)
                values.put(SMSContentProvider.NUMBER, number)
                var contactId =
                        this.context?.let { it1 -> ContentProviderController.getIdFromNumber(number, it1) }
                if (contactId == "") {
                    context?.contentResolver?.insert(SMSContentProvider.CONTENT_URI_CONTACTS, values)
                    text_field_name.editText?.setText("")
                    text_field_number.editText?.setText("")
                    Toast.makeText(context, "Contact added", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Contact Already exists with that number", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Name or number cannot be blank!", Toast.LENGTH_LONG).show()
            }
            val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
        return view
    }

    companion object {
        fun newInstance() = CreateContactFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}