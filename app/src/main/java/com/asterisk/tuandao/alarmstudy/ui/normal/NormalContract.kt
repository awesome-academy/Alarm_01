package com.asterisk.tuandao.alarmstudy.ui.normal

import com.asterisk.tuandao.alarmstudy.base.BasePresenter
import com.asterisk.tuandao.alarmstudy.base.BaseView
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailContract

interface NormalContract {

    interface View : BaseView<NormalContract.Presenter> {
        fun showAlarmOff(alarm: Alarm)
    }

    interface Presenter : BasePresenter {
        fun setAlarmId(alarmId: Int)
    }
}