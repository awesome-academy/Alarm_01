package com.asterisk.tuandao.alarmstudy.broadcast

import Constants
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.asterisk.tuandao.alarmstudy.service.AlarmService
import android.support.v4.content.ContextCompat.startForegroundService
import android.os.Build



class AlarmServiceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, AlarmService::class.java)
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent?.action)
            or Constants.UPDATE_ALARM.equals(intent?.action)
            or Constants.ACTION_ENABLE_ALARM.equals(intent?.action)
        ) {
            serviceIntent.action = Constants.ACTION_SCHEDULE_ALARM
        }
        if (Constants.ACTION_CANCEL_ALARM.equals(intent?.action)) {
            serviceIntent.action = Constants.ACTION_CANCEL_ALARM
        }
        if (Constants.ACTION_TRIGGER_ALARM.equals(intent?.action)) {
            val alarmId = intent?.getIntExtra(Constants.TRIGGERED_ALARM_ID, 0)
            serviceIntent.putExtra(Constants.TRIGGERED_ALARM_ID, alarmId)
            serviceIntent.action = Constants.ACTION_TRIGGER_ALARM
        }
        if (Constants.ACTION_DISMISS_ALARM.equals(intent?.action)) {
            serviceIntent.action = Constants.ACTION_DISMISS_ALARM
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(serviceIntent)
        } else {
            context?.startService(serviceIntent)
        }
    }
}
