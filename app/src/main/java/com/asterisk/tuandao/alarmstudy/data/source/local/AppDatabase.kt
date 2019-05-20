package com.asterisk.tuandao.alarmstudy.data.source.local

import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.util.AlarmDatabaseUtils
import javax.inject.Singleton

@Singleton
class AppDatabase(
    context: Context,
    DATABASE_NAME: String,
    DATABASE_VERSION: Int
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(".AppDatabase","CREATE_TABLE_ALARM: $CREATE_TABLE_ALARM")
        db?.execSQL(CREATE_TABLE_ALARM)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_ALARM_TABLE)
        onCreate(db)
    }

    fun getAlarms(): List<Alarm> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(SELECT_ALL_AlARMS_QUERY, null)
        return AlarmDatabaseUtils.toList(cursor)
    }

    fun getAlarm(alarmId: Int): Alarm {
        val db = this.readableDatabase
        val cursor = db.rawQuery("$SELETECT_ALARM_QUERY=$alarmId",null)
        return AlarmDatabaseUtils.toAlarm(cursor)
    }

    fun saveAlarm(alarm: Alarm) {
        val db = this.writableDatabase
        val i = db.insert(AlarmEntry.TABLE_NAME, null, AlarmDatabaseUtils.getAlarmValues(alarm))
        Log.d(".AppDatabase", "saveAlarm $i")
        db.close()
    }

    companion object {
        private const val DEFAULT_VALUE = 0
        private const val CREATE_TABLE_ALARM = ("CREATE TABLE " + AlarmEntry.TABLE_NAME + "("
                + AlarmEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + AlarmEntry.COLUMN_HOUR + " TEXT NOT NULL,"
                + AlarmEntry.COLUMN_MINUTE + " TEXT NOT NULL,"
                + AlarmEntry.COLUMN_DAY + " TEXT,"
                + AlarmEntry.COLUMN_SOUND_URI + " TEXT,"
                + AlarmEntry.COLUMN_SELECTED_SOUND + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_ACTIVE + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_VIBRATE + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_VIBRATE_URI + " TEXT,"
                + AlarmEntry.COLUMN_SELECTED_VIBRATE + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_SNOOZE_TIME + " INTEGER, "
                + AlarmEntry.COLUMN_SNOOZE + " INTEGER DEFAULT " + DEFAULT_VALUE +","
                + AlarmEntry.COLUMN_SELECTED_SNOOZE + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_LABEL + " TEXT,"
                + AlarmEntry.COLUMN_METHOD + " INTEGER DEFAULT " + DEFAULT_VALUE + ","
                + AlarmEntry.COLUMN_LEVEL + " INTEGER DEFAULT " + DEFAULT_VALUE + ")")
        private const val DROP_ALARM_TABLE = "DROP TABLE IF EXISTS " + AlarmEntry.TABLE_NAME
        private const val SELECT_ALL_AlARMS_QUERY = "SELECT * FROM " + AlarmEntry.TABLE_NAME
        private const val SELETECT_ALARM_QUERY = "SELECT * FROM ${AlarmEntry.TABLE_NAME} " +
                "WHERE ${AlarmEntry.COLUMN_ID}"
    }
}
