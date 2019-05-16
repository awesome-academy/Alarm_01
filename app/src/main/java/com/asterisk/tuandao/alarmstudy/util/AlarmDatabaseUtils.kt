package com.asterisk.tuandao.alarmstudy.util

import android.content.ContentValues
import android.database.Cursor
import com.asterisk.tuandao.alarmstudy.data.source.local.AlarmEntry
import com.asterisk.tuandao.alarmstudy.data.model.Alarm

object AlarmDatabaseUtils {

    const val DATABASE_NAME = "alarm.db"
    const val DATABASE_VERSION = 1

    fun toList(cursor: Cursor): List<Alarm> {
        val alarms: MutableList<Alarm> = ArrayList()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val columnId = cursor.getColumnIndex(AlarmEntry.COLUMN_ID)
            val columnHour = cursor.getColumnIndex(AlarmEntry.COLUMN_HOUR)
            val columnMinute = cursor.getColumnIndex(AlarmEntry.COLUMN_MINUTE)
            val columnDay = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY)
            val columnActive = cursor.getColumnIndex(AlarmEntry.COLUMN_ACTIVE)
            val columnLabel = cursor.getColumnIndex(AlarmEntry.COLUMN_LABEL)

            val id = cursor.getInt(columnId)
            val hour = cursor.getInt(columnHour)
            val minute = cursor.getInt(columnMinute)
            val day = cursor.getString(columnDay)
            val isEnabled = cursor.getInt(columnActive)
            val label = cursor.getString(columnLabel)

            val alarm = Alarm()
            alarm.id = id
            alarm.hour = hour
            alarm.minute = minute
            alarm.days = day
            alarm.isEnable = isEnabled
            alarm.label = label

            alarms.add(alarm)
        }
        cursor.close()
        return alarms
    }

    fun getAlarmValues(alarm: Alarm) :ContentValues{
        val values = ContentValues()
        values.put(AlarmEntry.COLUMN_HOUR, alarm.hour)
        values.put(AlarmEntry.COLUMN_MINUTE, alarm.minute)
        values.put(AlarmEntry.COLUMN_DAY, alarm.days)
        values.put(AlarmEntry.COLUMN_ACTIVE, alarm.isEnable)
        values.put(AlarmEntry.COLUMN_VIBRATE, alarm.isVibrated)
        values.put(AlarmEntry.COLUMN_VIBRATE_URI, alarm.vibrationUri)
        values.put(AlarmEntry.COLUMN_SELECTED_VIBRATE, alarm.selectedVibration)
        values.put(AlarmEntry.COLUMN_SELECTED_SOUND, alarm.selectedAlarmSound)
        values.put(AlarmEntry.COLUMN_SOUND_URI, alarm.soundUri)
        values.put(AlarmEntry.COLUMN_SELECTED_SNOOZE, alarm.selectedSnooze)
        values.put(AlarmEntry.COLUMN_LABEL, alarm.label)
        values.put(AlarmEntry.COLUMN_METHOD, alarm.method)
        values.put(AlarmEntry.COLUMN_LEVEL, alarm.level)
        return values
    }
}
