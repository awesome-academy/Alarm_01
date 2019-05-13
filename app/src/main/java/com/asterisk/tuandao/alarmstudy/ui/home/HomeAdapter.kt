package com.asterisk.tuandao.alarmstudy.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_home_alarm.view.*

class HomeAdapter(private val context: Context,
                  private val alarms: List<Alarm>)
    : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
            HomeHolder(mLayoutInflater
                    .inflate(R.layout.item_home_alarm, viewGroup, false))

    override fun getItemCount() = alarms.size

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val alarm = alarms[position]
        holder.onBind(alarm)
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(alarm: Alarm) {
            with(itemView) {
                recyclerDays.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                textAlarmTime.text = alarm.time
                recyclerDay.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                val adapter = DayAdapter(context, alarm.days)
                recyclerDay.adapter = adapter
            }
        }
    }
}
