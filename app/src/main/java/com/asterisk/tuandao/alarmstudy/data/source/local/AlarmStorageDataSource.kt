package com.asterisk.tuandao.alarmstudy.data.source.local

import ALARM_SOUND_TYPE_ALARM
import android.content.Context
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.di.ApplicationContext
import com.asterisk.tuandao.alarmstudy.util.AppExecutors
import com.asterisk.tuandao.alarmstudy.util.getAlarmSounds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmStorageDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    val executor: AppExecutors
) : AlarmDataSource.Storage {

    override fun loadAlarmSounds(callback: AlarmDataSource.loadSoundsCallback) {
        executor.diskIO.execute {
            val alarms = context.getAlarmSounds(ALARM_SOUND_TYPE_ALARM)
            executor.mainThread.execute {
                if (alarms.isEmpty()) callback.onFailure()
                else callback.onSuccess(alarms)
            }
        }
    }
}