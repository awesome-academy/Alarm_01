package com.asterisk.tuandao.alarmstudy.data

import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound

interface AlarmDataSource {

    interface LoadSoundCallback {

        fun onSuccess (sounds: ArrayList<AlarmSound>)

        fun onFailure()
    }

    interface LoadAlarmCallback {

        fun onSuccess (sounds: ArrayList<Alarm>)

        fun onFailure()
    }

    interface Local {
        fun saveAlarm(alarm: Alarm)
        fun getAlarms(callback: LoadAlarmCallback)
    }

    interface Storage {

        fun loadAlarmSounds(callback: LoadSoundCallback)

        fun loadAlarmVibration(callback: LoadSoundCallback)
    }
}
