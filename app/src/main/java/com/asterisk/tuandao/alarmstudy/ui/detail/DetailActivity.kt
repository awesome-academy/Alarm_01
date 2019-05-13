package com.asterisk.tuandao.alarmstudy.ui.detail

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
import android.util.Log
=======
>>>>>>> handle setting alarm
import android.widget.TimePicker
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.di.component.DaggerDetailActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.DetailActivityComponent
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmSoundPickerDialog
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmTimePickerDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.setting_feature_alarm.*
=======
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmTimePickerDialog
import kotlinx.android.synthetic.main.activity_detail.*
>>>>>>> handle setting alarm
import kotlinx.android.synthetic.main.setting_feature_alarm.view.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View, TimePickerDialog.OnTimeSetListener{

    @Inject
    override lateinit var presenter: DetailContract.Presenter
    @Inject
    lateinit var mTimePickerDialog: AlarmTimePickerDialog
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
    @Inject
    lateinit var mAlarmSoundPickerDialog: AlarmSoundPickerDialog
    private val TAG = this::class.java.simpleName
=======
>>>>>>> handle setting alarm
    private lateinit var mDetailActivityComponent: DetailActivityComponent
    private lateinit var mAdapter: DayAdapterDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initComponent()
        initAdapter()
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
        initListener()
        handleTimePicker()
        handleAlarmSound()
=======
        handleTimePicker()
>>>>>>> handle setting alarm
    }

    private fun initComponent() {
        mDetailActivityComponent = DaggerDetailActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .detailModule(DetailModule(this))
            .build()
        mDetailActivityComponent.inject(this)
    }

    private fun initAdapter() {
        mAdapter = DayAdapterDetail(this, resources.getStringArray(R.array.days))
        layoutSettingAlarm.recyclerDay.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutSettingAlarm.recyclerDay.adapter = mAdapter
    }

<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
    fun initListener() {
        mAlarmSoundPickerDialog.setL
    }

    private fun handleTimePicker() {
        constraintAlarmTime.setOnClickListener {
            presenter.getTimePicker()
        }
    }

    private fun handleAlarmSound() {
        mAlarmSoundPickerDialog = AlarmSoundPickerDialog()

        viewTransparentSound.setOnClickListener {
            presenter.getAlarmSound()
=======
    private fun handleTimePicker() {
        constraintAlarmTime.setOnClickListener {
            presenter.openTimePicker()
>>>>>>> handle setting alarm
        }
    }

    override fun showTimePicker() {
        mTimePickerDialog.show(supportFragmentManager, TIME_PICKER_DIALOG_TAG)
    }

<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
    override fun showAlarmSound() {
        mAlarmSoundPickerDialog.show(supportFragmentManager, SOUND_PICKER_DIALOG_TAG)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG,"hourOfDay $hourOfDay, minute $minute")
    }




    companion object {
        const val TIME_PICKER_DIALOG_TAG = "time_picker"
        const val SOUND_PICKER_DIALOG_TAG = "time_picker"
=======
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
    }

    companion object {
        const val TIME_PICKER_DIALOG_TAG = "time_picker"
>>>>>>> handle setting alarm
    }
}
