package com.asterisk.tuandao.alarmstudy.base

import android.app.Application
import com.asterisk.tuandao.alarmstudy.di.component.ApplicationComponent
import com.asterisk.tuandao.alarmstudy.di.component.DaggerApplicationComponent
import com.asterisk.tuandao.alarmstudy.di.module.ApplicationModule

class MainApplication : Application() {

    lateinit var mApplicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getComponent() = mApplicationComponent
}
