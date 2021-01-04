package com.example.vulnerablebankingapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_transaction.*
import java.time.LocalDateTime


class TransactionActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_transaction)
    }

    fun buttonSendMoneyOnClick(view: View) {
        startService(Intent(this, TransactionService::class.java))
        val accountNumber = text_field_account.editText?.text.toString()
        val amount = text_field_amount.editText?.text.toString().toFloat()
        val user = mAuth.currentUser
        if (user != null) {
            doTransaction(user, accountNumber, amount)
        }
    }

    private fun doTransaction(user: FirebaseUser, accountNumber: String, amount: Float) {
        val database = FirebaseDatabase.getInstance()
        var userRef = database.getReference(user.uid)
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
                    val localDateTime = LocalDateTime.now().toString().replace('.', '=')
                    userRef = userRef.child("transactions").child(localDateTime)
                    userRef.setValue("-$amount")
                    receiverRef.child(otherAccount).child("balance")
                        .setValue(receiverBalance + amount)
                    receiverRef.child(otherAccount).child("transactions").child(localDateTime)
                        .setValue(
                            "+$amount"
                        )
                    Log.d("Transaction", "Complete")
                    Snackbar.make(
                        LinerLayoutTransaction,
                        "Payment has been made!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Log.d("Transaction error", "Not sufficient funds")
                    Snackbar.make(
                        LinerLayoutTransaction,
                        "Error payment could not be made!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(LinerLayoutTransaction.windowToken, 0)
    }
}