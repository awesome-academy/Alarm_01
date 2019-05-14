package com.asterisk.tuandao.alarmstudy.ui.dialog

import ALARM_SOUND_TYPE_ALARM
import android.app.AlertDialog
import android.app.Dialog
import android.media.RingtoneManager
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.DialogFragment
import android.util.Log
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.util.getAlarmSounds

class AlarmSoundPickerDialog : DialogFragment() {

    private lateinit var mAlarmSoundPickerDialog: AlertDialog.Builder
    private val TAG = this::class.java.simpleName
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return builderSingleChoiceDialog().create()
    }

    private fun builderSingleChoiceDialog() :AlertDialog.Builder{
        var selectedItem = DEFAULT_SOUND_INDEX
        val arrAlarmSound = Array(mAlarmSounds.size){""}
        Log.d(TAG, "alarms $mAlarmSounds")
        mAlarmSounds.forEachIndexed { index, alarmSound ->
            Log.d(TAG, "index $index")
            arrAlarmSound[index] = alarmSound.title
        }

        mAlarmSoundPickerDialog = AlertDialog.Builder(activity)
        mAlarmSoundPickerDialog.setSingleChoiceItems(arrAlarmSound,
            DEFAULT_SOUND_INDEX
        ) { dialog, which ->
            //user checked itemt
            selectedItem = which
            Log.d(TAG, "which $which")
        }
        mAlarmSoundPickerDialog.setPositiveButton(POSITIVE_BUTTON) { dialog, which ->
            // user clicked ok
            callback(selectedItem)
        }
        mAlarmSoundPickerDialog.setNegativeButton(NEGATIVE_BUTTON) { dialog, which ->

        }

        return mAlarmSoundPickerDialog
    }

    companion object {
        const val DEFAULT_SOUND_INDEX = 0
        const val POSITIVE_BUTTON = "OK"
        const val NEGATIVE_BUTTON = "CANCEL"
        private lateinit var callback: (Int) -> Unit
        private lateinit var mAlarmSounds : ArrayList<AlarmSound>


        fun newInstance(sounds: ArrayList<AlarmSound>, listener: (Int) -> Unit): AlarmSoundPickerDialog {
            callback = listener
            mAlarmSounds = sounds
            return AlarmSoundPickerDialog()
        }
    }
}
