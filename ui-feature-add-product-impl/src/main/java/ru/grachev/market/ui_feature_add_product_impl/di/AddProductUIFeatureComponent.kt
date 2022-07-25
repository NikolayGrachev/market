package ru.grachev.market.ui_feature_add_product_impl.di

import androidx.fragment.app.FragmentFactory
import dagger.Component
import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.core_utils.di.component.UIFeatureComponent
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import ru.grachev.market.core_utils.di.factory.FragmentFactoryModule
import ru.grachev.market.ui_feature_add_product_api.AddProductNavigationApi

@Component(
    modules = [
        FragmentFactoryModule::class,
        FragmentBindsModule::class,
        AddProductUIInteractorModule::class,
        AddProductUIRepositoryModule::class],
    dependencies = [AddProductUIFeatureDependencies::class]
)
@FeatureScope
abstract class AddProductUIFeatureComponent: UIFeatureComponent {

    companion object {

        @Volatile
        var component: AddProductUIFeatureComponent? = null
            private set

        fun initAndGet(dependencies: AddProductUIFeatureDependencies): AddProductUIFeatureComponent? {
            if (component == null) {
                synchronized(AddProductUIFeatureComponent::class) {
                    component = DaggerAddProductUIFeatureComponent.builder()
                        .addProductUIFeatureDependencies(dependencies)
                        .build()
                }
            }
            return component
        }
    }

    override fun get(): AddProductUIFeatureComponent {
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
        ProductsApi::class,
        ProductsInListApi::class,
        AddProductNavigationApi::class,
        NetworkStateApi::class])
    interface AddProductUIFeatureDependenciesComponent : AddProductUIFeatureDependencies

}