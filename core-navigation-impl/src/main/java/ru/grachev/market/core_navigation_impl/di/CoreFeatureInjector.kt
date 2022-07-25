package ru.grachev.market.core_navigation_impl.di

import android.app.Application
import dev.grachev.core_network_impl.di.DaggerNetworkComponent
import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_impl.di.DaggerProductFeatureComponent_ProductFeatureDependenciesComponent
import dev.grachev.feature_product_impl.di.ProductFeatureComponent
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.feature_product_in_list_impl.di.DaggerProductInListFeatureComponent_ProductInListFeatureDependenciesComponent
import dev.grachev.feature_product_in_list_impl.di.ProductInListFeatureComponent
import dev.grachev.feature_product_worker.products_worker.di.DaggerProductsWorkerComponent_ProductsWorkerDependenciesComponent
import dev.grachev.feature_product_worker.products_worker.di.ProductsWorkerComponent
import dev.grachev.feature_product_worker_api.WorkerApi
import dev.grachev.ui_feature_network_state_monitor_impl.di.DaggerNetworkStateComponent
import ru.grachev.market.core_database_api.DatabaseApi
import ru.grachev.market.core_database_impl.di.DatabaseComponent
import ru.grachev.market.core_navigation_impl.di.component.NavigationComponent
import ru.grachev.market.core_utils.di.module.ContextModule
import java.lang.Exception


object CoreFeatureInjector {
    fun getNavigation() = NavigationComponent.initAndGet()

    fun getWorkerApi(context: Application): WorkerApi {
        val contextModule = ContextModule(context)

        return ProductsWorkerComponent.initAndGet(context,
            DaggerProductsWorkerComponent_ProductsWorkerDependenciesComponent.builder()
                .databaseApi(getDatabaseApi(context))
                .networkApi(
                    DaggerNetworkComponent.builder()
                        .contextModule(contextModule)
                        .build())
                .build()
        )?.getWorkerApi() ?: throw Exception("ProductsAPI not initialized")
    }

    fun getProductsApi(context: Application): ProductsApi {
        val contextModule = ContextModule(context)

        return ProductFeatureComponent.initAndGet(
            DaggerProductFeatureComponent_ProductFeatureDependenciesComponent.builder()
                .databaseApi(getDatabaseApi(context))
                .networkApi(
                    DaggerNetworkComponent.builder()
                        .contextModule(contextModule)
                        .build())
                .build()
        )?.getProductsApi() ?: throw Exception("ProductsAPI not initialized")
    }

    fun getProductsInListApi(context: Application): ProductsInListApi {
        val contextModule = ContextModule(context)

        return ProductInListFeatureComponent.initAndGet(
            DaggerProductInListFeatureComponent_ProductInListFeatureDependenciesComponent.builder()
                .databaseApi(getDatabaseApi(context))
                .networkApi(
                    DaggerNetworkComponent.builder()
                        .contextModule(contextModule)
                        .build())
                .workerApi(getWorkerApi(context))
                .build()
        )?.getProductsInListApi() ?: throw Exception("ProductsInListAPI not initialized")
    }

    fun getDatabaseApi(appContext: Application): DatabaseApi {
        return DatabaseComponent.initAndGet(appContext) ?: throw Exception("DatabaseApi not initialized")

        /*return DaggerSharedPreferencesComponent.builder()
            .gsonModule(GsonModule(Gson()))
            .contextModule(contextModule)
            .build()*/
    }

    fun getNetworkStateApi(context: Application) = DaggerNetworkStateComponent.builder()
        .contextModule(ContextModule(context))
        .build().getNetworkState()
}