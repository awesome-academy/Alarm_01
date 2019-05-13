package com.asterisk.tuandao.alarmstudy.data.model

data class Alarm(
        val id: Int,
        val time: String,
        val days: String,
        val isEnable: Int?,
        val isVibrated: Int?,
        val soundTitle: String?,
        val soundUri: String?,
        val label: String?,
        val method: Int?,
        val level: Int?
)
