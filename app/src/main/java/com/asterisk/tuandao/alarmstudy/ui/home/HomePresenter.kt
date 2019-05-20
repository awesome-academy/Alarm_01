package com.asterisk.tuandao.alarmstudy.ui.home

import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val mainView: HomeContract.View
) : HomeContract.Presenter {
    override fun start() {
        alarmRepository.getAlarms(object : AlarmDataSource.LoadAlarmCallback{
            override fun onSuccess(alarms: ArrayList<Alarm>) {
                mainView.showAlarms(alarms)
            }

            override fun onFailure() {
            }

        })
    }

    override fun addNewAlarm() {
        mainView.showAlarmSetting()
    }

    override fun activeAlarm(alarm: Alarm, status: Boolean) {
        alarmRepository.updateActiveAlarm(alarm.id, status, object : AlarmDataSource.updateAlarmCallback {
            override fun onSuccess(status: Boolean) {
                mainView.updateActiveAlarm(status, alarm)
            }

            override fun onFailure() {
                Log.d("HomePresenter","onFailure()")
            }

        })
    }
}
