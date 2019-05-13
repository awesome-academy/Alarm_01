package com.asterisk.tuandao.alarmstudy.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asterisk.tuandao.alarmstudy.R

class DetailFragment : Fragment() {

    private lateinit var mAdapter: DayDetailAdapter
    private lateinit var mRecyclerDay: RecyclerView
    private val days: List<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?
        , savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        initViews(view)
        initAdapter()
        return view
    }

    private fun initAdapter() {
        mAdapter = DayDetailAdapter(context!!, days)
        mRecyclerDay.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerDay.adapter = mAdapter
    }

    private fun initViews(view: View) {
        mRecyclerDay = view.findViewById(R.id.recyclerDay)
    }
}
