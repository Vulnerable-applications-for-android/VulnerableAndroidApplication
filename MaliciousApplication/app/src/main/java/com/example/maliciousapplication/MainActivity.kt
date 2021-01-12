package com.example.maliciousapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonActivityAccessOnClick(view: View) {
        val intent = Intent(this, ActivityAccessActivity::class.java)
        startActivity(intent)
    }

    fun buttonExportedServiceOnclick(view: View) {
        val intent = Intent(this, ExportedServiceActivity::class.java)
        startActivity(intent)
    }

}