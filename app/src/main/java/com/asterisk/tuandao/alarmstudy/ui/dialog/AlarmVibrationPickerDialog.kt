package com.asterisk.tuandao.alarmstudy.ui.dialog

import Constants.DEFAULT_SOUND_INDEX
import Constants.NEGATIVE_BUTTON
import Constants.POSITIVE_BUTTON
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.utils.toArrayString

class AlarmVibrationPickerDialog : DialogFragment(){
    private lateinit var mAlarmVibrationPickerDialog: AlertDialog.Builder
    private val TAG = this::class.java.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return builderSingleChoiceDialog().create()
    }

    private fun builderSingleChoiceDialog() : AlertDialog.Builder{
        var selectedItem = DEFAULT_SOUND_INDEX

        mAlarmVibrationPickerDialog = AlertDialog.Builder(activity)
        mAlarmVibrationPickerDialog.setSingleChoiceItems(
            alarmSounds.toArrayString(),
            DEFAULT_SOUND_INDEX
        ) { dialog, which ->
            //user checked item
            selectedItem = which
        }

        mAlarmVibrationPickerDialog.setPositiveButton(POSITIVE_BUTTON) { dialog, which ->
            // user clicked ok
            callback(alarmSounds[selectedItem])
        }

        mAlarmVibrationPickerDialog.setNegativeButton(NEGATIVE_BUTTON, { dialog, which ->

        })

        return mAlarmVibrationPickerDialog
    }

    companion object {
        private lateinit var callback: (AlarmSound) -> Unit
        private lateinit var alarmSounds : ArrayList<AlarmSound>

        fun newInstance(sounds: ArrayList<AlarmSound>,listener: (AlarmSound) -> Unit): AlarmVibrationPickerDialog {
            callback = listener
            alarmSounds = sounds
            return AlarmVibrationPickerDialog()
        }
    }
}
