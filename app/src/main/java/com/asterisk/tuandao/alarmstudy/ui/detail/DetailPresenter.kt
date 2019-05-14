package com.asterisk.tuandao.alarmstudy.ui.detail

import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import com.asterisk.tuandao.alarmstudy.di.DetailActivityScope
import javax.inject.Inject

@DetailActivityScope
class DetailPresenter @Inject constructor(
    val alarmRepository: AlarmRepository,
    val detailView: DetailContract.View
) : DetailContract.Presenter {


    override fun start() {
    }

    override fun getTimePicker() {
        detailView.showTimePicker()
    }

    override fun getAlarmSound() {
        alarmRepository.loadAlarmSounds(object : AlarmDataSource.loadSoundsCallback{
            override fun onSuccess(sounds: ArrayList<AlarmSound>) {
                detailView.showAlarmSound(sounds)
            }

            override fun onFailure() {
            }

        })
    }

    override fun getAlarmSnooze() {
    }

    override fun getAlarmVibration() {
    }
}
