package com.asterisk.tuandao.alarmstudy.util

import android.content.ContentValues
import android.database.Cursor
import com.asterisk.tuandao.alarmstudy.data.source.local.AlarmEntry
import com.asterisk.tuandao.alarmstudy.data.model.Alarm

object AlarmDatabaseUtils {

    const val DATABASE_NAME = "alarm.db"
    const val DATABASE_VERSION = 1

//    fun toList(cursor: Cursor): List<Alarm> {
//        val alarms: MutableList<Alarm> = ArrayList()
//        cursor.moveToFirst()
//        while (!cursor.isAfterLast) {
//            val columnId = cursor.getColumnIndex(AlarmEntry.COLUMN_ID)
//            val columnTime = cursor.getColumnIndex(AlarmEntry.COLUMN_TIME)
//            val columnDay = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY)
//            val columnActive = cursor.getColumnIndex(AlarmEntry.COLUMN_ACTIVE)
//            val columnLabel = cursor.getColumnIndex(AlarmEntry.COLUMN_LABEL)
//            val columnVibrate = cursor.getColumnIndex(AlarmEntry.COLUMN_VIBRATE)
//            val columnSoundTitle = cursor.getColumnIndex(AlarmEntry.COLUMN_SOUND_TITLE)
//            val columnSoundUri = cursor.getColumnIndex(AlarmEntry.COLUMN_SOUND_URI)
//            val columnMethod = cursor.getColumnIndex(AlarmEntry.COLUMN_METHOD)
//            val columnLevel = cursor.getColumnIndex(AlarmEntry.COLUMN_LEVEL)
//
//            val id = cursor.getInt(columnId)
//            val time = cursor.getString(columnTime)
//            val day = cursor.getString(columnDay)
//            val isEnabled = cursor.getInt(columnActive)
//            val label = cursor.getString(columnLabel)
//            val vibrate = cursor.getInt(columnVibrate)
//            val soundTitle = cursor.getString(columnSoundTitle)
//            val soundUri = cursor.getString(columnSoundUri)
//            val method = cursor.getInt(columnMethod)
//
//            alarms.add(Alarm(id, time, day, isEnabled,
//                    vibrate, soundTitle, null, label, null, null))
//        }
//        cursor.close()
//        return alarms
//    }

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
const val TABLE_NAME = "Alarm"
const val COLUMN_ID = "id"
const val COLUMN_HOUR = "hour"
const val COLUMN_MINUTE = "minute"
const val COLUMN_DAY = "days"
const val COLUMN_ACTIVE = "active"
const val COLUMN_VIBRATE = "vibrate"
const val COLUMN_VIBRATE_URI = "vibrate_uri"
const val COLUMN_SELECTED_VIBRATE = "selected_vibrate"
const val COLUMN_SELECTED_SOUND = "sound_title"
const val COLUMN_SOUND_URI = "sound_uri"
const val COLUMN_SNOOZE_TIME = "snooze_time"
const val COLUMN_SELECTED_SNOOZE = "selected_snooze"
const val COLUMN_LABEL = "label"
const val COLUMN_METHOD = "method"
const val COLUMN_LEVEL = "level"