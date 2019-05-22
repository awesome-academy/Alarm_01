package com.asterisk.tuandao.alarmstudy.ui.detail

import Constants
import Constants.PERMISSION_READ_STORAGE
import android.Manifest
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
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
    private var alarmId = 0
    private var cacheAlarm = Alarm()
    private var cacheSelectedDay = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.asterisk.tuandao.alarmstudy.R.layout.activity_detail)

        initComponent()
        initAdapter()
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

    private fun initData() {
        alarmId = intent.getIntExtra(Constants.EXTRA_ALARM_ID, Constants.INVALID_ID)
        Log.d("DetailActivity","alarmId $alarmId")
        if (alarmId > 0) presenter.getEditAlarm(alarmId)
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
        //switch snooze
        switchSnooze.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cacheAlarm.isSnoozed = SWITCH_IS_NOT_CHECKED_STATE
                viewTransparentSnooze.isClickable = true
                viewTransparentSnooze.setBackgroundColor(resources.getColor(R.color.hide_transparent_view))
                textSnoozeContent.text = DEFAULT_ENABLED
            }
            else {
                cacheAlarm.isSnoozed = SWITCH_IS_CHECKED_STATE
                viewTransparentSnooze.isClickable = false
                viewTransparentSnooze.setBackgroundColor(resources.getColor(R.color.un_hide_transparent_view))
                textSnoozeContent.text = DEFAULT_DISABLED
            }
        }
        //switch snooze
        switchVibration.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cacheAlarm.isVibrated = SWITCH_IS_NOT_CHECKED_STATE
                Log.d("TAG","$SWITCH_IS_NOT_CHECKED_STATE")
                viewTransparentVibration.isClickable = true
                viewTransparentVibration.setBackgroundColor(resources.getColor(R.color.hide_transparent_view))
                textVibrateContent.text = DEFAULT_ENABLED
            }
            else {
                cacheAlarm.isVibrated = SWITCH_IS_CHECKED_STATE
                Log.d("TAG","$SWITCH_IS_CHECKED_STATE")
                viewTransparentVibration.isClickable = false
                viewTransparentVibration.setBackgroundColor(resources.getColor(R.color.un_hide_transparent_view))
                textVibrateContent.text = DEFAULT_DISABLED
            }
        }
        //button save
        buttonSave.setOnClickListener {
            val label = editAlarmName.text.toString()
            if (label != null) cacheAlarm.label = label
            cacheSelectedDay.forEach {
                cacheAlarm.daysOfWeek += it.toString()
            }
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

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.message_alert_title_permission))
                .setMessage(getString(R.string.message_alert_permission))
                .setPositiveButton(
                    getString(R.string.message_positive_button)
                ) { dialog, which ->
                    ActivityCompat.requestPermissions(this@DetailActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_READ_STORAGE)
                }
                .setNegativeButton(getString(R.string.message_cancel_alert_button)
                ) { dialog, which -> dialog.dismiss() }.create().show()

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
            cacheAlarm.selectedAlarmSound = it.id
            textAlarmSoundDetail.text = it.title
        }
        mAlarmSoundPickerDialog.show(supportFragmentManager, SOUND_PICKER_DIALOG_TAG)
    }

    override fun showAlarmSnooze() {
        mAlarmSnoozeDialog = AlarmSnoozeDialog.newInstance {
            cacheAlarm.selectedSnooze = it
            selectedSnooze(it)
        }
        mAlarmSnoozeDialog.show(supportFragmentManager, SNOOZE_PICKER_DIALOG_TAG)
    }

    override fun showAlarmVibration(vibrations: ArrayList<AlarmSound>) {
        mAlarmVibrationDialog = AlarmVibrationPickerDialog.newInstance(vibrations) {
            cacheAlarm.selectedVibration = it.id
            cacheAlarm.vibrationUri = it.uri
            textVibrateContent.text = it.title
        }
        vibrations.forEach {
            Log.d("showAlarmSound","id: ${it.id},title: ${it.title},uri: ${it.uri}")
        }
        mAlarmVibrationDialog.show(supportFragmentManager, VIBRATION_PICKER_DIALOG)
    }

    override fun showAlarmMethod() {
        mAlarmMethodDialog = AlarmMethodDialog.newInstance {
            cacheAlarm.method = it
            selectedMethod(it)
        }
        mAlarmMethodDialog.show(supportFragmentManager, METHOD_PICKER_DIALOG)
    }

    override fun showEditSetting(alarm: Alarm) {
        Log.d("DetailActivity","${alarm.id},${alarm.soundUri},${alarm.isVibrated},${alarm.method}" +
                "${alarm.selectedSnooze}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = alarm.hour
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.minute = alarm.minute
        }
        if (alarm.label!=null){
            editAlarmName.text = Editable.Factory.getInstance().newEditable(alarm.label)
        }
        if (alarm.soundTitle!=null) textAlarmSoundDetail.text = alarm.soundTitle
        if (alarm.vibrationTitle!=null) textVibrateContent.text = alarm.vibrationTitle
        if (alarm.isVibrated == SWITCH_IS_CHECKED_STATE) switchVibration.isEnabled = true
        selectedMethod(alarm.method!!)
        if (alarm.isSnoozed == SWITCH_IS_CHECKED_STATE) switchVibration.isEnabled = true
        if (alarm.snoozeTime!=null) textSnoozeContent.text = "${alarm.snoozeTime} ${Constants.PREFIX_MINUTE}"
    }

    override fun showAlarmSetting() {

    }

    override fun showDefaultSetting() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        cacheAlarm.hour = hour
        cacheAlarm.minute = minute
        cacheAlarm.soundTitle = DEFAULT_SOUND_TITLE
        cacheAlarm.soundUri = DEFAULT_SOUND_URI
        cacheAlarm.selectedAlarmSound = DEFAULT_SELECTED_SOUND
        textSnoozeContent.text = DEFAULT_DISABLED
        viewTransparentVibration.isClickable = false
        viewTransparentVibration.setBackgroundColor(resources.getColor(R.color.un_hide_transparent_view))
        viewTransparentSnooze.isClickable = false
        viewTransparentSnooze.setBackgroundColor(resources.getColor(R.color.un_hide_transparent_view))
