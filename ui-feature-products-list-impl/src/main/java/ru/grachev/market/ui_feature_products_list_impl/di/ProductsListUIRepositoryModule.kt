package ru.grachev.market.ui_feature_products_list_impl.di

import dagger.Binds
import dagger.Module
import ru.grachev.market.ui_feature_products_list_impl.data.repository_impl.ProductInListRepositoryImpl
import ru.grachev.market.ui_feature_products_list_impl.domain.repository.ProductInListRepository


@Module
interface ProductsListUIRepositoryModule {
    @Binds
    fun bindPDPRepository(repository: ProductInListRepositoryImpl): ProductInListRepository
}