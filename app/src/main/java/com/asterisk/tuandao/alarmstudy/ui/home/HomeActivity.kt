package com.asterisk.tuandao.alarmstudy.ui.home

import Constants
import Constants.EXTRA_ALARM_ID
import Constants.UPDATE_ALARM
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.broadcast.AlarmServiceBroadcastReceiver
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.di.component.DaggerHomeActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.HomeActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailActivity
import com.asterisk.tuandao.alarmstudy.utils.AlarmTimeUtils
import com.asterisk.tuandao.alarmstudy.utils.TAG
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeContract.View {

    @Inject
    override lateinit var presenter: HomeContract.Presenter
    private lateinit var mHomeActivityComponent: HomeActivityComponent
    private val alarms: MutableList<Alarm> = ArrayList()
    private lateinit var mTouchHelper: ItemTouchHelper

    private lateinit var mAdapter: HomeAdapter

    private val mUpdateAlarmReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG(), "action ${intent?.action}")
            if (intent?.action == Constants.UPDATE_ALARM) {
                presenter.start()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initToolbar()
        initComponent()
        initAdapter()
        handleEvent()
        initData()
    }

    private fun initComponent() {
        mHomeActivityComponent = DaggerHomeActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .homeModule(HomeModule(this))
            .build()
        mHomeActivityComponent.inject(this)
    }

    private fun initData() {
        presenter.start()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initAdapter() {
        recyclerAlarmInfo.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        mAdapter = HomeAdapter(this, alarms, ::onListenerClickedItem, ::onListenerSwitch, ::onDeleteItem)
        recyclerAlarmInfo.adapter = mAdapter
    }

    private fun handleEvent() {
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(mUpdateAlarmReceiver, IntentFilter(UPDATE_ALARM))
    }

    override fun showAlarms(alarms: ArrayList<Alarm>) {
        mAdapter.swapAlarms(alarms)
    }

    override fun showAlarmSetting() {
        val intent = Intent(this, DetailActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add -> presenter.addNewAlarm()
        }
        return true
    }

    override fun updateActiveAlarm(status: Boolean, alarm: Alarm) {
        val intent = Intent(this, AlarmServiceBroadcastReceiver::class.java)
        if (status) {
            intent.action = Constants.ACTION_ENABLE_ALARM
            sendBroadcast(intent)
        } else {
            val nextAlarm = AlarmTimeUtils.cacheNextAlarm
            val cancelAlarm = AlarmTimeUtils.getNewAlarm(alarm)
            if (AlarmTimeUtils.getCalendarAlarm(nextAlarm).timeInMillis
                == AlarmTimeUtils.getCalendarAlarm(cancelAlarm).timeInMillis && nextAlarm?.id!=null) {
                intent.action = Constants.ACTION_CANCEL_ALARM
                sendBroadcast(intent)
            }
        }
    }

    override fun updateDeleteAlarm() {
//        presenter.start()
    }

    private fun onListenerSwitch(alarm: Alarm, status: Boolean) {
        presenter.activeAlarm(alarm, status)
    }

    private fun onListenerClickedItem(alarmId: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_ALARM_ID, alarmId)
            startActivity(this)
        }
    }

    private fun onDeleteItem(alarmId: Int,position: Int) {
        presenter.deleteAlarm(alarmId)
        mAdapter.swapDeleteAlarm(position)
        Toast.makeText(this,"alarm deleted ",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(mUpdateAlarmReceiver)
    }
}
