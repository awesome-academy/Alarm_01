package com.asterisk.tuandao.alarmstudy.util

import android.util.Log

object AlarmTimeUtils {

    fun getTimeString(hour: Int?, minute: Int?): String{
        return "$hour:$minute"
    }

    fun toDaysList(days: String?): List<Int> {
        var dayCharArray = days?.toCharArray()
        val dayList = ArrayList<Int>()
        dayCharArray?.forEach {
            dayList.add(it.toInt())
        }
        Log.d(".AlarmTimeUtils", "days: $dayList")
        return dayList
    }

    fun checkEnabbledDay(dayIndex: Int,dayIsEnabled: List<Int>): Boolean{
        var check = false
        for (day in dayIsEnabled) {
            if (dayIndex == day){
                check = true
                break
            }
        }
        return check
    }
}