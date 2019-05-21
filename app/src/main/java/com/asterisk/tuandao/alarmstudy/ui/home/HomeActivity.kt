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
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        initComponent()
        initToolbar()
        initAdapter()
        handleEvent()
        presenter.start()
    }

    private fun initComponent() {
        mHomeActivityComponent = DaggerHomeActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .homeModule(HomeModule(this))
            .build()
        mHomeActivityComponent.inject(this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbarHome)
        toolbarHome.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initAdapter() {
        recyclerAlarmInfo.layoutManager = LinearLayoutManager(this)
        mAdapter = HomeAdapter(this, alarms, ::onListenerClickedItem, ::onListenerSwitch)
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
        return false
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

    private fun onListenerSwitch(alarm: Alarm, status: Boolean) {
        presenter.activeAlarm(alarm, status)
    }

    private fun onListenerClickedItem(alarmId: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_ALARM_ID, alarmId)
            startActivity(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(mUpdateAlarmReceiver)
    }
}
