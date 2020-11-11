package com.example.vulnerablebankingapp.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vulnerablebankingapp.R
import com.example.vulnerablebankingapp.TransactionActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_transaction_check_password.*

class TransactionFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transaction_check_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        login_button.setOnClickListener {
            buttonGoToTransactionOnClick(it)
        }
    }

    private fun buttonGoToTransactionOnClick(view: View) {
        val email = text_field_email.editText?.text.toString()
        val password = text_field_password.editText?.text.toString()
        val credentials = EmailAuthProvider.getCredential(email, password)
        mAuth.currentUser?.reauthenticate(credentials)?.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Reauthorise", "Success")
                val intent = Intent(this.context, TransactionActivity::class.java)
                startActivity(intent)
            } else {
                Log.d("Reauthorise", "Error")
            }
        }

    }
}