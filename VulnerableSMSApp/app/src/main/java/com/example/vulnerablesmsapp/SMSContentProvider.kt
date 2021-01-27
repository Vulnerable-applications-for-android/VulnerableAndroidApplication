package com.example.vulnerablesmsapp

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import kotlin.coroutines.coroutineContext

class SMSContentProvider : ContentProvider() {

    private lateinit var db : SQLiteDatabase

    companion object {
        const val PROVIDER_NAME = "com.example.vulnerablesmsapp.SMSContentProvider"
        private const val URL = "content://$PROVIDER_NAME/contacts"
        val CONTENT_URI: Uri = Uri.parse(URL)

        val ID = "_id"
        const val NAME = "name";
        const val NUMBER = "number";

        lateinit var contactsProjectionMap: HashMap<String, String>

        const val CONTACTS = 1
        const val CONTACT_ID = 2

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        const val DATABASE_NAME = "SMS"
        const val CONTACTS_TABLE_NAME = "contacts"
        const val DATABASE_VERSION = 1
        const val CREATE_DB_TABLE = " CREATE TABLE " + CONTACTS_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, " +
                " number TEXT NOT NULL);"

    }

    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $CONTACTS_TABLE_NAME")
            onCreate(db)
        }
    }

    override fun onCreate(): Boolean {
        //TODO these might not meant to be here
        uriMatcher.addURI(PROVIDER_NAME, "contacts", CONTACTS)
        uriMatcher.addURI(PROVIDER_NAME, "contacts/#", CONTACT_ID)

        val dbHelper = context?.let { DatabaseHelper(it) }
        if (dbHelper != null) {
            db = dbHelper.writableDatabase
            return true
        }
        return false
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val qb = SQLiteQueryBuilder()
        qb.tables = CONTACTS_TABLE_NAME

        when (uriMatcher.match(uri)) {
            CONTACTS -> qb.projectionMap = contactsProjectionMap
            CONTACT_ID -> qb.appendWhere( ID + "=" + uri.pathSegments[1])
        }

        var sortOrder = sortOrder
        if (sortOrder == null || sortOrder == "") {
            sortOrder = NAME
        }

        val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        c.setNotificationUri(context?.contentResolver, uri)
        return c
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)) {
            CONTACTS -> "vnd.android.cursor.dir/vnd.example.students"
            CONTACT_ID -> "vnd.android.cursor.item/vnd.example.students"
            else -> {
                throw IllegalArgumentException("Unsupported URI: $uri");
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowID = db.insert(CONTACTS_TABLE_NAME, "", values)

        if (rowID > 0) {
            val _uri = ContentUris.withAppendedId(CONTENT_URI, rowID)
            context?.contentResolver?.notifyChange(_uri, null)
            return _uri
        }
        throw SQLException("Failed to add a record into $uri")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

}