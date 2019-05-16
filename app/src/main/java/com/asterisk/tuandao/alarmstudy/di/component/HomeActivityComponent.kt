package com.asterisk.tuandao.alarmstudy.di.component

import com.asterisk.tuandao.alarmstudy.di.HomeActivityScope
import com.asterisk.tuandao.alarmstudy.ui.home.HomeActivity
import com.asterisk.tuandao.alarmstudy.ui.home.HomeModule
import dagger.Component

@HomeActivityScope
@Component(modules = [HomeModule::class], dependencies = [ApplicationComponent::class])
interface HomeActivityComponent {
    fun inject(homeActivity: HomeActivity)
}