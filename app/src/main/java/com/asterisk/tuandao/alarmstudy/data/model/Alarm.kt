package com.asterisk.tuandao.alarmstudy.data.model

class Alarm constructor(
    var id: Int?,
    var hour: Int?,
    var minute: Int?,
    var days: String?,
    var isEnable: Int?,
    var isVibrated: Int?,
    var vibrationUri: String?,
    var selectedVibration: Int?,
    var selectedAlarmSound: Int?,
    var soundUri: String?,
    var selectedSnooze: Int?,
    var label: String?,
    var method: Int?,
    var level: Int?
) {
    constructor() : this(null,
        null, null, null,
        null, null, null, null,
        null, null, null, null, null, null)
}