package com.asterisk.tuandao.alarmstudy.service

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import javax.inject.Inject

class MediaPlayerManager @Inject constructor(val context: AlarmService) : MediaPlayerController{

    private var mMediaPlayer: MediaPlayer? = null
    override fun create(alarm: Alarm) {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.reset()
        } else {
            mMediaPlayer = MediaPlayer()
        }
        if (alarm != null) {
            val soundUri = Uri.parse(alarm.soundUri)
            mMediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_ALARM)
                setDataSource(context, soundUri)
                isLooping = true
                prepare()
                start()
            }
        }
    }

    override fun destroyPlayer() {
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        mMediaPlayer = null
    }
}