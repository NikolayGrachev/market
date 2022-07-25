package dev.grachev.feature_product_impl.di

import dagger.Component
import dev.grachev.core_network_api.NetworkApi
import dev.grachev.feature_product_impl.domain.repository.ProductRepository
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import ru.grachev.market.core_database_api.DatabaseApi
import java.lang.RuntimeException

@Component(
    modules = [ProductFeatureModule::class],
    dependencies = [ProductFeatureDependencies::class]
)
@FeatureScope
abstract class ProductFeatureComponent: ProductRepository {
    companion object {

        @Volatile
        var component: ProductFeatureComponent? = null
            private set

        fun initAndGet(productFeatureDependencies: ProductFeatureDependencies): ProductFeatureComponent? {
            if (component == null) {
                synchronized(ProductFeatureComponent::class) {
                    component = DaggerProductFeatureComponent.builder()
                        .productFeatureDependencies(productFeatureDependencies)
                        .build()
                }
            }
            return component
        }

        fun get(): ProductFeatureComponent? {
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
    @Component(dependencies = [NetworkApi::class, DatabaseApi::class])
    interface ProductFeatureDependenciesComponent : ProductFeatureDependencies
}