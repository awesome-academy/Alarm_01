package com.asterisk.tuandao.alarmstudy.data.model

data class Alarm(val id: Int,
                 val timeInMinutes: Int,
                 val days: String,
                 val soundTitle: String,
                 val soundUri: String,
                 val isVibrated: Boolean,
                 val isEnabled: Boolean,
                 val method: Int,
                 val level: Int,
                 val label: String)
