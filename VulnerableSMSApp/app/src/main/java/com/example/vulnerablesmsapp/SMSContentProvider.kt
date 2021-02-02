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
        private const val URL_CONTACTS = "content://$PROVIDER_NAME/contacts"
        private const val URL_MESSAGES = "content://$PROVIDER_NAME/messages"

        val CONTENT_URI_CONTACTS: Uri = Uri.parse(URL_CONTACTS)
        val CONTENT_URI_MESSAGES: Uri = Uri.parse(URL_MESSAGES)

        val ID = "_id"
        const val NAME = "name"
        const val NUMBER = "number"
        const val MESSAGE = "message"
        const val ID_CONTACT = "id_contact"

        lateinit var contactsProjectionMap: HashMap<String, String>
        lateinit var messagesProjectionMap: HashMap<String, String>

        const val CONTACTS = 1
        const val CONTACT_ID = 2
        const val MESSAGES = 3
        const val MESSAGE_ID = 4

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        const val DATABASE_NAME = "SMS"
        const val CONTACTS_TABLE_NAME = "contacts"
        const val MESSAGES_TABLE_NAME = "messages"
        const val DATABASE_VERSION = 1
        const val CREATE_DB_TABLE_CONTACTS = " CREATE TABLE " + CONTACTS_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, " +
                " number TEXT NOT NULL);"
        const val CREATE_DB_TABLE_MESSAGES = " CREATE TABLE " + MESSAGES_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " id_contact INTEGER NOT NULL, " +
                " message TEXT NOT NULL);"

    }

    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_DB_TABLE_CONTACTS)
            db?.execSQL(CREATE_DB_TABLE_MESSAGES)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $CONTACTS_TABLE_NAME")
            db?.execSQL("DROP TABLE IF EXISTS $MESSAGES_TABLE_NAME")
            onCreate(db)
        }
    }

    override fun onCreate(): Boolean {
        uriMatcher.addURI(PROVIDER_NAME, "contacts", CONTACTS)
        uriMatcher.addURI(PROVIDER_NAME, "contacts/#", CONTACT_ID)
        uriMatcher.addURI(PROVIDER_NAME, "messages", MESSAGES)
        uriMatcher.addURI(PROVIDER_NAME, "messages/#", MESSAGE_ID)

        val dbHelper = context?.let { DatabaseHelper(it) }
        if (dbHelper != null) {
            db = dbHelper.writableDatabase
            return true
        }
        return false
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val qb = SQLiteQueryBuilder()
        when (uriMatcher.match(uri)) {
            CONTACTS -> {
                qb.tables = CONTACTS_TABLE_NAME
                var sortOrder = sortOrder
                if (sortOrder == null || sortOrder == "") {
                    sortOrder = NAME
                }
                val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
                c.setNotificationUri(context?.contentResolver, uri)
                return c
            }

            CONTACT_ID -> {
                qb.tables = CONTACTS_TABLE_NAME
                qb.appendWhere( ID + "=" + uri.pathSegments[1])
                var sortOrder = sortOrder
                if (sortOrder == null || sortOrder == "") {
                    sortOrder = NAME
                }
                val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
                c.setNotificationUri(context?.contentResolver, uri)
                return c
            }

            MESSAGES -> {
                qb.tables = MESSAGES_TABLE_NAME
                var sortOrder = sortOrder
                if (sortOrder == null || sortOrder == "") {
                    sortOrder = ID
                }
                val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
                c.setNotificationUri(context?.contentResolver, uri)
                return c
            }

            MESSAGE_ID -> {
                qb.tables = MESSAGES_TABLE_NAME
                qb.appendWhere( ID + "=" + uri.pathSegments[1])
                var sortOrder = sortOrder
                if (sortOrder == null || sortOrder == "") {
                    sortOrder = ID
                }
                val c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
                c.setNotificationUri(context?.contentResolver, uri)
                return c
            }
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)) {
            CONTACTS -> "vnd.android.cursor.dir/vnd.example.students"
            CONTACT_ID -> "vnd.android.cursor.item/vnd.example.students"
            else -> {
                throw IllegalArgumentException("Unsupported URI: $uri")
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            CONTACTS -> {
                val rowID = db.insert(CONTACTS_TABLE_NAME, "", values)
                if (rowID > 0) {
                    val uriTemp = ContentUris.withAppendedId(CONTENT_URI_CONTACTS, rowID)
                    context?.contentResolver?.notifyChange(uriTemp, null)
                    return uriTemp
                }
            }
            MESSAGES -> {
                val rowID = db.insert(MESSAGES_TABLE_NAME, "", values)
                if (rowID > 0) {
                    val uriTemp = ContentUris.withAppendedId(CONTENT_URI_MESSAGES, rowID)
                    context?.contentResolver?.notifyChange(uriTemp, null)
                    return uriTemp
                }
            }
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