package com.asterisk.tuandao.alarmstudy.ui.detail

import com.asterisk.tuandao.alarmstudy.base.BasePresenter
import com.asterisk.tuandao.alarmstudy.base.BaseView

interface DetailContract {

    interface View : BaseView<Presenter> {
        fun showTimePicker()
    }

    interface Presenter : BasePresenter {
        fun openTimePicker()
    }
}
