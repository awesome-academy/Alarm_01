package com.asterisk.tuandao.alarmstudy.ui.home

import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import javax.inject.Inject

class HomePresenter @Inject constructor(
    val alarmRepository: AlarmRepository,
    val mainView: HomeContract.View
) : HomeContract.Presenter {
    override fun start() {
        alarmRepository.getAlarms(object : AlarmDataSource.LoadAlarmCallback{
            override fun onSuccess(sounds: ArrayList<Alarm>) {
                mainView.showAlarms(sounds)
            }

            override fun onFailure() {
            }

        })
    }

    override fun addNewAlarm() {
        mainView.showAlarmSetting()
    }
}
