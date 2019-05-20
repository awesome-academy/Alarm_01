package com.asterisk.tuandao.alarmstudy.ui.detail

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.asterisk.tuandao.alarmstudy.R
import kotlinx.android.synthetic.main.item_day_of_week.view.*


class DayAdapterDetail(private val context: Context,
                       private val days: Array<String>,
                       private val clickedDay: (Int, Boolean) -> Unit) :
    RecyclerView.Adapter<DayAdapterDetail.DayDetailViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        DayAdapterDetail.DayDetailViewHolder(
            mLayoutInflater.inflate(R.layout.item_day_of_week, viewGroup, false)
        )

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayAdapterDetail.DayDetailViewHolder, position: Int) {
        val day = days[position]
        holder.onBind(day, position, clickedDay)

    }

    class DayDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stateDay = false
        fun onBind(sDay: String, position: Int, clickedDay: (Int, Boolean) -> Unit) {
            with(itemView) {
                textDayOfWeek.apply {
                    text = sDay
                    setOnClickListener {
                       if (!stateDay) {
                           stateDay = true
                           textDayOfWeek.setTextColor(resources.getColor(R.color.color_blue_text))
                       } else {
                           stateDay = false
                           textDayOfWeek.setTextColor(resources.getColor(R.color.color_white))
                       }
                        clickedDay(position,stateDay)
                    }
                }
            }
        }
    }

    companion object {
        const val RIGHT_MARGIN = 78
        const val TEXT_SIZE = 20
    }
}
