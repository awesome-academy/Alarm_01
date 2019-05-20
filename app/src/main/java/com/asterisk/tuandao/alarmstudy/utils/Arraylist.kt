package com.asterisk.tuandao.alarmstudy.utils

import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound

fun ArrayList<AlarmSound>.toArrayString(): Array<String> {
    val arrAlarmSound = Array(this.size){""}
    this.forEachIndexed { index, alarmSound ->
        arrAlarmSound[index] = alarmSound.title
    }
    return arrAlarmSound
}

fun ArrayList<Int>.removeElement(day:Int) {
    var index = 0
    for (i in 0 until this.size) {
        if (this[i] == day) index = i
    }
    this.remove(index)
}