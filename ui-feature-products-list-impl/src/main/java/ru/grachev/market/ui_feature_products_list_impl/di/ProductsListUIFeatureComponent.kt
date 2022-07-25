package ru.grachev.market.ui_feature_products_list_impl.di

import android.app.Application
import androidx.fragment.app.FragmentFactory
import dagger.Component
import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.core_utils.di.component.UIFeatureComponent
import ru.grachev.market.core_utils.di.module.ContextModule
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import ru.grachev.market.core_utils.di.factory.FragmentFactoryModule
import ru.grachev.market.ui_feature_products_list_api.ProductsNavigationApi

@Component(
    modules = [
        ContextModule::class,
        FragmentFactoryModule::class,
        FragmentBindsModule::class,
        ProductsListUIInteractorModule::class,
        ProductsListUIRepositoryModule::class],
    dependencies = [ProductsListUIFeatureDependencies::class]
)
@FeatureScope
abstract class ProductsListUIFeatureComponent: UIFeatureComponent {
    companion object {
        @Volatile
        var component: ProductsListUIFeatureComponent? = null
            private set

        fun initAndGet(appContext: Application, dependencies: ProductsListUIFeatureDependencies): ProductsListUIFeatureComponent? {
            if (component == null) {
                synchronized(ProductsListUIFeatureComponent::class) {
                    component = DaggerProductsListUIFeatureComponent.builder()
                        .contextModule(ContextModule(appContext))
                        .productsListUIFeatureDependencies(dependencies)
                        .build()
                }
            }
            return component
        }
    }

    override fun get(): ProductsListUIFeatureComponent {
        if (component == null) {
            throw RuntimeException("You must call 'initAndGet(productFeatureDependencies: ProductFeatureDependencies)' method")
        }
        return component!!
    }

    override fun resetComponent() {
        component = null
    }

    abstract override val fragmentFactory: FragmentFactory

    @FeatureScope
    @Component(dependencies = [
        ProductsInListApi::class,
        ProductsApi::class,
        ProductsNavigationApi::class,
        NetworkStateApi::class
    ])
    interface ProductsListUIFeatureDependenciesComponent : ProductsListUIFeatureDependencies

}