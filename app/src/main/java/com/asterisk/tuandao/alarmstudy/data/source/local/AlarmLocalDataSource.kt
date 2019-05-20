package com.asterisk.tuandao.alarmstudy.data.source.local

import android.util.Log
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
            Log.d(".AlarmLocalDataSource", "alarms $alarms")
            executor.mainThread.execute {
                if (alarms.isEmpty()) {
                    callback.onFailure()
                } else {
                    callback.onSuccess(alarms)
                }
            }
        }
    }

    override fun getAlarm(alarmId: Int, callback: AlarmDataSource.GetAlarmCallback) {
        executor.diskIO.execute {
            val alarm = appDatabase.getAlarm(alarmId)
            executor.mainThread.execute {
                if (alarm!=null) callback.onSuccess(alarm)
                else callback.onFailure()
            }
        }
    }
}
