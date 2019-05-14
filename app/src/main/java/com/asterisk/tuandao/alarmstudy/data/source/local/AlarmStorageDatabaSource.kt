package com.asterisk.tuandao.alarmstudy.data.source.local

import android.content.Context
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.di.ApplicationContext
import com.asterisk.tuandao.alarmstudy.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmStorageDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    val executor: AppExecutors
) : AlarmDataSource.Local {

}