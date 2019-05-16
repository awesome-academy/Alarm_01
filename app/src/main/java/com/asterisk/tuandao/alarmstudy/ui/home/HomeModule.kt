package com.asterisk.tuandao.alarmstudy.ui.home

import com.asterisk.tuandao.alarmstudy.di.HomeActivityContext
import com.asterisk.tuandao.alarmstudy.di.HomeActivityScope
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailContract
import com.asterisk.tuandao.alarmstudy.ui.detail.DetailPresenter
import dagger.Module
import dagger.Provides

@Module
class HomeModule(val context: HomeContract.View) {

    @Provides
    @HomeActivityContext
    fun provideContext() = context as HomeActivity

    @Provides
    @HomeActivityScope
    fun provideDetailView(): HomeContract.View{
        return context
    }

    @Provides
    @HomeActivityScope
    fun provideDetailPresenter(presenter: HomePresenter): HomeContract.Presenter {
        return presenter
    }
}