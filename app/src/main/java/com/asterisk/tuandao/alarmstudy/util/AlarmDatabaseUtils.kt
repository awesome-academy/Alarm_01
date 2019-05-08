package com.asterisk.tuandao.alarmstudy.util

import android.database.Cursor
import com.asterisk.tuandao.alarmstudy.data.entry.AlarmEntry
import com.asterisk.tuandao.alarmstudy.data.model.Alarm

object AlarmDatabaseUtils {

    fun toList(cursor: Cursor): List<Alarm> {
        val alarms: MutableList<Alarm> = ArrayList()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val columnId = cursor.getColumnIndex(AlarmEntry.COLUMN_ID)
            val columnTime = cursor.getColumnIndex(AlarmEntry.COLUMN_TIME)
            val columnDay = cursor.getColumnIndex(AlarmEntry.COLUMN_DAY)
            val columnActive = cursor.getColumnIndex(AlarmEntry.COLUMN_ACTIVE)
            val columnLabel = cursor.getColumnIndex(AlarmEntry.COLUMN_LABEL)
            val columnVibrate = cursor.getColumnIndex(AlarmEntry.COLUMN_VIBRATE)
            val columnSoundTitle = cursor.getColumnIndex(AlarmEntry.COLUMN_SOUND_TITLE)
            val columnSoundUri = cursor.getColumnIndex(AlarmEntry.COLUMN_SOUND_URI)
            val columnMethod = cursor.getColumnIndex(AlarmEntry.COLUMN_METHOD)
            val columnLevel = cursor.getColumnIndex(AlarmEntry.COLUMN_LEVEL)

            val id = cursor.getInt(columnId)
            val time = cursor.getString(columnTime)
            val day = cursor.getString(columnDay)
            val isEnabled = cursor.getInt(columnActive)
            val label = cursor.getString(columnLabel)
            val vibrate = cursor.getInt(columnVibrate)
            val soundTitle = cursor.getString(columnSoundTitle)
            val soundUri = cursor.getString(columnSoundUri)
            val method = cursor.getInt(columnMethod)

            alarms.add(Alarm(id, time, day, isEnabled,
                    vibrate, soundTitle, null, label, null, null))
        }
        cursor.close()
        return alarms
    }
}
