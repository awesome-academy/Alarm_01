package com.asterisk.tuandao.alarmstudy.service

import com.asterisk.tuandao.alarmstudy.data.model.Alarm

interface MediaPlayerController {
    fun create(alarm: Alarm)

    fun destroyPlayer()
}