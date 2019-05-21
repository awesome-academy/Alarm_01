package com.asterisk.tuandao.alarmstudy.data.model

import java.io.Serializable

class Alarm constructor(
    var id: Int,
    var hour: Int,
    var minute: Int,
    var dayOfMonth: Int?,
    var month: Int?,
    var year: Int?,
    var daysOfWeek: String?,
    var isEnable: Int?,
    var isVibrated: Int?,
    var vibrationUri: String?,
    var selectedVibration: Int?,
    var selectedAlarmSound: Int?,
    var soundUri: String?,
    var isSnoozed: Int?,
    var selectedSnooze: Int?,
    var snoozeTime: Int?,
    var label: String?,
    var method: Int?,
    var level: Int?
) {
    constructor() : this(-1,
        0, 0, null,
        null, null, null, null,
        null, null, null, null, null, null
    ,null,null, null, null, null)
}
