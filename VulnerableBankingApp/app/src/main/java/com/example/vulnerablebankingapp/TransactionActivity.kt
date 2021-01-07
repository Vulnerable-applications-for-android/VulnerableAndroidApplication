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
        //TODO Validate input fields
        val accountNumber = text_field_account.editText?.text.toString()
        val amountTemp = text_field_amount.editText?.text.toString().toFloat()
        val amount = MoneyConverter.poundsToPennies(amountTemp).toString()
        val user = mAuth.currentUser
        if (user != null) {
            val intent = Intent(this, TransactionService::class.java)
            intent.putExtra("userUID", user.uid)
            intent.putExtra("accountNumber", accountNumber)
            intent.putExtra("amount", amount)
            startService(intent)
        }
        val hideKeyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard.hideSoftInputFromWindow(LinerLayoutTransaction.windowToken, 0)
    }
}