package com.asterisk.tuandao.alarmstudy.data.repository

import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.di.Local
import com.asterisk.tuandao.alarmstudy.di.Storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(
    @Local val alarmLocalDataSource: AlarmDataSource.Local,
    @Storage val alarmStorageDataSource: AlarmDataSource.Storage
) : AlarmDataSource.Local, AlarmDataSource.Storage {
    private val TAG = this::class.java.simpleName

    var cachedAlarmSounds: ArrayList<AlarmSound> = ArrayList()
    var cachedVibration: ArrayList<AlarmSound> = ArrayList()

    override fun loadAlarmSounds(callback: AlarmDataSource.loadSoundsCallback) {
        if (cachedAlarmSounds.isNotEmpty()) {
            callback.onSuccess(cachedAlarmSounds)
            return
        }

        alarmStorageDataSource.loadAlarmSounds(object : AlarmDataSource.loadSoundsCallback {
            override fun onSuccess(sounds: ArrayList<AlarmSound>) {
                Log.d(TAG,"sounds $sounds")
                //cache sounds
                cachedAlarmSounds = sounds
                callback.onSuccess(sounds)
            }
            override fun onFailure() {
                callback.onFailure()
            }

        })
    }

    override fun loadAlarmVibration(callback: AlarmDataSource.loadSoundsCallback) {
        if (cachedVibration.isNotEmpty()) {
            callback.onSuccess(cachedVibration)
            return
        }

        alarmStorageDataSource.loadAlarmVibration(object : AlarmDataSource.loadSoundsCallback {
            override fun onSuccess(vibrations: ArrayList<AlarmSound>) {
                Log.d(TAG,"sounds $vibrations")
                //cache sounds
                cachedVibration = vibrations
                callback.onSuccess(vibrations)
            }
            override fun onFailure() {
                callback.onFailure()
            }

        })
    }

    override fun saveAlarm(alarm: Alarm) {
        alarmLocalDataSource.saveAlarm(alarm)
    }
}