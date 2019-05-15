package com.asterisk.tuandao.alarmstudy.util

import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmSoundPickerDialog

fun ArrayList<AlarmSound>.toArrayString(): Array<String> {
    val arrAlarmSound = Array(this.size){""}
    this.forEachIndexed { index, alarmSound ->
        arrAlarmSound[index] = alarmSound.title
    }
    return arrAlarmSound
}