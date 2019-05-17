package com.asterisk.tuandao.alarmstudy.util

import ALARM_SOUND_TYPE_NOTIFICATION
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound


fun Context.TAG(): String = this::class.java.simpleName

fun Context.getAlarmSounds(type: Int): ArrayList<AlarmSound> {
    val alarms = ArrayList<AlarmSound>()
    val ringtonManager = RingtoneManager(this)
    ringtonManager.setType(
        if (type == ALARM_SOUND_TYPE_NOTIFICATION) RingtoneManager.TYPE_NOTIFICATION
        else RingtoneManager.TYPE_ALARM
    )

    val cursor = ringtonManager.cursor
    var curId = 0
    while (cursor.moveToNext()) {
        val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
        var uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX)
        val id = cursor.getString(RingtoneManager.ID_COLUMN_INDEX)
        if (!uri.endsWith(id)) {
            uri += "/$id"
        }

        val alarmSound = AlarmSound(curId++, title, uri)
        alarms.add(alarmSound)
    }
    return alarms
}

fun Context.getDefaultRington(): Uri =
    RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM)

fun Context.getDefaultRingtonTitle(uri: Uri): String =
    RingtoneManager.getRingtone(this,uri).getTitle(this)


