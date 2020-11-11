package com.example.vulnerablebankingapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vulnerablebankingapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()

        val databaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val accountNumber = dataSnapshot.child("account_number").value
                val balance = dataSnapshot.child("balance").value
                //Error account number is sometimes null, it crashes becuase i switch fragment before the account number and balance have been feteched so the text views no longer exist.
                account_number_text_view.text = "Account Number: $accountNumber"
                balance_text_view.text = "Balance: $balance"
                Log.e("account", accountNumber.toString())
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        mAuth.uid?.let { database.getReference(it) }?.addListenerForSingleValueEvent(databaseListener)

    }

}