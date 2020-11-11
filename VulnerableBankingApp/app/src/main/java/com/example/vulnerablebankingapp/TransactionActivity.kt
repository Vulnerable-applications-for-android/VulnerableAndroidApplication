package com.example.vulnerablebankingapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_transaction.*

class TransactionActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_transaction)
    }

    fun buttonSendMoneyOnClick(view: View) {
        val accountNumber = text_field_account.editText?.text.toString()
        val amount = text_field_amount.editText?.text.toString().toFloat()
        val user = mAuth.currentUser
        if (user != null) {
            doTransaction(user, accountNumber, amount)
        }
    }

    private fun doTransaction(user : FirebaseUser, accountNumber : String, amount : Float) {
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference(user.uid)
        val receiverRef = database.reference
        var balance = 0.0
        var receiverBalance = 0.0

        receiverRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                var otherAccount = ""
                for (ss in snapshot.children) {
                    if (ss.child("account_number").value == accountNumber.toLong()) {
                        receiverBalance = ss.child("balance").value.toString().toDouble()
                        otherAccount = ss.key.toString()
                    } else if (ss.key == user.uid) {
                        balance = ss.child("balance").value.toString().toDouble()
                    }
                }
                if (balance >= amount) {
                    userRef.child("balance").setValue(balance - amount)
                    receiverRef.child(otherAccount).child("balance").setValue(receiverBalance + amount)
                    Log.d("Transaction", "Complete")
                } else {
                    Log.d("Transaction error", "Not sufficient funds")
                }
            }
        })
    }
}