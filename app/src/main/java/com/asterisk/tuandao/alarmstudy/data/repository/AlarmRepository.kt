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

    override fun loadAlarmSounds(callback: AlarmDataSource.LoadSoundCallback) {
        if (cachedAlarmSounds.isNotEmpty()) {
            callback.onSuccess(cachedAlarmSounds)
            return
        }

        alarmStorageDataSource.loadAlarmSounds(object : AlarmDataSource.LoadSoundCallback {
            override fun onSuccess(sounds: ArrayList<AlarmSound>) {
                //cache sounds
                cachedAlarmSounds = sounds
                callback.onSuccess(sounds)
            }
            override fun onFailure() {
                callback.onFailure()
            }
        })
    }

    override fun loadAlarmVibration(callback: AlarmDataSource.LoadSoundCallback) {
        if (cachedVibration.isNotEmpty()) {
            callback.onSuccess(cachedVibration)
            return
        }

        alarmStorageDataSource.loadAlarmVibration(object : AlarmDataSource.LoadSoundCallback {
            override fun onSuccess(vibrations: ArrayList<AlarmSound>) {
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

    override fun getAlarms(callback: AlarmDataSource.LoadAlarmCallback) {
        alarmLocalDataSource.getAlarms(object : AlarmDataSource.LoadAlarmCallback{
            override fun onSuccess(alarms: ArrayList<Alarm>) {
                callback.onSuccess(alarms)
            }
            override fun onFailure() {
                callback.onFailure()
            }

        })
    }

    override fun getAlarm(alarmId: Int, callback: AlarmDataSource.GetAlarmCallback) {
        alarmLocalDataSource.getAlarm(alarmId, object : AlarmDataSource.GetAlarmCallback {
            override fun onSuccess(alarm: Alarm) {
                callback.onSuccess(alarm)
            }

            override fun onFailure() {
                callback.onFailure()
            }

        })
    }

    override fun updateActiveAlarm(alarmId: Int, status: Boolean, callback: AlarmDataSource.updateAlarmCallback) {
        alarmLocalDataSource.updateActiveAlarm(alarmId, status, object : AlarmDataSource.updateAlarmCallback {
            override fun onSuccess(status: Boolean) {
                callback.onSuccess(status)
            }
            override fun onFailure() {
                callback.onFailure()
            }

        })
    }

    override fun deleteAlarm(alarmId: Int, callback: AlarmDataSource.updateAlarmCallback) {
        alarmLocalDataSource.deleteAlarm(alarmId, object : AlarmDataSource.updateAlarmCallback{
            override fun onSuccess(status: Boolean) {
                callback.onSuccess(true)
            }

            override fun onFailure() {
            }

        })
    }
}
