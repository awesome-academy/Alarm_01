package com.asterisk.tuandao.alarmstudy.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

class AlarmTimePickerDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(activity, activity as TimePickerDialog.OnTimeSetListener,
            hour, minute, true)
    }
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f

=======
>>>>>>> handle setting alarm
}
