package ru.grachev.market.ui_feature_add_product_impl.di

import dagger.Binds
import dagger.Module
import ru.grachev.market.ui_feature_add_product_impl.domain.interactor.AddProductInteractor
import ru.grachev.market.ui_feature_add_product_impl.domain.interactor.AddProductInteractorImpl

@Module
interface AddProductUIInteractorModule {
    @Binds
    fun bindProductsInteractor(interactor: AddProductInteractorImpl): AddProductInteractor
}