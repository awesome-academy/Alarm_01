package com.asterisk.tuandao.alarmstudy.data.model

class Alarm constructor(
    var id: Int,
    var hour: Int,
    var minute: Int,
    var days: String?,
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
    constructor() : this(0,
        0, 0, null,
        null, null, null, null,
        null, null, null, null, null, null
    ,null,null)
}
