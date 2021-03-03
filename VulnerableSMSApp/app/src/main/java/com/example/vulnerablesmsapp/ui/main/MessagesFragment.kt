package com.example.vulnerablesmsapp.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vulnerablesmsapp.*
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class MessagesFragment : Fragment() {
    private lateinit var recycleViewAdapter: ContactRecycleViewAdapter
    //TODO auto update contacts
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
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            recycleViewAdapter = ContactRecycleViewAdapter()
            adapter = recycleViewAdapter
        }

        getContacts()
    }

    override fun onResume() {
        super.onResume()
        getContacts()
    }

    private fun getContacts() {
        var list: ArrayList<ContactData> = ArrayList()
        val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
        val contacts = Uri.parse(url)
        val cursor = context?.contentResolver?.query(contacts, null, null, null, "name")

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list.add(
                            ContactData(
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(0).toInt()
                            )
                    )
                    Log.e("Error", "name " + cursor.getString(1))
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        recycleViewAdapter.submitList(list)
        recycleViewAdapter.notifyDataSetChanged()
    }

}