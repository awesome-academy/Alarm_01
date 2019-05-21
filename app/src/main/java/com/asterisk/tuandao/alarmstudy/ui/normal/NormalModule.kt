package com.asterisk.tuandao.alarmstudy.ui.normal

import com.asterisk.tuandao.alarmstudy.di.NormalActivityContext
import com.asterisk.tuandao.alarmstudy.di.NormalActivityScope
import dagger.Module
import dagger.Provides

@Module
class NormalModule(val context: NormalContract.View) {

    @NormalActivityContext
    @Provides
    fun provideContext() = context as NormalActivity

    @NormalActivityScope
    @Provides
    fun provideNormalView(): NormalContract.View{
        return context
    }

    @NormalActivityScope
    @Provides
    fun providePresenter(presenter: NormalPresenter): NormalContract.Presenter {
        return presenter
    }
}