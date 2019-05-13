package com.asterisk.tuandao.alarmstudy.ui.detail

import com.asterisk.tuandao.alarmstudy.ui.BasePresenter
import com.asterisk.tuandao.alarmstudy.ui.BaseView

interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showTimePicker()
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
        fun showAlarmSound()
    }

    interface Presenter : BasePresenter {
        fun getTimePicker()
        fun getAlarmSound()
=======
    }

    interface Presenter : BasePresenter {
        fun openTimePicker()
>>>>>>> handle setting alarm
    }
}
