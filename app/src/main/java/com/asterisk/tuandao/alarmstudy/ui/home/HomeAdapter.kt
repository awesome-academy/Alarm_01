package com.asterisk.tuandao.alarmstudy.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.utils.AlarmTimeUtils
import kotlinx.android.synthetic.main.item_home_alarm.view.*

class HomeAdapter(
    private val context: Context,
    private var alarms: List<Alarm>,
    private val listenerItem: (Int) -> Unit,
    private val listenerSwitch: (Alarm, Boolean)  -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        HomeHolder(mLayoutInflater.inflate(R.layout.item_home_alarm,
            viewGroup, false))

    override fun getItemCount() = alarms.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val alarm = alarms[position]
        holder.onBind(alarm, listenerItem, listenerSwitch)
    }

    fun swapAlarms(newAlarms: List<Alarm>) {
        alarms = newAlarms
        notifyDataSetChanged()
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(alarm: Alarm, listenerItem: (Int) -> Unit, listenerSwitch: (Alarm, Boolean)  -> Unit) {
            with(itemView) {
                setOnClickListener {
                    listenerItem(alarm.id)
                }
                textAlarmTime.text = AlarmTimeUtils.getTimeString(alarm.hour, alarm.minute)
//                switchAlarm.isEnabled = alarm.isEnable == 1
                switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
                    listenerSwitch(alarm, isChecked)
                    Log.d("HomeAdapter", "switchAlarm $isChecked")
                }
                recyclerDayHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
                    false)
                recyclerDayHome.adapter = DayAdapter(context, AlarmTimeUtils.toDaysList(alarm.daysOfWeek))
            }
        }
    }
}
