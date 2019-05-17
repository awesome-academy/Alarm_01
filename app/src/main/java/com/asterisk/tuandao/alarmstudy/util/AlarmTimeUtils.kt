package com.asterisk.tuandao.alarmstudy.util

import NUMBER_MAX
import android.util.Log

object AlarmTimeUtils {

    fun getTimeString(hour: Int, minute: Int): String{
        var hourString = "$hour"
        var minuteString = "$minute"
        if (hour < NUMBER_MAX) hourString = "0$hour"
        if (minute < NUMBER_MAX) minuteString = "0$minute"
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
}
