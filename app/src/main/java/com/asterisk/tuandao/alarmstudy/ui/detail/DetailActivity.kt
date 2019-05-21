package com.asterisk.tuandao.alarmstudy.ui.detail

import Constants
import Constants.PERMISSION_READ_STORAGE
import android.Manifest
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.broadcast.AlarmServiceBroadcastReceiver
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.di.component.DaggerDetailActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.DetailActivityComponent
import com.asterisk.tuandao.alarmstudy.ui.dialog.*
import com.asterisk.tuandao.alarmstudy.utils.removeElement
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.setting_feature_alarm.*
import kotlinx.android.synthetic.main.setting_feature_alarm.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class DetailActivity : AppCompatActivity(), DetailContract.View, TimePickerDialog.OnTimeSetListener {

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
        setContentView(com.asterisk.tuandao.alarmstudy.R.layout.activity_detail)

        initComponent()
        initAdapter()
        initDefault()
        handleEvent()
        initData()
    }

    private fun initComponent() {
        mDetailActivityComponent = DaggerDetailActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .detailModule(DetailModule(this))
            .build()
        mDetailActivityComponent.inject(this)
    }

    private fun initAdapter() {
        mAdapter = DayAdapterDetail(this,
            resources.getStringArray(com.asterisk.tuandao.alarmstudy.R.array.days), ::onListenerClickedDay)
        layoutSettingAlarm.recyclerDay.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutSettingAlarm.recyclerDay.adapter = mAdapter
    }

    private fun initDefault() {
//        val uriDefaulSound = this.getDefaultRington()
//        Log.d("uriDefaulSound", "$uriDefaulSound")
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        cacheAlarm.hour = hour
        cacheAlarm.minute = minute
//        cacheAlarm.soundUri = uriDefaulSound.toString()
    }

    private fun initData() {
        val alarmId = intent.getIntExtra(Constants.EXTRA_ALARM_ID, Constants.INVALID_ID)
        if (alarmId > 0) presenter.getAlarmSetting(alarmId)
        else presenter.start()
    }

    private fun handleEvent() {
        //alarm sound
        viewTransparentSound.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                presenter.getAlarmSound()
            } else {
                requestStoragePermission()
            }
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
            if (label != null) cacheAlarm.label = label
            cacheSelectedDay.forEach {
                cacheAlarm.daysOfWeek += it.toString()
            }
            cacheAlarm.isEnable = Constants.ALARM_IS_ENABLED
            Log.d("DetailActivity", "cacheAlarm ${cacheAlarm.soundUri}")
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

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.message_alert_title_permission))
                .setMessage(getString(R.string.message_alert_permission))
                .setPositiveButton(
                    getString(R.string.message_positive_button),
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(this@DetailActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_READ_STORAGE) })
                .setNegativeButton(getString(R.string.message_cancel_alert_button),
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).create().show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_READ_STORAGE)
        }
    }

    override fun showTimePicker() {
        mTimePickerDialog.show(supportFragmentManager, TIME_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSound(alarms: ArrayList<AlarmSound>) {
        mAlarmSoundPickerDialog = AlarmSoundPickerDialog.newInstance(alarms) {
            cacheAlarm.soundUri = it.uri
            Log.d("DetailActivity", "showAlarmSound ${cacheAlarm.soundUri}")
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
        mAlarmMethodDialog = AlarmMethodDialog.newInstance {
            cacheAlarm.method = it
        }
        mAlarmMethodDialog.show(supportFragmentManager, METHOD_PICKER_DIALOG)
    }

    override fun showAlarmSetting() {

    }

    override fun showDefaultSetting() {
//        val uriDefaulSound = this.getDefaultRington()
//        val defaultSoundTitle = this.getDefaultRingtonTitle(uriDefaulSound)
//        Log.d("DetailActivity", "defaultSoundTitle $defaultSoundTitle")
        //vibration content
        textVibrateContent.text = DEFAULT_ENABLE
        // snooze
        textSnoozeContent.text = DEFAULT_ENABLE
        //sound
//        textAlarmSoundDetail.text = defaultSoundTitle
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        cacheAlarm.hour = hourOfDay
        cacheAlarm.minute = minute
    }

    private fun onListenerClickedDay(day: Int, stateDay: Boolean) {
        Log.d(TAG, "day $day")
        if (stateDay) cacheSelectedDay.add(day)
        else cacheSelectedDay.removeElement(day)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_READ_STORAGE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.message_granted_permission), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.message_denied_permission), Toast.LENGTH_SHORT).show();
            }
        }
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
