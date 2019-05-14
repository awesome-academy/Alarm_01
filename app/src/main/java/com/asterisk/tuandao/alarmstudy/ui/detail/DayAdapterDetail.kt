package com.asterisk.tuandao.alarmstudy.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.asterisk.tuandao.alarmstudy.R
import kotlinx.android.synthetic.main.item_day_of_week.view.*


class DayAdapterDetail(private val context: Context, private val days: Array<String>) :
    RecyclerView.Adapter<DayAdapterDetail.DayDetailViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        DayAdapterDetail.DayDetailViewHolder(
            mLayoutInflater.inflate(R.layout.item_day_of_week, viewGroup, false)
        )

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayAdapterDetail.DayDetailViewHolder, position: Int) {
        val day = days[position]
        holder.onBind(day, position, days.size)
    }

    class DayDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(sDay: String, position: Int, size: Int) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            if (position != size -1) layoutParams.rightMargin = RIGHT_MARGIN
            with(itemView) {
                textDayOfWeek.layoutParams = layoutParams
                textDayOfWeek.textSize = TEXT_SIZE.toFloat()
                textDayOfWeek.text = sDay
                textDayOfWeek.setTextColor(resources.getColor(R.color.color_white))
            }
        }
    }

    companion object {
        const val RIGHT_MARGIN = 78
        const val TEXT_SIZE = 20
    }
}
