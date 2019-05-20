package com.asterisk.tuandao.alarmstudy.ui.home

import NUMBER_DAY_OF_WEEK
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.util.AlarmTimeUtils
import kotlinx.android.synthetic.main.item_day_of_week_home.view.*

class DayAdapter(private val context: Context, private val daysIsEnabled: List<Int>) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    private val mLayoutInflater = LayoutInflater.from(context)
    private var days = context.resources.getStringArray(R.array.days_home)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        DayViewHolder(mLayoutInflater.inflate(R.layout.item_day_of_week_home, viewGroup, false))

    override fun getItemCount() = NUMBER_DAY_OF_WEEK

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.onBind(day, position, daysIsEnabled)
    }

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(sDay: String, position: Int, daysIsEnabled: List<Int>) {
            with(itemView) {
                textDay.text = sDay
                if (daysIsEnabled!=null) {
                    if (AlarmTimeUtils.checkEnabledDay(position, daysIsEnabled)) {
                        textDay.setTextColor(resources.getColor(R.color.color_blue_text))
                    }
                }
            }
        }
    }
}
