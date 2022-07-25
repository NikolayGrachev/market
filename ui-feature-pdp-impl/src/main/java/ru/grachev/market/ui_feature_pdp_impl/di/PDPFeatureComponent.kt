package ru.grachev.market.ui_feature_pdp_impl.di

import android.util.Log
import androidx.fragment.app.FragmentFactory
import dagger.Component
import dev.grachev.feature_product_api.ProductsApi
import dev.grachev.feature_product_in_list_api.ProductsInListApi
import dev.grachev.ui_feature_network_state_monitor_api.NetworkStateApi
import ru.grachev.market.core_utils.di.component.UIFeatureComponent
import ru.grachev.market.core_utils.di.annotation.FeatureScope
import ru.grachev.market.core_utils.di.factory.FragmentFactoryModule
import ru.grachev.market.ui_feature_pdp_api.PDPNavigationApi
import java.lang.RuntimeException

@Component(
    modules = [
        FragmentFactoryModule::class,
        FragmentBindsModule::class,
        PDPInteractorModule::class,
        PDPRepositoryModule::class],
    dependencies = [PDPFeatureDependencies::class]
)
@FeatureScope
abstract class PDPFeatureComponent: UIFeatureComponent {

    companion object {

        @Volatile
        var component: PDPFeatureComponent? = null
            private set

        fun initAndGet(pdpFeatureDependencies: PDPFeatureDependencies): PDPFeatureComponent? {
            if (component == null) {
                synchronized(PDPFeatureComponent::class) {
                    component = DaggerPDPFeatureComponent.builder()
                        .pDPFeatureDependencies(pdpFeatureDependencies)
                        .build()
                }
            }

            Log.d("tag", "initFeature PDPFeatureComponent")
            return component
        }
    }

    override fun get(): PDPFeatureComponent {
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
        PDPNavigationApi::class,
        NetworkStateApi::class])
    interface PDPFeatureDependenciesComponent : PDPFeatureDependencies

}