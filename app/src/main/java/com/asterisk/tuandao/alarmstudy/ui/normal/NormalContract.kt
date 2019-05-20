package com.asterisk.tuandao.alarmstudy.ui.normal

import com.asterisk.tuandao.alarmstudy.base.BasePresenter
import com.asterisk.tuandao.alarmstudy.base.BaseView
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailContract

interface NormalContract {

    interface View : BaseView<DetailContract.Presenter> {

    }

    interface Presenter : BasePresenter {

    }
}