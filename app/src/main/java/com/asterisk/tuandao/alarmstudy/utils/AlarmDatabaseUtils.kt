package com.asterisk.tuandao.alarmstudy.utils

import android.content.ContentValues
import android.database.Cursor
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.source.local.AlarmEntry

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
            val columnDay = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY_OF_WEEK)
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
            alarm.daysOfWeek = day
            alarm.isEnable = isEnabled
            alarm.label = label
            alarms.add(alarm)
            cursor.moveToNext()
        }
        cursor.close()
        return alarms
    }

    fun toAlarm(cursor: Cursor): Alarm {
        val alarm = Alarm()
        if (cursor.moveToFirst()) {
            val columnId = cursor.getColumnIndex(AlarmEntry.COLUMN_ID)
            val columnHour = cursor.getColumnIndex(AlarmEntry.COLUMN_HOUR)
            val columnMinute = cursor.getColumnIndex(AlarmEntry.COLUMN_MINUTE)
            val columnDay = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY_OF_WEEK)
            val columnActive = cursor.getColumnIndex(AlarmEntry.COLUMN_ACTIVE)
            val columnVibration = cursor.getColumnIndex(AlarmEntry.COLUMN_VIBRATE)
            val columnVibrationUri = cursor.getColumnIndex(AlarmEntry.COLUMN_VIBRATE_URI)
            val columnSelectedVibration = cursor.getColumnIndex(AlarmEntry.COLUMN_SELECTED_VIBRATE)
            val columnSelectedSound = cursor.getColumnIndex(AlarmEntry.COLUMN_SELECTED_SOUND)
            val columnSoundUri = cursor.getColumnIndex(AlarmEntry.COLUMN_SOUND_URI)
            val columnSnooze = cursor.getColumnIndex(AlarmEntry.COLUMN_SNOOZE)
            val columnSnoozeTime = cursor.getColumnIndex(AlarmEntry.COLUMN_SNOOZE_TIME)
            val columnLabel = cursor.getColumnIndex(AlarmEntry.COLUMN_LABEL)
            val columnMethod = cursor.getColumnIndex(AlarmEntry.COLUMN_METHOD)
            val columnLevel = cursor.getColumnIndex(AlarmEntry.COLUMN_LEVEL)

            val id = cursor.getInt(columnId)
            val hour = cursor.getInt(columnHour)
            val minute = cursor.getInt(columnMinute)
            val day = cursor.getString(columnDay)
            val isEnabled = cursor.getInt(columnActive)
            val vibration = cursor.getInt(columnVibration)
            val vibrationUri = cursor.getString(columnVibrationUri)
            val selectedVibration = cursor.getInt(columnSelectedVibration)
            val selectedSound = cursor.getInt(columnSelectedSound)
            val soundUri = cursor.getString(columnSoundUri)
            val snooze = cursor.getInt(columnSnooze)
            val snoozeTime = cursor.getInt(columnSnoozeTime)
            val method = cursor.getInt(columnMethod)
            val label = cursor.getString(columnLabel)
            val level = cursor.getInt(columnLevel)

            alarm.id = id
            alarm.hour = hour
            alarm.minute = minute
            alarm.daysOfWeek = day
            alarm.isEnable = isEnabled
            alarm.label = label
            alarm.isVibrated = vibration
            alarm.vibrationUri = vibrationUri
            alarm.selectedVibration = selectedVibration
            alarm.selectedAlarmSound = selectedSound
            alarm.soundUri = soundUri
            alarm.isSnoozed = snooze
            alarm.snoozeTime = snoozeTime
            alarm.method = method
            alarm.level = level
        }
        cursor.close()
        return alarm
    }

    fun getAlarmValues(alarm: Alarm) = ContentValues().apply {
        put(AlarmEntry.COLUMN_HOUR, alarm.hour)
        put(AlarmEntry.COLUMN_MINUTE, alarm.minute)
        put(AlarmEntry.COLUMN_DAY_OF_WEEK, alarm.daysOfWeek)
        put(AlarmEntry.COLUMN_ACTIVE, alarm.isEnable)
        put(AlarmEntry.COLUMN_VIBRATE, alarm.isVibrated)
        put(AlarmEntry.COLUMN_VIBRATE_URI, alarm.vibrationUri)
        put(AlarmEntry.COLUMN_SELECTED_VIBRATE, alarm.selectedVibration)
        put(AlarmEntry.COLUMN_SELECTED_SOUND, alarm.selectedAlarmSound)
        put(AlarmEntry.COLUMN_SOUND_URI, alarm.soundUri)
        put(AlarmEntry.COLUMN_SELECTED_SNOOZE, alarm.selectedSnooze)
        put(AlarmEntry.COLUMN_LABEL, alarm.label)
        put(AlarmEntry.COLUMN_METHOD, alarm.method)
        put(AlarmEntry.COLUMN_LEVEL, alarm.level)
    }

    fun updateStatus(status: Boolean): ContentValues{
        val values = ContentValues()
        var statusInt = if (status) 1 else 0
        values.put(AlarmEntry.COLUMN_ACTIVE, statusInt)
        return values
    }
}
