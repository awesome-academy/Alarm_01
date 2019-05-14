package com.asterisk.tuandao.alarmstudy.ui.detail

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TimePicker
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.di.component.DaggerDetailActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.DetailActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmSnoozeDialog
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmSoundPickerDialog
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmTimePickerDialog
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmVibrationPickerDialog
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.setting_feature_alarm.*
import kotlinx.android.synthetic.main.setting_feature_alarm.view.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailContract.View, TimePickerDialog.OnTimeSetListener{

    @Inject
    override lateinit var presenter: DetailContract.Presenter
    @Inject
    lateinit var mTimePickerDialog: AlarmTimePickerDialog
    private lateinit var mAlarmSoundPickerDialog: AlarmSoundPickerDialog
    private lateinit var mAlarmSnoozeDialog: AlarmSnoozeDialog
    private lateinit var mAlarmVibrationDialog: AlarmVibrationPickerDialog
    private val TAG = this::class.java.simpleName
    private lateinit var mDetailActivityComponent: DetailActivityComponent
    private lateinit var mAdapter: DayAdapterDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initComponent()
        initAdapter()
        handleTimePicker()
        handleAlarmSound()
        handleAlarmMethod()
        handleAlarmSnooze()
        handleVibration()
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

//    fun initListener() {
//        mAlarmSoundPickerDialog = AlarmSoundPickerDialog.newInstance {
//            Log.d(TAG," $it")
//        }
//    }

    private fun handleTimePicker() {
        constraintAlarmTime.setOnClickListener {
            presenter.getTimePicker()
        }
    }

    private fun handleAlarmSound() {
        viewTransparentSound.setOnClickListener {
            presenter.getAlarmSound()
        }
    }

    private fun handleAlarmMethod() {
        viewTransparentMethod.setOnClickListener {
        }
    }

    private fun handleAlarmSnooze() {
        mAlarmSnoozeDialog = AlarmSnoozeDialog.newInstance {
            Log.d(TAG,"handleAlarmMethod $it")
        }

        viewTransparentSnooze.setOnClickListener {
            presenter.getAlarmSnooze()
        }
    }

    private fun handleVibration() {
        mAlarmVibrationDialog = AlarmVibrationPickerDialog.newInstance {
            Log.d(TAG,"handleVibration $it")
        }
    }

    override fun showTimePicker() {
        mTimePickerDialog.show(supportFragmentManager, TIME_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSound(alarms: ArrayList<AlarmSound>) {
        mAlarmSoundPickerDialog = AlarmSoundPickerDialog.newInstance(alarms) {
            Log.d(TAG,"handleAlarmSound $it")
        }
        mAlarmSoundPickerDialog.show(supportFragmentManager, SOUND_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSnooze() {
        mAlarmSnoozeDialog.show(supportFragmentManager, SNOOZE_PICKER_DIALOG_TAG)
    }

    override fun showAlarmVibration() {
        mAlarmVibrationDialog.show(supportFragmentManager, VIBRATION_PICKER_DIALOG)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG,"hourOfDay $hourOfDay, minute $minute")
    }

    companion object {
        const val TIME_PICKER_DIALOG_TAG = "time_picker"
        const val SOUND_PICKER_DIALOG_TAG = "sound_picker"
        const val SNOOZE_PICKER_DIALOG_TAG = "snooze_picker"
        const val VIBRATION_PICKER_DIALOG = "vibration_picker"
    }
}
