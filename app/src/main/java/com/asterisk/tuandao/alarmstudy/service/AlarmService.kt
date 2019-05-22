package com.asterisk.tuandao.alarmstudy.service

import Constants
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import com.asterisk.tuandao.alarmstudy.di.component.AlarmServiceComponent
import com.asterisk.tuandao.alarmstudy.di.component.DaggerAlarmServiceComponent
import com.asterisk.tuandao.alarmstudy.ui.normal.NormalActivity
import com.asterisk.tuandao.alarmstudy.utils.AlarmTimeUtils
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class AlarmService : Service(), MediaPlayerController{

    @Inject
    lateinit var repository: AlarmRepository
    @Inject
    lateinit var mMediaPlayerManager: MediaPlayerController
    private lateinit var mAlarmServiceComponent: AlarmServiceComponent

    private lateinit var mNotification: Notification
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        mAlarmServiceComponent = DaggerAlarmServiceComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .serviceModule(ServiceModule(this))
            .build()
        mAlarmServiceComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        when (action) {
            Constants.ACTION_SCHEDULE_ALARM -> {
                Timer().schedule(Constants.DELAY_TIME) {
                    AlarmTimeUtils.scheduleAlarm(repository, applicationContext)
                }
            }
            Constants.ACTION_CANCEL_ALARM -> AlarmTimeUtils.cancelAlarm(applicationContext)
            Constants.ACTION_TRIGGER_ALARM -> {
                val alarmId = intent.getIntExtra(Constants.TRIGGERED_ALARM_ID,Constants.INVALID_ID)
                if (alarmId!= Constants.INVALID_ID) {
                    getAlarmOff(alarmId)
                    setupAlarm(alarmId)
                }
            }
            Constants.ACTION_DISMISS_ALARM -> {
                destroyPlayer()
                AlarmTimeUtils.scheduleAlarm(repository, applicationContext)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupAlarm(alarmId: Int) {
        repository.getAlarm(alarmId, object : AlarmDataSource.GetAlarmCallback{
            override fun onSuccess(alarm: Alarm) {
                Log.d("AlarmService","alarm: id ${alarm.id}, hour ${alarm.hour}, uri ${alarm.soundUri}")
                create(alarm)
            }

            override fun onFailure() {
            }
        })
    }

    private fun getAlarmOff(alarmId: Int) {
        val intent = Intent(this, NormalActivity::class.java)
        intent.putExtra(Constants.TRIGGERED_ALARM_ID, alarmId)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun create(alarm: Alarm) {
        mMediaPlayerManager.create(alarm)
    }

    override fun destroyPlayer() {
        mMediaPlayerManager.destroyPlayer()
    }

}
