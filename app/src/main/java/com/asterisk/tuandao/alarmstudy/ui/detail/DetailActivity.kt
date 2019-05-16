package com.asterisk.tuandao.alarmstudy.ui.detail

import EXTRA_ALARM_ID
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TimePicker
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.di.component.DaggerDetailActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.DetailActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.dialog.*
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
    private lateinit var mAlarmMethodDialog: AlarmMethodDialog
    private val TAG = this::class.java.simpleName
    private lateinit var mDetailActivityComponent: DetailActivityComponent
    private lateinit var mAdapter: DayAdapterDetail
    private var cacheAlarm = Alarm()
    private var cacheSelectedDay: String? = null
    private var alarmId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        alarmId = intent.getIntExtra(EXTRA_ALARM_ID,0)
        initComponent()
        initAdapter()
        handleEvent()
    }

    private fun initComponent() {
        mDetailActivityComponent = DaggerDetailActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .detailModule(DetailModule(this))
            .build()
        mDetailActivityComponent.inject(this)
    }

    private fun initAdapter() {
        mAdapter = DayAdapterDetail(this, resources.getStringArray(R.array.days), ::onListenerClickedDay)
        layoutSettingAlarm.recyclerDay.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutSettingAlarm.recyclerDay.adapter = mAdapter
    }

    private fun handleEvent() {
        //time picker
//        constraintAlarmTime.setOnClickListener {
//            presenter.getTimePicker()
//        }

        //alarm sound
        viewTransparentSound.setOnClickListener {
            presenter.getAlarmSound()
        }
        //alarm method
        viewTransparentMethod.setOnClickListener {
            presenter.getAlarmMethod()
        }
        //snooze
        viewTransparentSnooze.setOnClickListener {
            presenter.getAlarmSnooze()
        }
        //vibration
        viewTransparentVibration.setOnClickListener {
            presenter.getAlarmVibration()
        }

        //time picker
        timePicker.setIs24HourView(true)
        timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            Log.d(TAG,"hourOfDay $hourOfDay, minute  $minute")
            cacheAlarm.hour = hourOfDay
            cacheAlarm.minute = minute
        }

        //switch
        switchVibration.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d(TAG,"switchVibration ${isChecked}" )
            if (isChecked) cacheAlarm.isEnable = SWITCH_IS_CHECKED_STATE
            else cacheAlarm.isEnable = SWITCH_IS_NOT_CHECKED_STATE
        }

        //button save
        buttonSave.setOnClickListener {
            val label = editAlarmName.text.toString()
            if (label!=null) cacheAlarm.label = label
            presenter.addNewAlarm(cacheAlarm)
        }
    }

    override fun showTimePicker() {
        mTimePickerDialog.show(supportFragmentManager, TIME_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSound(alarms: ArrayList<AlarmSound>) {
        mAlarmSoundPickerDialog = AlarmSoundPickerDialog.newInstance(alarms) {
            cacheAlarm.soundUri = it.uri
            cacheAlarm.selectedAlarmSound = it.id
            Log.d(TAG,"showAlarmSound $it")
        }
        mAlarmSoundPickerDialog.show(supportFragmentManager, SOUND_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSnooze() {
        mAlarmSnoozeDialog = AlarmSnoozeDialog.newInstance {
            Log.d(TAG,"showAlarmSnooze $it")
            cacheAlarm.selectedSnooze = it
        }
        mAlarmSnoozeDialog.show(supportFragmentManager, SNOOZE_PICKER_DIALOG_TAG)
    }

    override fun showAlarmVibration(alarms: ArrayList<AlarmSound>) {
        mAlarmVibrationDialog = AlarmVibrationPickerDialog.newInstance(alarms) {
            Log.d(TAG,"showAlarmVibration $it")
            cacheAlarm.selectedVibration = it.id
            cacheAlarm.vibrationUri = it.uri
        }
        mAlarmVibrationDialog.show(supportFragmentManager, VIBRATION_PICKER_DIALOG)
    }

    override fun showAlarmMethod() {
        mAlarmMethodDialog = AlarmMethodDialog.newInstance{
            Log.d(TAG,"showAlarmMethod $it")
            cacheAlarm.method = it
        }
        mAlarmMethodDialog.show(supportFragmentManager,METHOD_PICKER_DIALOG)
    }

    override fun showAlarmSetting() {

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG,"onTimeSet")
        cacheAlarm.hour = hourOfDay
        cacheAlarm.minute = minute
    }

    fun onListenerClickedDay(day: Int) {
        Log.d(TAG,"day $day")
        cacheSelectedDay += day.toString()
    }

    override fun onResume() {
        super.onResume()
        if (alarmId!=0) presenter.getAlarmSetting(alarmId)
    }

    companion object {
        const val TIME_PICKER_DIALOG_TAG = "time_picker"
        const val SOUND_PICKER_DIALOG_TAG = "sound_picker"
        const val SNOOZE_PICKER_DIALOG_TAG = "snooze_picker"
        const val VIBRATION_PICKER_DIALOG = "vibration_picker"
        const val METHOD_PICKER_DIALOG = "method_picker"
        const val SWITCH_IS_NOT_CHECKED_STATE = 0
        const val SWITCH_IS_CHECKED_STATE = 1
    }
}
