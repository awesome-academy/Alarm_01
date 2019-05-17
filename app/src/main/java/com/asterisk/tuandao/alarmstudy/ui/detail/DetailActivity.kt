package com.asterisk.tuandao.alarmstudy.ui.detail

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TimePicker
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.di.component.DaggerDetailActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.DetailActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmTimePickerDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.setting_feature_alarm.view.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View, TimePickerDialog.OnTimeSetListener{

    @Inject
    override lateinit var presenter: DetailContract.Presenter
    @Inject
    lateinit var mTimePickerDialog: AlarmTimePickerDialog
    private lateinit var mDetailActivityComponent: DetailActivityComponent
    private lateinit var mAdapter: DayDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initComponent()
        initAdapter()
        handleTimePicker()
    }

    private fun initComponent() {
        mDetailActivityComponent = DaggerDetailActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .detailModule(DetailModule(this))
            .build()
        mDetailActivityComponent.inject(this)
    }

    private fun initAdapter() {
        mAdapter = DayDetailAdapter(this, resources.getStringArray(R.array.days))
        layoutSettingAlarm.recyclerDay.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutSettingAlarm.recyclerDay.adapter = mAdapter
    }

    private fun handleTimePicker() {
        constraintAlarmTime.setOnClickListener {
            presenter.openTimePicker()
        }
    }

    override fun showTimePicker() {
        mTimePickerDialog.show(supportFragmentManager, TIME_PICKER_DIALOG_TAG)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
    }

    companion object {
        const val TIME_PICKER_DIALOG_TAG = "time_picker"
    }
}
