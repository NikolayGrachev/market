package ru.grachev.market.core_utils.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class GsonModule(private val gson: Gson) {
    @Singleton
    @Provides
    fun provideGson(): Gson = gson
}
