package com.asterisk.tuandao.alarmstudy.ui.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.ui.data.model.Alarm
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val alarms: MutableList<Alarm> = ArrayList()
    private lateinit var mAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initToolbar()
        initAdapter()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAdapter() {
        recyclerAlarmInfo.layoutManager = LinearLayoutManager(this)
        mAdapter = HomeAdapter(this, alarms)
        recyclerAlarmInfo.adapter = mAdapter
    }
}
