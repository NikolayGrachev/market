package ru.grachev.market.core_database_impl.di


import android.app.Application
import dagger.Component
import ru.grachev.market.core_utils.di.module.ContextModule
import ru.grachev.market.core_database_api.DatabaseApi
import java.lang.RuntimeException
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, DatabaseModule::class])
abstract class DatabaseComponent: DatabaseApi {
    companion object {
        @Volatile
        var component: DatabaseComponent? = null
            private set

        fun initAndGet(appContext: Application): DatabaseApi? {
            if (component == null) {
                synchronized(DatabaseComponent::class) {
                    component = DaggerDatabaseComponent.builder()
                        .contextModule(ContextModule(appContext))
                        .build()
                }
            }
            return component
        }

        fun get(): DatabaseApi? {
            if (component == null) {
                throw RuntimeException("You must call 'initAndGet()' method")
            }
            return component
        }
    }
}