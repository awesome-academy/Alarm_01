package com.asterisk.tuandao.alarmstudy.ui.detail


import Constants
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TimePicker
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.broadcast.AlarmServiceBroadcastReceiver
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.di.component.DaggerDetailActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.DetailActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.dialog.*
import com.asterisk.tuandao.alarmstudy.utils.getDefaultRington
import com.asterisk.tuandao.alarmstudy.utils.getDefaultRingtonTitle
import com.asterisk.tuandao.alarmstudy.utils.removeElement
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.setting_feature_alarm.*
import kotlinx.android.synthetic.main.setting_feature_alarm.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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
    private var cacheSelectedDay = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val alarmId = intent.getIntExtra(Constants.EXTRA_ALARM_ID,0)
        initComponent()
        initAdapter()
        initDefault()
        handleEvent()
        if (alarmId!=0) presenter.getAlarmSetting(alarmId)
        else presenter.start()
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

    private fun initDefault() {
        val uriDefaulSound = this.getDefaultRington()
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        cacheAlarm.hour = hour
        cacheAlarm.minute = minute
        cacheAlarm.soundUri = uriDefaulSound.toString()
    }

    private fun handleEvent() {
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
            cacheAlarm.hour = hourOfDay
            cacheAlarm.minute = minute
        }
        //switch vibration
        switchVibration.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) cacheAlarm.isEnable = SWITCH_IS_CHECKED_STATE
            else cacheAlarm.isEnable = SWITCH_IS_NOT_CHECKED_STATE
        }
        //switch snooze
        switchVibration.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) cacheAlarm.isSnoozed = SWITCH_IS_CHECKED_STATE
            else cacheAlarm.isSnoozed = SWITCH_IS_NOT_CHECKED_STATE
        }
        //button save
        buttonSave.setOnClickListener {
            val label = editAlarmName.text.toString()
            if (label!=null) cacheAlarm.label = label
            cacheSelectedDay.forEach {
                cacheAlarm.daysOfWeek += it.toString()
            }
            Log.d("cacheAlarm.daysOfWeek","${cacheAlarm.daysOfWeek}")
            cacheAlarm.isEnable = Constants.ALARM_IS_ENABLED
            presenter.addNewAlarm(cacheAlarm)
            callAlarmService()
            sendNewAlarm()
            finish()
        }
        //button cancel
        buttonCancel.setOnClickListener {
            finish()
        }
    }

    private fun sendNewAlarm() {
        val intent = Intent(Constants.UPDATE_ALARM)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    private fun callAlarmService() {
        val intent = Intent(this, AlarmServiceBroadcastReceiver::class.java)
        intent.action = Constants.ACTION_ENABLE_ALARM
        sendBroadcast(intent)
    }

    override fun showTimePicker() {
        mTimePickerDialog.show(supportFragmentManager, TIME_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSound(alarms: ArrayList<AlarmSound>) {
        mAlarmSoundPickerDialog = AlarmSoundPickerDialog.newInstance(alarms) {
            cacheAlarm.soundUri = it.uri
            cacheAlarm.selectedAlarmSound = it.id
        }
        mAlarmSoundPickerDialog.show(supportFragmentManager, SOUND_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSnooze() {
        mAlarmSnoozeDialog = AlarmSnoozeDialog.newInstance {
            cacheAlarm.selectedSnooze = it
        }
        mAlarmSnoozeDialog.show(supportFragmentManager, SNOOZE_PICKER_DIALOG_TAG)
    }

    override fun showAlarmVibration(alarms: ArrayList<AlarmSound>) {
        mAlarmVibrationDialog = AlarmVibrationPickerDialog.newInstance(alarms) {
            cacheAlarm.selectedVibration = it.id
            cacheAlarm.vibrationUri = it.uri
        }
        mAlarmVibrationDialog.show(supportFragmentManager, VIBRATION_PICKER_DIALOG)
    }

    override fun showAlarmMethod() {
        mAlarmMethodDialog = AlarmMethodDialog.newInstance{
            cacheAlarm.method = it
        }
        mAlarmMethodDialog.show(supportFragmentManager,METHOD_PICKER_DIALOG)
    }

    override fun showAlarmSetting() {

    }

    override fun showDefaultSetting() {
        val uriDefaulSound = this.getDefaultRington()
        val defaultSoundTitle = this.getDefaultRingtonTitle(uriDefaulSound)
        //vibration content
        textVibrateContent.text = DEFAULT_ENABLE
        // snooze
        textSnoozeContent.text = DEFAULT_ENABLE
        //sound
        textAlarmSoundDetail.text = defaultSoundTitle
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        cacheAlarm.hour = hourOfDay
        cacheAlarm.minute = minute
    }

    private fun onListenerClickedDay(day: Int, stateDay: Boolean) {
        Log.d(TAG,"day $day")
        if (stateDay) cacheSelectedDay.add(day)
        else cacheSelectedDay.removeElement(day)
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        const val TIME_PICKER_DIALOG_TAG = "time_picker"
        const val SOUND_PICKER_DIALOG_TAG = "sound_picker"
        const val SNOOZE_PICKER_DIALOG_TAG = "snooze_picker"
        const val VIBRATION_PICKER_DIALOG = "vibration_picker"
        const val METHOD_PICKER_DIALOG = "method_picker"
        const val SWITCH_IS_NOT_CHECKED_STATE = 0
        const val SWITCH_IS_CHECKED_STATE = 1
        const val DEFAULT_HOUR = 6
        const val DEFAULT_MINUTE = 0
        const val DEFAULT_ENABLE = "Off"
    }
}
