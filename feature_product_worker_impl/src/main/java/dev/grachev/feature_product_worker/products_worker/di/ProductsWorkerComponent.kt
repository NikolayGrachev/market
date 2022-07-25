package dev.grachev.feature_product_worker.products_worker.di

import android.app.Application
import dagger.Component
import dev.grachev.core_network_api.NetworkApi
import dev.grachev.feature_product_worker.products_worker.domain.WorkerRepository
import ru.grachev.market.core_database_api.DatabaseApi
import ru.grachev.market.core_utils.di.module.ContextModule
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContextModule::class,
    ProductsWorkerModule::class],
    dependencies = [ProductsWorkerDependencies::class])
abstract class ProductsWorkerComponent: WorkerRepository {
    companion object {

        @Volatile
        var component: ProductsWorkerComponent? = null
            private set

        fun initAndGet(appContext: Application, dependencies: ProductsWorkerDependenciesComponent):
                ProductsWorkerComponent? {
            if (component == null) {
                synchronized (ProductsWorkerComponent::class) {
                    component = DaggerProductsWorkerComponent.builder()
                        .contextModule(ContextModule(appContext))
                        .productsWorkerDependencies(dependencies)
                        .build()
                }
            }
            return component
        }

        fun get(): ProductsWorkerComponent? {
            if (component == null) {
                throw RuntimeException("You must call 'initAndGet(productFeatureDependencies: ProductFeatureDependencies)' method")
            }
            return component
        }

        fun resetComponent() {
            component = null
        }

    }

    @FeatureScope
    @Component(dependencies = [
        NetworkApi::class,
        DatabaseApi::class
    ])
    interface ProductsWorkerDependenciesComponent : ProductsWorkerDependencies
}