package com.asterisk.tuandao.alarmstudy.ui.detail

import android.support.v4.app.DialogFragment
import com.asterisk.tuandao.alarmstudy.di.DetailActivityContext
import com.asterisk.tuandao.alarmstudy.di.DetailActivityScope
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmSoundPickerDialog
=======
>>>>>>> handle setting alarm
import com.asterisk.tuandao.alarmstudy.ui.dialog.AlarmTimePickerDialog
import dagger.Module
import dagger.Provides

@Module
class DetailModule(val context: DetailContract.View) {

    @Provides
    @DetailActivityContext
    fun provideContext() = context as DetailActivity

    @Provides
    fun provideDetailView(): DetailContract.View{
        return context
    }

    @Provides
    @DetailActivityScope
    fun provideDetailPresenter(presenter: DetailPresenter): DetailContract.Presenter {
        return presenter
    }

    @Provides
    @DetailActivityScope
<<<<<<< 790db7c238b11d15bf54aaa72ba3063b7694c92f
    fun provideTimePickerDialog(): AlarmTimePickerDialog {
        return AlarmTimePickerDialog()
    }

    @Provides
    @DetailActivityScope
    fun provideAlarmSoundPickerDialog(): AlarmSoundPickerDialog {
        return AlarmSoundPickerDialog()
    }

=======
    fun provideTimePickerDialog(): AlarmTimePickerDialog{
        return AlarmTimePickerDialog()
    }
>>>>>>> handle setting alarm
}
