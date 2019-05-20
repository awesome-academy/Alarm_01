package com.asterisk.tuandao.alarmstudy.ui.home

import com.asterisk.tuandao.alarmstudy.base.BasePresenter
import com.asterisk.tuandao.alarmstudy.base.BaseView
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun showAlarms(sounds: ArrayList<Alarm>)

        fun showAlarmSetting()

        fun updateActiveAlarm(status: Boolean, alarm: Alarm)
    }

    interface Presenter : BasePresenter {

        fun addNewAlarm()

        fun activeAlarm(alarm: Alarm, status: Boolean)
    }
}
