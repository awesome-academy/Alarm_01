package com.asterisk.tuandao.alarmstudy.ui.dialog

import DEFAULT_SOUND_INDEX
import NEGATIVE_BUTTON
import POSITIVE_BUTTON
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.data.model.AlarmSound
import com.asterisk.tuandao.alarmstudy.util.toArrayString

class AlarmMethodDialog : DialogFragment(){
    private lateinit var mAlarmMethodDialog: AlertDialog.Builder
    private val TAG = this::class.java.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return builderSingleChoiceDialog().create()
    }

    private fun builderSingleChoiceDialog() : AlertDialog.Builder{
        var selectedItem = DEFAULT_SOUND_INDEX
        mAlarmMethodDialog = AlertDialog.Builder(activity)
        mAlarmMethodDialog.setSingleChoiceItems(
            resources.getStringArray(R.array.methods),
            DEFAULT_SOUND_INDEX
        ) { dialog, which ->
            //user checked itemt
            selectedItem = which
            Log.d(TAG, "which $which")
        }
        mAlarmMethodDialog.setPositiveButton(POSITIVE_BUTTON) { dialog, which ->
            // user clicked ok
            callback(selectedItem)
        }
        mAlarmMethodDialog.setNegativeButton(NEGATIVE_BUTTON) { dialog, which ->

        }

        return mAlarmMethodDialog
    }

    companion object {
        private lateinit var callback: (Int) -> Unit

        fun newInstance(listener: (Int) -> Unit): AlarmMethodDialog {
            callback = listener
            return AlarmMethodDialog()
        }
    }
}