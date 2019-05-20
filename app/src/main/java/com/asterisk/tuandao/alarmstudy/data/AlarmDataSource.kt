package com.asterisk.tuandao.alarmstudy.data

import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound

interface AlarmDataSource {

    interface LoadSoundCallback {

        fun onSuccess (sounds: ArrayList<AlarmSound>)

        fun onFailure()
    }

    interface LoadAlarmCallback {

        fun onSuccess (alarms: ArrayList<Alarm>)

        fun onFailure()
    }

    interface GetAlarmCallback {

        fun onSuccess (alarm: Alarm)

        fun onFailure()
    }

    interface updateAlarmCallback {

        fun onSuccess (status: Boolean)

        fun onFailure()
    }

    interface Local {

        fun saveAlarm(alarm: Alarm)

        fun getAlarms(callback: LoadAlarmCallback)

        fun getAlarm(alarmId: Int, callback: GetAlarmCallback)

        fun updateActiveAlarm(alarmId: Int, status: Boolean, callback: updateAlarmCallback)

    }

    interface Storage {

        fun loadAlarmSounds(callback: LoadSoundCallback)

        fun loadAlarmVibration(callback: LoadSoundCallback)
    }
}
