package com.asterisk.tuandao.alarmstudy.di.component

import com.asterisk.tuandao.alarmstudy.di.AlarmServiceScope
import com.asterisk.tuandao.alarmstudy.service.AlarmService
import com.asterisk.tuandao.alarmstudy.service.ServiceModule
import dagger.Component

@AlarmServiceScope
@Component(dependencies = [ApplicationComponent::class], modules = [ServiceModule::class])
interface AlarmServiceComponent {
    fun inject(alarmService: AlarmService)
}