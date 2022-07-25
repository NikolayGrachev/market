package ru.grachev.market.ui_feature_products_list_impl.di

import dagger.Binds
import dagger.Module
import ru.grachev.market.ui_feature_products_list_impl.domain.interactor.ProductsInListInteractor
import ru.grachev.market.ui_feature_products_list_impl.domain.interactor.ProductsInListInteractorImpl

@Module
interface ProductsListUIInteractorModule {
    @Binds
    fun bindProductsInteractor(interactor: ProductsInListInteractorImpl): ProductsInListInteractor
}