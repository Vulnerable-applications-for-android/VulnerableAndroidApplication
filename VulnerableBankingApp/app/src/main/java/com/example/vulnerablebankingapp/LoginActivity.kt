package com.example.vulnerablebankingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)
        updateUI(mAuth.currentUser)
    }

    fun buttonLogInOnClick(view: View) {
        val email = text_field_email.editText?.text.toString()
        val password = text_field_password.editText?.text.toString()
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Success", "signInWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Error", "signInWithEmail:failure", task.exception)
                        updateUI(null)
                    }
                }
    }

    fun buttonCreateAccountOnClick(view: View) {
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }

    fun updateUI(user : FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}