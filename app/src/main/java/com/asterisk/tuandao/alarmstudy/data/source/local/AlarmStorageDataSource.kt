package com.asterisk.tuandao.alarmstudy.data.source.local



import android.content.Context
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.di.ApplicationContext
import com.asterisk.tuandao.alarmstudy.utils.AppExecutors
import com.asterisk.tuandao.alarmstudy.utils.getAlarmSounds
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmStorageDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val executor: AppExecutors
) : AlarmDataSource.Storage {

    override fun loadAlarmSounds(callback: AlarmDataSource.LoadSoundCallback) {
        executor.diskIO.execute {
            val alarms = context.getAlarmSounds(Constants.ALARM_SOUND_TYPE_ALARM)
            executor.mainThread.execute {
                if (alarms.isEmpty()) callback.onFailure()
                else callback.onSuccess(alarms)
            }
        }
    }

    override fun loadAlarmVibration(callback: AlarmDataSource.LoadSoundCallback) {
        executor.diskIO.execute {
            val alarms = context.getAlarmSounds(Constants.ALARM_SOUND_TYPE_NOTIFICATION)
            executor.mainThread.execute {
                if (alarms.isEmpty()) callback.onFailure()
                else callback.onSuccess(alarms)
            }
        }
    }
}
