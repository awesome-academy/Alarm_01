package com.asterisk.tuandao.alarmstudy.service

import Constants
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import com.asterisk.tuandao.alarmstudy.di.component.AlarmServiceComponent
import com.asterisk.tuandao.alarmstudy.di.component.DaggerAlarmServiceComponent
import com.asterisk.tuandao.alarmstudy.ui.normal.NormalActivity
import com.asterisk.tuandao.alarmstudy.utils.AlarmTimeUtils
import javax.inject.Inject

class AlarmService : Service(), MediaPlayerController{

    @Inject
    lateinit var repository: AlarmRepository
    @Inject
    lateinit var mMediaPlayerManager: MediaPlayerController
    private lateinit var mAlarmServiceComponent: AlarmServiceComponent
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
            .build()
        mAlarmServiceComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        when (intent?.action) {
            Constants.ACTION_SCHEDULE_ALARM -> AlarmTimeUtils.scheduleAlarm(repository, applicationContext)
            Constants.ACTION_CANCEL_ALARM -> AlarmTimeUtils.cancelAlarm(applicationContext)
            else -> {
                val alarmId = intent?.getIntExtra(Constants.TRIGGERED_ALARM_ID,0)
                getAlarmOff(alarmId!!)
                setupAlarm(alarmId!!)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupAlarm(alarmId: Int) {
        repository.getAlarm(alarmId, object : AlarmDataSource.GetAlarmCallback{
            override fun onSuccess(alarm: Alarm) {
                create(alarm)
            }

            override fun onFailure() {
            }
        })
    }

    private fun getAlarmOff(alarmId: Int) {
        val intent = Intent(this, NormalActivity::class.java)
        intent.putExtra(Constants.TRIGGERED_ALARM_ID, alarmId)
        startActivity(intent)
    }

    override fun create(alarm: Alarm) {
        mMediaPlayerManager.create(alarm)
    }

    override fun destroyPlayer() {
        mMediaPlayerManager.destroyPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
