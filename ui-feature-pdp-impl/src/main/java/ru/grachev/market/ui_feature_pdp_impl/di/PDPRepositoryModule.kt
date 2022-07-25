package ru.grachev.market.ui_feature_pdp_impl.di

import dagger.Binds
import dagger.Module
import ru.grachev.market.ui_feature_pdp_impl.data.repository_impl.PDPRepositoryImpl
import ru.grachev.market.ui_feature_pdp_impl.domain.repository.PDPRepository

@Module
interface PDPRepositoryModule {
    @Binds
    fun bindPDPRepository(repository: PDPRepositoryImpl): PDPRepository
}