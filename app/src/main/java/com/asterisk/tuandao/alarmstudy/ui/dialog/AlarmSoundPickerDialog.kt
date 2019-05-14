package com.asterisk.tuandao.alarmstudy.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import com.asterisk.tuandao.alarmstudy.R

class AlarmSoundPickerDialog : DialogFragment() {

    private lateinit var mAlarmSoundPickerDialog: AlertDialog.Builder
    private val TAG = this::class.java.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return builderSingleChoiceDialog().create()
    }

    private fun builderSingleChoiceDialog() :AlertDialog.Builder{
        var selectedItem = DEFAULT_SOUND_INDEX
        mAlarmSoundPickerDialog = AlertDialog.Builder(activity)
        mAlarmSoundPickerDialog.setSingleChoiceItems(resources.getStringArray(R.array.intervals),
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

        fun newInstance(listener: (Int) -> Unit): AlarmSoundPickerDialog {
            callback = listener
            return AlarmSoundPickerDialog()
        }
    }
}
