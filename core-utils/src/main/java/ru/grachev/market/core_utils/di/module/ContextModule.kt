package ru.grachev.market.core_utils.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class ContextModule(private val app: Application) {
    @Provides
    fun provideAppContext(): Context = app.applicationContext
}
