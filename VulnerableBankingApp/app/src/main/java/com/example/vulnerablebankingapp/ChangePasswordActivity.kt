package com.example.vulnerablebankingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.text_field_password

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_change_password)
    }

    fun buttonChangePasswordOnClick(view: View) {
        val password = text_field_password.editText?.text.toString()
        mAuth.currentUser?.updatePassword(password)
    }
}