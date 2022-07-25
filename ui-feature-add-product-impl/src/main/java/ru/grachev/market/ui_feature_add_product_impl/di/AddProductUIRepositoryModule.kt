package ru.grachev.market.ui_feature_add_product_impl.di

import dagger.Binds
import dagger.Module
import ru.grachev.market.ui_feature_add_product_impl.data.repository_impl.AddProductRepositoryImpl
import ru.grachev.market.ui_feature_add_product_impl.domain.repository.AddProductRepository


@Module
interface AddProductUIRepositoryModule {
    @Binds
    fun bindPDPRepository(repository: AddProductRepositoryImpl): AddProductRepository
}