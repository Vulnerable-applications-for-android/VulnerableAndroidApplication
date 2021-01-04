package com.example.vulnerablebankingapp

import android.app.Service
import android.content.Intent
import android.os.HandlerThread
import android.os.IBinder
import android.util.Log
import android.widget.Toast

class TransactionService : Service() {

    override fun onCreate() {

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()
        stopSelf()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }

}