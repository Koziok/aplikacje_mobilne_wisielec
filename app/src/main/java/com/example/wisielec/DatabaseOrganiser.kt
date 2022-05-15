package com.example.wisielec

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseOrganiser(context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER)
{
    companion object
    {
        private const val DB_NAME = "HangerGameDB.db"
        private const val DB_VER = 1
        private const val TAB_NAME = "users"
        private const val COL_ID = "id"
        private const val COL_LOGIN = "login"
        private const val COL_PASSWORD = "password"
        private const val COL_SCORE = "score"
        private const val COL_EMAIL = "email"
        private const val COL_PHONE = "phone"
        private const val COL_SENT_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TAB_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_LOGIN VARCHAR(50), $COL_PASSWORD VARCHAR(50), $COL_EMAIL VARCHAR(50), $COL_PHONE VARCHAR(50), $COL_SCORE INTEGER, $COL_SENT_RATING INTEGER)")
        db!!.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TAB_NAME")
        onCreate(db!!)
    }


    fun login(loginData: String, passwordData: String): Int
    {
        val db = this.writableDatabase
        val password = passwordData
        val login = loginData
        val query = "SELECT * FROM $TAB_NAME WHERE $COL_LOGIN=?AND $COL_PASSWORD=?"
        db.rawQuery(query, arrayOf(login, password)).use {
            if(it.moveToFirst()) {
                return it.getInt(it.getColumnIndexOrThrow(COL_SCORE))
            }
            else {
                return -1
            }
        }
        db.close()
    }

    fun register(loginData: String, passwordData: String, phoneData: String, emailData: String, score: Int): Int
    {
        val db = this.writableDatabase
        val login = loginData
        val query = "SELECT * FROM $TAB_NAME WHERE $COL_LOGIN=?"
        db.rawQuery(query, arrayOf(login)).use {
            if(it.moveToFirst()) {
                return -1
            }
            else {
                val values = ContentValues()
                values.put(COL_LOGIN, loginData)
                values.put(COL_PASSWORD, passwordData)
                values.put(COL_SCORE, phoneData)
                values.put(COL_PHONE, emailData)
                values.put(COL_EMAIL, score)
                values.put(COL_SENT_RATING, 0)

                db.insert(TAB_NAME, null, values)
                db.close()
                return 0
            }
        }
        db.close()
    }

    fun getRating(loginData: String): Int
    {
        val db = this.writableDatabase
        val query = "SELECT $COL_SENT_RATING FROM $TAB_NAME WHERE $COL_LOGIN=?"
        db.rawQuery(query, arrayOf(loginData)).use {
            if(it.moveToFirst())
            {
                return it.getInt(it.getColumnIndexOrThrow(COL_SENT_RATING))
            }
            else
            {
                return -1
            }
        }
        db.close()
    }

    fun setRating(loginData: String): Int {
        val db = this.writableDatabase
        val query = "UPDATE $TAB_NAME SET $COL_SENT_RATING = 1 WHERE $COL_LOGIN=?"
        db.rawQuery(query, arrayOf(loginData)).use {
            if(it.moveToFirst())
            {
                return 0
            }
            else
            {
                return -1
            }
        }
        db.close()
    }


}