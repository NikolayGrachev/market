package dev.grachev.core_shared_preferences_impl.di


import dagger.Component
import ru.grachev.market.core_database_api.DatabaseApi
import ru.grachev.market.core_utils.di.module.ContextModule
import ru.grachev.market.core_utils.di.module.GsonModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ContextModule::class,
    GsonModule::class,
    SharedPreferencesModule::class])
interface SharedPreferencesComponent: DatabaseApi
