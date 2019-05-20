package com.asterisk.tuandao.alarmstudy.ui.dialog

import Constants.DEFAULT_SOUND_INDEX
import Constants.NEGATIVE_BUTTON
import Constants.POSITIVE_BUTTON
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.asterisk.tuandao.alarmstudy.R

class AlarmSnoozeDialog : DialogFragment() {

    private lateinit var mAlarmSnoozeDialog: AlertDialog.Builder
    private val TAG = this::class.java.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return builderSingleChoiceDialog().create()
    }

    private fun builderSingleChoiceDialog() : AlertDialog.Builder{
        var selectedItem = DEFAULT_SOUND_INDEX
        mAlarmSnoozeDialog = AlertDialog.Builder(activity)
        mAlarmSnoozeDialog.setSingleChoiceItems(resources.getStringArray(R.array.intervals),
            DEFAULT_SOUND_INDEX
        ) { dialog, which ->
            //user checked item
            selectedItem = which
        }

        mAlarmSnoozeDialog.setPositiveButton(POSITIVE_BUTTON) { dialog, which ->
            // user clicked ok
            callback(selectedItem)
        }

        mAlarmSnoozeDialog.setNegativeButton(NEGATIVE_BUTTON, { dialog, which ->

        })

        return mAlarmSnoozeDialog
    }

    companion object {
        private lateinit var callback: (Int) -> Unit

        fun newInstance(listener: (Int) -> Unit): AlarmSnoozeDialog {
            callback = listener
            return AlarmSnoozeDialog()
        }
    }
}
