package com.asterisk.tuandao.alarmstudy.ui.detail

import com.asterisk.tuandao.alarmstudy.ui.BasePresenter
import com.asterisk.tuandao.alarmstudy.ui.BaseView

interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showTimePicker()
    }

    interface Presenter : BasePresenter {
        fun openTimePicker()
    }
}
