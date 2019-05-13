package com.asterisk.tuandao.alarmstudy.ui.detail

import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import com.asterisk.tuandao.alarmstudy.di.DetailActivityContext
import com.asterisk.tuandao.alarmstudy.di.DetailActivityScope
import javax.inject.Inject

@DetailActivityScope
class DetailPresenter @Inject constructor(
    val alarmRepository: AlarmRepository,
    val detailView: DetailContract.View
) : DetailContract.Presenter {

    override fun start() {
    }

<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
    override fun getTimePicker() {
        detailView.showTimePicker()
    }

    override fun getAlarmSound() {
        detailView.showAlarmSound()
    }
=======
    override fun openTimePicker() {
        detailView.showTimePicker()
    }
>>>>>>> handle setting alarm
}
