package dev.grachev.feature_product_worker.products_worker.di

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dev.grachev.feature_product_worker.products_worker.data.ProductsWorkerRepositoryImpl
import dev.grachev.feature_product_worker.products_worker.factory.ProductsWorkerFactory
import dev.grachev.feature_product_worker_api.WorkerApi
import ru.grachev.market.core_database_api.DatabaseApi
import javax.inject.Singleton

@Module
class ProductsWorkerModule {
    @Singleton
    @Provides
    fun provideWorkManager(
        appContext: Context,
        workerFactory: ProductsWorkerFactory): WorkManager {
        try {
            val config = Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()

            WorkManager.initialize(appContext, config)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return WorkManager.getInstance(appContext.applicationContext)
    }

    @Singleton
    @Provides
    fun providesProductsWorker(workManager: WorkManager, database: DatabaseApi): WorkerApi =
        ProductsWorkerRepositoryImpl(workManager, database)
}