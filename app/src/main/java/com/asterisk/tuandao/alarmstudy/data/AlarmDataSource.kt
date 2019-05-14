package com.asterisk.tuandao.alarmstudy.data

import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound

interface AlarmDataSource {

    interface loadSoundsCallback {

        fun onSuccess(sounds: ArrayList<AlarmSound>)

        fun onFailure()
    }

    interface Local {

    }

    interface Storage {

        fun loadAlarmSounds(callback: loadSoundsCallback)
    }
}
