package com.asterisk.tuandao.alarmstudy.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.asterisk.tuandao.alarmstudy.data.entry.AlarmEntry
import com.asterisk.tuandao.alarmstudy.data.model.Alarm

open class Appdatabase(val context: Context,
                       val factory: SQLiteDatabase.CursorFactory?,
                       val DATABASE_NAME: String,
                       val DATABASE_VERSION: Int)
    : SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_ALARM_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_TABLE_ALARM)
        onCreate(db)
    }

    companion object {
        const val DEFAULT_VALUE = 0
        const val CREATE_ALARM_TABLE = ("CREATE TABLE " + AlarmEntry.TABLE_NAME + " ("
                + AlarmEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + AlarmEntry.COLUMN_TIME + " TEXT,"
                + AlarmEntry.COLUMN_DAY + " TEXT,"
                + AlarmEntry.COLUMN_LABEL + " TEXT,"
                + AlarmEntry.COLUMN_SOUND_TITLE + " TEXT,"
                + AlarmEntry.COLUMN_SOUND_URI + " TEXT,"
                + AlarmEntry.COLUMN_STATUS + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_VIBRATE +" INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_METHOD + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_LEVEL + " INTEGER DEFAULT " + DEFAULT_VALUE + ")")
        const val DROP_TABLE_ALARM = "DROP TABLE IF EXISTS " + AlarmEntry.TABLE_NAME
    }
}
