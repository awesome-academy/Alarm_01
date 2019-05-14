package com.asterisk.tuandao.alarmstudy.data.repository

import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.di.Local
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmRepository @Inject constructor(@Local val alarmLocalDataSource: AlarmDataSource.Local)
    : AlarmDataSource
