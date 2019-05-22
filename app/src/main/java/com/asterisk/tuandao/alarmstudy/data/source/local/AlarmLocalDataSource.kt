package com.asterisk.tuandao.alarmstudy.data.source.local

import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.utils.AppExecutors
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
            alarms.forEach {
                Log.d(".AlarmLocalDataSource", "alarms ${it.id}")
            }
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
                if (alarm!=null) {
                    Log.d("AlarmLocalDataSource","getAlarm ${alarm.id}, hour ${alarm.hour}, uri ${alarm.soundUri}")
                    callback.onSuccess(alarm)
                }
                else callback.onFailure()
            }
        }
    }

    override fun updateActiveAlarm(alarmId: Int, status: Boolean, callback: AlarmDataSource.updateAlarmCallback) {
        executor.diskIO.execute {
            val statusUpdate = appDatabase.updateStatus(alarmId, status)
            executor.mainThread.execute {
                if (statusUpdate) callback.onSuccess(status)
                else callback.onFailure()
            }
        }
    }

    override fun deleteAlarm(alarmId: Int, callback: AlarmDataSource.updateAlarmCallback) {
        executor.diskIO.execute {
            val delete = appDatabase.deleteAlarm(alarmId)
            executor.mainThread.execute {
                if (delete!=-1) callback.onSuccess(true)
            }
        }
    }
}
