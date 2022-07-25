package dev.grachev.ui_feature_network_state_monitor_impl.di

import dagger.Component
import dev.grachev.ui_feature_network_state_monitor_api.NetworkMonitorApi
import ru.grachev.market.core_utils.di.module.ContextModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    NetworkStateModule::class,
])
interface NetworkStateComponent: NetworkMonitorApi