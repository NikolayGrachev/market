package dev.grachev.core_shared_preferences_impl.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dev.grachev.core_shared_preferences_impl.data.ProductsInListSPImpl
import dev.grachev.core_shared_preferences_impl.data.ProductsSPImpl
import ru.grachev.market.core_database_api.ProductsDatabaseApi
import ru.grachev.market.core_database_api.ProductsInListDatabaseApi
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            "market_core_prefs",
            Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideProductsInListSP(sp: SharedPreferences,
                                gson: Gson
    ): ProductsInListDatabaseApi =
        ProductsInListSPImpl(sp, gson)

    @Singleton
    @Provides
    fun provideProductsSP(sp: SharedPreferences,
                          gson: Gson): ProductsDatabaseApi =
        ProductsSPImpl(sp, gson)
}