package dev.grachev.ui_feature_network_state_monitor_impl.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import dev.grachev.ui_feature_network_state_monitor_impl.data.NetworkStateRepositoryImpl
import javax.inject.Singleton

@Module
class NetworkStateModule {
    @Singleton
    @Provides
    fun provideNetworkStateApi(context: Context): NetworkStateApi =
        NetworkStateRepositoryImpl(context)
}