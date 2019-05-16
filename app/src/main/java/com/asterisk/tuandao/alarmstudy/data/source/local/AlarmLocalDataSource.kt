package com.asterisk.tuandao.alarmstudy.data.source.local

import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val executor: AppExecutors
) : AlarmDataSource.Local {

    override fun saveAlarm(alarm: Alarm) {
        executor.diskIO.execute {
            appDatabase.saveAlarm(alarm)
        }
    }

    override fun getAlarms(callback: AlarmDataSource.LoadAlarmCallback) {
        executor.diskIO.execute {
            val alarms = appDatabase.getAlarms() as ArrayList
            executor.mainThread.execute {
                if (alarms.isEmpty()) {
                    callback.onFailure()
                }else {
                    callback.onSuccess(alarms)
                }
            }
        }
    }

}
