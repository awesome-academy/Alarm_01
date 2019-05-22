package com.asterisk.tuandao.alarmstudy.utils

import Constants.ALARM_SOUND_TYPE_NOTIFICATION
import android.app.Activity
import android.content.Context
import android.media.RingtoneManager
import android.view.WindowManager
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
fun Activity.showOverLockscreen() {
    window.addFlags(
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}


