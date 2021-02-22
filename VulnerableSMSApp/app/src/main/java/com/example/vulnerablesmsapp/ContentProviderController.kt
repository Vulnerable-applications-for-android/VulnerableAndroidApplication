package com.example.vulnerablesmsapp

import android.content.Context
import android.net.Uri
import android.util.Log

class ContentProviderController {
    companion object {
        fun getIdFromNumber(number: String, context: Context): String {
            val url = "content://com.example.vulnerablesmsapp.SMSContentProvider/contacts"
            val contacts = Uri.parse(url)
            val cursor = context.contentResolver?.query(contacts, null, null, null, "name")
            var id = "";
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Log.e("getID", "db: " + cursor.getString(2))
                        Log.e("getID", "num in: " + number)

                        if (cursor.getString(2) == number) {
                            id = cursor.getString(0);
                        }
                    } while (cursor.moveToNext())
                }
            }
            cursor?.close()
            return id
        }
    }
}