//        switchSnooze.isEnabled = false
        textVibrateContent.text = DEFAULT_DISABLED
//        switchVibration.isEnabled = false
        textAlarmSoundDetail.text = DEFAULT_SOUND_TITLE
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

    private fun selectedMethod(index: Int){
        when(index) {
            NORMAL_METHOD -> textMethodContent.text = NORMAL_METHOD_TITLE
            VOCABULARY_METHOD -> textMethodContent.text = VOCABULARY_METHOD_TITLE
            TOIEC_METHOD -> textMethodContent.text = TOIEC_METHOD_TITLE
        }
    }

    private fun selectedSnooze(index: Int){
        when(index) {
            SNOOZE_FIVE -> textSnoozeContent.text = SNOOZE_TIME_FIVE
            SNOOZE_TEN -> textSnoozeContent.text = SNOOZE_TIME_TEN
            SNOOZE_FIFTHTEEN -> textSnoozeContent.text = SNOOZE_TIME_FIFTHTEEN
            SNOOZE_THIRTY -> textSnoozeContent.text = SNOOZE_TIME_THIRTY
        }
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
        const val DEFAULT_DISABLED = "Off"
        const val DEFAULT_ENABLED = "On"
        const val NORMAL_METHOD = 0
        const val NORMAL_METHOD_TITLE = "Normal"
        const val VOCABULARY_METHOD = 1
        const val VOCABULARY_METHOD_TITLE = "Vocabulary"
        const val TOIEC_METHOD = 2
        const val TOIEC_METHOD_TITLE = "TOIEC"
        const val MATH_METHOD = 3
        const val MATH_METHOD_TITLE = "math_method_title"
        const val SNOOZE_FIVE = 0
        const val SNOOZE_TIME_FIVE = "5 minutes"
        const val SNOOZE_TEN = 1
        const val SNOOZE_TIME_TEN = "10 minutes"
        const val SNOOZE_FIFTHTEEN = 2
        const val SNOOZE_TIME_FIFTHTEEN = "15 minutes"
        const val SNOOZE_THIRTY = 3
        const val SNOOZE_TIME_THIRTY = "30 minutes"
        const val DEFAULT_SELECTED_SOUND = 0
        const val DEFAULT_SOUND_TITLE = "Argon"
        const val DEFAULT_SOUND_URI = "content://media/internal/audio/media/12"
        const val DEFAULT_SELECTED_VIBRATION = 0
        const val DEFAULT_VIBRATION_TITLE = "Adara"
        const val DEFAULT_VIBRATION_URI = "content://media/internal/audio/media/12"
    }
}
