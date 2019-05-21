package com.asterisk.tuandao.alarmstudy.ui.detail

import com.asterisk.tuandao.alarmstudy.base.BasePresenter
import com.asterisk.tuandao.alarmstudy.base.BaseView
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound


interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showTimePicker()
        fun showAlarmSound(alarms: ArrayList<AlarmSound>)
        fun showAlarmSnooze()
        fun showAlarmVibration(alarms: ArrayList<AlarmSound>)
        fun showAlarmMethod()
        fun showAlarmSetting()
        fun showDefaultSetting()
    }

    interface Presenter : BasePresenter {
        fun getTimePicker()
        fun getAlarmSound()
        fun getAlarmSnooze()
        fun getAlarmVibration()
        fun getAlarmMethod()
        fun addNewAlarm(alarm: Alarm)
    }
}
