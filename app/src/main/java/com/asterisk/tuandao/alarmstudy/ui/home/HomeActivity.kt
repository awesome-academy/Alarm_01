package com.asterisk.tuandao.alarmstudy.ui.home

import EXTRA_ALARM_ID
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.di.component.DaggerHomeActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.HomeActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailActivity
import com.asterisk.tuandao.alarmstudy.util.TAG
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity: AppCompatActivity(), HomeContract.View{

    @Inject
    override lateinit var presenter: HomeContract.Presenter
    private lateinit var mHomeActivityComponent: HomeActivityComponent
    private val alarms: MutableList<Alarm> = ArrayList()

    private lateinit var mAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initComponent()
        initToolbar()
        initAdapter()
        handleEvent()
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
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun initAdapter() {
        recyclerAlarmInfo.layoutManager = LinearLayoutManager(this)
        mAdapter = HomeAdapter(this, alarms, ::onListenerClickedItemt)
        recyclerAlarmInfo.adapter = mAdapter
    }

    private fun handleEvent() {

    }

    override fun showAlarms(sounds: ArrayList<Alarm>) {
        mAdapter.swapAlarms(sounds)
    }

    override fun showAlarmSetting() {
        val intent = Intent(this, DetailActivity::class.java).apply {
            startActivity(this)
        }
    }

    fun onListenerClickedItemt(position: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_ALARM_ID, position)
            startActivity(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.menu.menu_home -> presenter.addNewAlarm()
            else -> {
                Log.d(TAG(),"error")
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
}
