package com.asterisk.tuandao.alarmstudy.ui.normal

import android.util.Log
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import javax.inject.Inject

class NormalPresenter @Inject constructor(
    val repository: AlarmRepository,
    val normalView: NormalContract.View
) : NormalContract.Presenter {
    private var alarmId = -1

    override fun start() {
        if (alarmId != -1) {
            getAlarm()
        }
    }

    override fun setAlarmId(alarmId: Int) {
        this.alarmId = alarmId
    }

    private fun getAlarm() {
        repository.getAlarm(alarmId, object : AlarmDataSource.GetAlarmCallback {
            override fun onSuccess(alarm: Alarm) {
                normalView.showAlarmOff(alarm)
            }

            override fun onFailure() {
            }

        })
    }
}
