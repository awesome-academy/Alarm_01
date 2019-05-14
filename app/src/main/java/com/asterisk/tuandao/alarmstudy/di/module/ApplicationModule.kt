package com.asterisk.tuandao.alarmstudy.di.module

import android.app.Application
import android.content.Context
import com.asterisk.tuandao.alarmstudy.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }
}
