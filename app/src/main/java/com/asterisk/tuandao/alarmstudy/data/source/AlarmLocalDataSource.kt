package com.asterisk.tuandao.alarmstudy.data.source

import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmLocalDataSource @Inject constructor(
    val appDatabase: AppDatabase,
    val executor: AppExecutors
) : AlarmDataSource.Local
