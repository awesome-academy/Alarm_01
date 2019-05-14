package com.asterisk.tuandao.alarmstudy.data.repository

import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.data.source.local.AlarmStorageDataSource
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

    var cachedTasks: ArrayList<AlarmSound> = ArrayList()

    override fun loadAlarmSounds(callback: AlarmDataSource.loadSoundsCallback) {

        if (cachedTasks.isNotEmpty()) {
            callback.onSuccess(cachedTasks)
            return
        }

        alarmStorageDataSource.loadAlarmSounds(object : AlarmDataSource.loadSoundsCallback {
            override fun onSuccess(sounds: ArrayList<AlarmSound>) {
                Log.d(TAG,"sounds $sounds")
                //cache sounds
                cachedTasks = sounds
                callback.onSuccess(sounds)
            }
            override fun onFailure() {
                callback.onFailure()
            }

        })
    }
}