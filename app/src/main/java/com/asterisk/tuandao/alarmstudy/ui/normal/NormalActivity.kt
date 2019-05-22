package com.asterisk.tuandao.alarmstudy.ui.normal

import Constants
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.asterisk.tuandao.alarmstudy.R
import com.asterisk.tuandao.alarmstudy.base.MainApplication
import com.asterisk.tuandao.alarmstudy.broadcast.AlarmServiceBroadcastReceiver
import com.asterisk.tuandao.alarmstudy.data.model.Alarm
import com.asterisk.tuandao.alarmstudy.di.component.DaggerNormalActivityComponent
import com.asterisk.tuandao.alarmstudy.di.component.NormalActivityComponent
import com.asterisk.tuandao.alarmstudy.utils.AlarmTimeUtils
import com.asterisk.tuandao.alarmstudy.utils.showOverLockscreen
import kotlinx.android.synthetic.main.activity_normal.*
import javax.inject.Inject

class NormalActivity : AppCompatActivity(), NormalContract.View{

    @Inject
    override lateinit var presenter: NormalContract.Presenter

    private lateinit var mNormalActivityComponent: NormalActivityComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)
        showOverLockscreen()
        val id = intent.getIntExtra(Constants.TRIGGERED_ALARM_ID,-1)
        initComponent()
        handleEvent()
        if (id != -1) {
            presenter.setAlarmId(id)
            presenter.start()
        }
    }

    private fun initComponent() {
        mNormalActivityComponent = DaggerNormalActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .normalModule(NormalModule(this))
            .build()
        mNormalActivityComponent.inject(this)
    }

    private fun handleEvent() {
        buttonDismiss.setOnClickListener {
            val intent = Intent(this, AlarmServiceBroadcastReceiver::class.java)
            intent.action = Constants.ACTION_DISMISS_ALARM
            sendBroadcast(intent)
            finish()
        }
    }

    override fun showAlarmOff(alarm: Alarm) {
        val text = AlarmTimeUtils.getTimeString(alarm.hour, alarm.minute)
        textAlarmOffTime.text = text
    }
}
