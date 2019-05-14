package com.asterisk.tuandao.alarmstudy.di.module

import android.content.Context
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import com.asterisk.tuandao.alarmstudy.data.source.local.AlarmLocalDataSource
import com.asterisk.tuandao.alarmstudy.data.source.local.AlarmStorageDataSource
import com.asterisk.tuandao.alarmstudy.data.source.local.AppDatabase
import com.asterisk.tuandao.alarmstudy.di.*
import com.asterisk.tuandao.alarmstudy.util.AlarmDatabaseUtils
import com.asterisk.tuandao.alarmstudy.util.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class DataModule {


//    @Provides
//    @Singleton
//    fun provideRepository(alarmRepository: AlarmRepository): AlarmDataSource {
//        return alarmRepository
//    }

    @Provides
    @Singleton
    @Local
    fun provideLocalDataSource(alarmLocalDataSource: AlarmLocalDataSource): AlarmDataSource.Local {
        return alarmLocalDataSource
    }

    @Provides
    @Singleton
    @Storage
    fun provideStorageDataSource(alarmStorageDataSource: AlarmStorageDataSource): AlarmDataSource.Local {
        return alarmStorageDataSource
    }

    @Provides
    @Singleton
    @DatabaseName
    fun provideDatabaseName() = AlarmDatabaseUtils.DATABASE_NAME

    @Provides
    @Singleton
    @DatabaseVersion
    fun provideDatabaseVersion() = AlarmDatabaseUtils.DATABASE_VERSION

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName DATABASE_NAME: String,
        @DatabaseVersion DATABASE_VERSION: Int
    ): AppDatabase {
        return AppDatabase(
            context,
            DATABASE_NAME,
            DATABASE_VERSION
        )
    }

    @Provides
    @Singleton
    fun provideExecutor(): AppExecutors {
        return AppExecutors()
    }
}