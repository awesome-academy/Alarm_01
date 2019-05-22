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
import com.asterisk.tuandao.alarmstudy.utils.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.item_home_alarm.view.*

class HomeAdapter(
    private val context: Context,
    private var alarms: List<Alarm>,
    private val listenerItem: (Int) -> Unit,
    private val listenerSwitch: (Alarm, Boolean)  -> Unit,
    private val listenerDelete: (Int,Int) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeHolder>(), ItemTouchHelperAdapter {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        HomeHolder(mLayoutInflater.inflate(R.layout.item_home_alarm,
            viewGroup, false))

    override fun getItemCount() = alarms.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val alarm = alarms[position]
        holder.onBind(alarm,position ,listenerItem, listenerSwitch, listenerDelete)
    }

    fun swapAlarms(newAlarms: List<Alarm>) {
        alarms = newAlarms
        notifyDataSetChanged()
    }

    fun swapDeleteAlarm(position: Int) {
        (alarms as ArrayList).removeAt(position)
        notifyItemChanged(position)
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(
            alarm: Alarm,
            position: Int,
            listenerItem: (Int) -> Unit,
            listenerSwitch: (Alarm, Boolean) -> Unit,
            listenerDelete: (Int,Int) -> Unit
            ) {
            with(itemView) {
                setOnClickListener {
                    listenerItem(alarm.id)
                }
                textAlarmTime.text = AlarmTimeUtils.getTimeString(alarm.hour, alarm.minute)
                when(alarm.isEnable) {
                    SWITCH_IS_CHECKED_STATE -> switchAlarm.isChecked = true
                    else -> switchAlarm.isChecked = false
                }
                switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
                    listenerSwitch(alarm,isChecked)
                }
                imageActionDelete.setOnClickListener {
                    listenerDelete(alarm.id,position)
                }

                if (alarm.label!=null) textLabel.text = alarm.label
                recyclerDayHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,
                    false)
                recyclerDayHome.adapter = DayAdapter(context, AlarmTimeUtils.toDaysList(alarm.daysOfWeek))
            }
        }
    }

    override fun onItemDismiss(position: Int) {
        Log.d("onItemDismiss","position $position")
        (alarms as ArrayList).removeAt(position)
        notifyItemRemoved(position)
        alarms.forEach {
            Log.d("alarm for each ","${it.id}")
        }
        Log.d("alarms position","${alarms[position].id}")
//        listenerDelete()
    }

    companion object {
        const val SWITCH_IS_NOT_CHECKED_STATE = 0
        const val SWITCH_IS_CHECKED_STATE = 1
    }
}
