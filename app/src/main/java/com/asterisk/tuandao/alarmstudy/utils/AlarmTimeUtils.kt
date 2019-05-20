package com.asterisk.tuandao.alarmstudy.utils

import Constants
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.asterisk.tuandao.alarmstudy.broadcast.AlarmServiceBroadcastReceiver
import com.asterisk.tuandao.alarmstudy.data.AlarmDataSource
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.repository.AlarmRepository
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList


object AlarmTimeUtils {
    var cacheNextAlarm = Alarm()

    fun getTimeString(hour: Int, minute: Int): String{
        var hourString = "$hour"
        var minuteString = "$minute"
        if (hour < Constants.NUMBER_MAX) hourString = "0$hour"
        if (minute < Constants.NUMBER_MAX) minuteString = "0$minute"
        return "$hourString:$minuteString"
    }

    fun toDaysList(days: String?): List<Int> {
        val dayList = ArrayList<Int>()
        if (days!=null) {
            val s = days?.split("null")[1].trim().split("")
            s.forEach {
                it.toIntOrNull()?.let {
                    if (it != null) dayList.add(it)
                }
            }
        }
        return dayList
    }

    fun checkEnabledDay(dayIndex: Int,dayIsEnabled: List<Int>): Boolean{
        var check = false
        for (day in dayIsEnabled) {
            if (day == dayIndex){
                check = true
                break
            }
        }
        return check
    }

    fun scheduleAlarm(repository: AlarmRepository, applicationContext: Context) {
        repository.getAlarms(object : AlarmDataSource.LoadAlarmCallback{
            override fun onSuccess(alarms: ArrayList<Alarm>) {
                val nextAlarm = getNextAlarm(alarms)
                establishAlarmManager(nextAlarm, applicationContext)
            }
            override fun onFailure() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    fun getNextAlarm(alarms: ArrayList<Alarm>): Alarm{
        val alarmQueue = initAlarmQueue()
        alarms.forEach {
            if (it.isEnable == Constants.ALARM_IS_ENABLED) {
                val alarm = getNewAlarm(it)
                alarmQueue.add(alarm)
                val daysOfWeek = toDaysList(it.daysOfWeek)
                if (!daysOfWeek.isEmpty()) {
                    daysOfWeek.forEach { day ->
                        val alarm = getNewAlarm(it, day)
                        alarmQueue.add(alarm)
                    }
                }
            }
        }
        val alarm = alarmQueue.pollFirst()
        cacheNextAlarm = alarm
        return alarm
    }

    fun initAlarmQueue() = TreeSet(object :Comparator<Alarm> {
            override fun compare(previousAlarm: Alarm, nextAlarm: Alarm): Int {
                val diff = getCalendarAlarm(previousAlarm).timeInMillis - getCalendarAlarm(nextAlarm).timeInMillis
                if (diff > 0) return 1
                if (diff < 0) return -1
                return  0
            }
        })
    //condition diff = 0
    fun getNewAlarm(alarm: Alarm) :Alarm{
        val alarmTime = Calendar.getInstance()
        alarmTime.set(Calendar.HOUR_OF_DAY, alarm.hour)
        alarmTime.set(Calendar.MINUTE, alarm.minute)
        if (alarmTime.before(Calendar.getInstance())) {
            alarmTime.add(Calendar.DAY_OF_MONTH,1)
        }
        val cloneAlarm = Alarm()
        cloneAlarm.hour = alarmTime.get(Calendar.HOUR_OF_DAY)
        cloneAlarm.minute = alarmTime.get(Calendar.MINUTE)
        cloneAlarm.month = alarmTime.get(Calendar.MONTH)
        cloneAlarm.dayOfMonth = alarmTime.get(Calendar.DAY_OF_MONTH)
        cloneAlarm.year = alarmTime.get(Calendar.YEAR)
        return cloneAlarm
    }
    //condition diff!=0
    fun getNewAlarm(alarm: Alarm, day: Int): Alarm{
        val newAlarmTime = Calendar.getInstance()
        val newCurrentTime = Calendar.getInstance()
        newAlarmTime.set(Calendar.HOUR_OF_DAY, alarm.hour)
        newAlarmTime.set(Calendar.MINUTE, alarm.minute)
        //convert corresponding Calandar API DAY OF WEEK 2,3,4,5,6,7,1 to 0,1,2,3,4,5,6
        val currentDayOfWeek = (newCurrentTime.get(Calendar.DAY_OF_WEEK) - 2)
            .negativeMod(Constants.NUMBER_DAY_OF_WEEK)
        val diff = currentDayOfWeek - day
        var diffDayOfMonth = 0
        if (diff<0) {
            diffDayOfMonth = newCurrentTime.get(Calendar.DAY_OF_MONTH) + Math.abs(diff)
        }
        if (diff>0){
            diffDayOfMonth = newCurrentTime.get(Calendar.DAY_OF_MONTH)
            + Constants.NUMBER_DAY_OF_WEEK - Math.abs(diff)
        }
        if (diff == 0) {
            if (newAlarmTime.before(newCurrentTime)) {
                newAlarmTime.add(Calendar.DAY_OF_MONTH,1)
                diffDayOfMonth = newAlarmTime.get(Calendar.DAY_OF_MONTH)
            }else {
                diffDayOfMonth = newAlarmTime.get(Calendar.DAY_OF_MONTH)
            }
        }

        newAlarmTime.set(Calendar.DAY_OF_MONTH, diffDayOfMonth)
        //create a new alarm
        val cloneAlarm = Alarm()
        cloneAlarm.hour = newAlarmTime.get(Calendar.HOUR_OF_DAY)
        cloneAlarm.minute = newAlarmTime.get(Calendar.MINUTE)
        cloneAlarm.month = newAlarmTime.get(Calendar.MONTH)
        cloneAlarm.dayOfMonth = newAlarmTime.get(Calendar.DAY_OF_MONTH)
        cloneAlarm.year = newAlarmTime.get(Calendar.YEAR)
        return cloneAlarm
    }

    fun getCalendarAlarm(alarm: Alarm): Calendar{
        val newCalendarAlarm = Calendar.getInstance()
        newCalendarAlarm.set(alarm.year!!, alarm.month!!, alarm.dayOfMonth!!
        ,alarm.hour, alarm.minute)
        return newCalendarAlarm
    }

    fun establishAlarmManager(alarm: Alarm, context: Context) {
        val myIntent = Intent(context, AlarmServiceBroadcastReceiver::class.java)
        myIntent.action = Constants.ACTION_TRIGGER_ALARM
        myIntent.putExtra(Constants.TRIGGERED_ALARM_ID, alarm.id)
        val pendingIntent = PendingIntent.getBroadcast(context, 0,
            myIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, getCalendarAlarm(alarm).timeInMillis, pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        val myIntent = Intent(context, AlarmServiceBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0,
            myIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
