package dev.grachev.core_network_impl.di

import dagger.Component
import dev.grachev.core_network_api.NetworkApi
import ru.grachev.market.core_utils.di.module.ContextModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    NetworkModule::class,
])
interface NetworkComponent: NetworkApi