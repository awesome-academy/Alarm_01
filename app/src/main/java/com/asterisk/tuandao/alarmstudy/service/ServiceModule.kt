package com.asterisk.tuandao.alarmstudy.service

import android.content.Context
import com.asterisk.tuandao.alarmstudy.di.AlarmServiceScope
import dagger.Module
import dagger.Provides

@Module
class ServiceModule(val context: AlarmService){

    @Provides
    fun provideService(): AlarmService {
        return context
    }

    @AlarmServiceScope
    @Provides
    fun provideMediaPlayerManager(mediaPlayerManager: MediaPlayerManager): MediaPlayerController{
        return mediaPlayerManager
    }
}