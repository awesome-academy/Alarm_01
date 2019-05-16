package com.asterisk.tuandao.alarmstudy.util

import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound

fun ArrayList<AlarmSound>.toArrayString(): Array<String> {
    val arrAlarmSound = Array(this.size){""}
    this.forEachIndexed { index, alarmSound ->
        arrAlarmSound[index] = alarmSound.title
    }
    return arrAlarmSound
}