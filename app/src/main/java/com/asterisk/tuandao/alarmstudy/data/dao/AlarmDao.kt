package com.asterisk.tuandao.alarmstudy.data.dao

import com.asterisk.tuandao.alarmstudy.data.model.Alarm

interface AlarmDao {

    fun getAlarms(): List<Alarm>
}
