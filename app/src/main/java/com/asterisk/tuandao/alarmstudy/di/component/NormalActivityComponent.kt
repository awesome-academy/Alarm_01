package com.asterisk.tuandao.alarmstudy.di.component

import com.asterisk.tuandao.alarmstudy.di.NormalActivityScope
import com.asterisk.tuandao.alarmstudy.ui.normal.NormalActivity
import com.asterisk.tuandao.alarmstudy.ui.normal.NormalModule
import dagger.Component

@NormalActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [NormalModule::class])
interface NormalActivityComponent {
    fun inject(normalActivity: NormalActivity)
}