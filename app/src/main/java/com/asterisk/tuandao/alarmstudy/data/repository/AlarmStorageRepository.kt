package com.asterisk.tuandao.alarmstudy.data.repository

import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.di.Local
import com.asterisk.tuandao.alarmstudy.di.Storage
import javax.inject.Inject

class AlarmStorageRepository @Inject constructor(
    @Storage val alarmStorageDataSource: AlarmDataSource.Local,
    @Local val alarmLocalDataSource: AlarmDataSource.Local
) : AlarmDataSource {

}