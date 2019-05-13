package com.asterisk.tuandao.alarmstudy.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.asterisk.tuandao.alarmstudy.R
import kotlinx.android.synthetic.main.item_day_of_week.view.*

class DayDetailAdapter(private val context: Context, private val days: List<String>) :
    RecyclerView.Adapter<DayDetailAdapter.DayDetailViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        DayDetailAdapter.DayDetailViewHolder(
            mLayoutInflater.inflate(R.layout.item_day_of_week, viewGroup, false)
        )

    override fun getItemCount() = days.size

    override fun onBindViewHolder(holder: DayDetailAdapter.DayDetailViewHolder, position: Int) {
        val day = days[position]
        holder.onBind(day)
    }

    class DayDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NewApi")
        fun onBind(sDay: String) {
            val layoutParams = ConstraintLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.rightMargin = RIGHT_MARGIN
            itemView.layoutParams = layoutParams
            with(itemView) {
                textDayOfWeek.text = sDay
                val drawableId = com.asterisk.tuandao.alarmstudy.R.drawable.circle_background_stroke
                textDayOfWeek.background = itemView.context.resources.getDrawable(drawableId)
            }
        }
    }

    companion object {
        const val RIGHT_MARGIN = 12
    }
}
