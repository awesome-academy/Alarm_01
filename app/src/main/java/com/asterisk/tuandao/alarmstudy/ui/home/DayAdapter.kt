package com.asterisk.tuandao.alarmstudy.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asterisk.tuandao.alarmstudy.R
import kotlinx.android.synthetic.main.item_day_of_week.view.*

class DayAdapter(private val context: Context, private val days: List<String>) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {
    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        DayViewHolder(mLayoutInflater.inflate(R.layout.item_day_of_week, viewGroup, false))

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.onBind(day)
    }

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(sDay: String) {
            with(itemView) {
                textDayOfWeek.text = sDay
            }
        }
    }
}
