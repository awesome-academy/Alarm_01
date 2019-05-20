package com.asterisk.tuandao.alarmstudy.di.component

import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmStorageRepository
import com.asterisk.tuandao.alarmstudy.di.module.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface ApplicationComponent {

    fun inject(mainApplication: MainApplication)

    fun getAlarmRepository(): AlarmRepository

    fun getAlarmStorageRepository(): AlarmStorageRepository
}
