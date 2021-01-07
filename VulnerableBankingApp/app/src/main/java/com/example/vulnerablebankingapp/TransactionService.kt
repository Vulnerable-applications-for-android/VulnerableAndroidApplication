package com.example.vulnerablebankingapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_transaction.*
import java.time.LocalDateTime

class TransactionService : Service() {

    lateinit var userUID: String
    lateinit var accountNumber: String
    var amount: Int? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e("Service", "Service has started")
        userUID = intent.getStringExtra("userUID") ?: ""
        accountNumber = intent.getStringExtra("accountNumber") ?: ""
        amount = intent.getStringExtra("amount").toInt()
        if (userUID == "" || accountNumber == "" || amount == null) {
            Toast.makeText(this, "Error payment could not be made", Toast.LENGTH_SHORT).show()
            stopSelf()
        } else {
            Log.e("Service", "Starting transaction")
            transaction(this)
        }
        return START_STICKY
    }

    private fun transaction(context: Context) {
        Log.e("Service", "Transaction started")
        val database = FirebaseDatabase.getInstance()
        var userRef = database.getReference(userUID)
        val receiverRef = database.reference
        var balance = 0
        var receiverBalance = 0
        Log.e("Service", "onDataChange")

        receiverRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("Service", "onDataChange")
                var otherAccount = ""
                for (ss in snapshot.children) {
                    if ((ss.child("account_number").value).toString().toInt() == accountNumber.toInt()) {
                        receiverBalance = ss.child("balance").value.toString().toInt()
                        otherAccount = ss.key.toString()
                    } else if (ss.key == userUID) {
                        balance = ss.child("balance").value.toString().toInt()
                    }
                }
                if (otherAccount == "") {
                    Log.e("Service", "other account is empty")
                    //TODO error other account does not exist
                    stopSelf()
                } else if (balance >= amount!!) {
                    userRef.child("balance").setValue(balance - amount!!)
                    val localDateTime = LocalDateTime.now().toString().replace('.', '=')
                    userRef = userRef.child("transactions").child(localDateTime)
                    val userTransactionHistory = MoneyConverter.penniesToPounds(amount!!)
                    userRef.setValue("-$userTransactionHistory")
                    receiverRef.child(otherAccount).child("balance")
                            .setValue(receiverBalance + amount!!)
                    val otherTransactionHistory = MoneyConverter.penniesToPounds(amount!!)
                    receiverRef.child(otherAccount).child("transactions").child(localDateTime)
                            .setValue("+$otherTransactionHistory")
                    Log.d("Transaction", "Complete")
                    Toast.makeText(context, "Payment Successful", Toast.LENGTH_SHORT).show()
                    stopSelf()
                } else {
                    Log.d("Transaction error", "Not sufficient funds")
                    Toast.makeText(context, "Error Not sufficient funds", Toast.LENGTH_SHORT).show()
                    stopSelf()
                }
            }
        })
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Log.e("Service", "Destroy")
    }

}