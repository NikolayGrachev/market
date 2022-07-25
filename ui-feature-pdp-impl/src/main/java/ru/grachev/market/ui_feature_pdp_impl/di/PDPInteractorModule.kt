package ru.grachev.market.ui_feature_pdp_impl.di

import dagger.Binds
import dagger.Module
import ru.grachev.market.ui_feature_pdp_impl.domain.interactor.PDPInteractor
import ru.grachev.market.ui_feature_pdp_impl.domain.interactor.PDPInteractorImpl

@Module
interface PDPInteractorModule {
    @Binds
    fun bindProductsInteractor(interactor: PDPInteractorImpl): PDPInteractor
}