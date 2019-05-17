package com.asterisk.tuandao.alarmstudy.di.component

import com.asterisk.tuandao.alarmstudy.di.DetailActivityScope
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailActivity
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailModule
import dagger.Component

@DetailActivityScope
@Component(modules = [DetailModule::class], dependencies = [ApplicationComponent::class])
interface DetailActivityComponent {
    fun inject(detailActivity: DetailActivity)
}
