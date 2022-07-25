package dev.grachev.feature_product_in_list_impl.di

import dagger.Component
import dev.grachev.core_network_api.NetworkApi
import dev.grachev.feature_product_in_list_impl.domain.repository.ProductInListRepository
import dev.grachev.feature_product_worker_api.WorkerApi
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import ru.grachev.market.core_database_api.DatabaseApi
import java.lang.RuntimeException

@Component(
    modules = [ProductInListFeatureModule::class],
    dependencies = [ProductInListFeatureDependencies::class]
)
@FeatureScope
abstract class ProductInListFeatureComponent: ProductInListRepository {

    companion object {
        @Volatile
        var component: ProductInListFeatureComponent? = null
            private set

        fun initAndGet(productFeatureDependencies: ProductInListFeatureDependencies): ProductInListFeatureComponent? {
            if (component == null) {
                synchronized(ProductInListFeatureComponent::class) {
                    component = DaggerProductInListFeatureComponent.builder()
                        .productInListFeatureDependencies(productFeatureDependencies)
                        .build()
                }
            }
            return component
        }

        fun get(): ProductInListFeatureComponent? {
            if (component == null) {
                throw RuntimeException("You must call 'initAndGet(productFeatureDependencies: ProductFeatureDependencies)' method")
            }
            return component
        }

        fun resetComponent() {
            component = null
        }

    }

    // abstract fun inject(fragment: ProductsFragment)

    @FeatureScope
    @Component(dependencies = [NetworkApi::class, DatabaseApi::class, WorkerApi::class])
    interface ProductInListFeatureDependenciesComponent : ProductInListFeatureDependencies

}