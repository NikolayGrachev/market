package ru.grachev.market.ui_feature_products_list_impl.presentation.view_model

import androidx.lifecycle.*
import ru.grachev.market.core_utils.model.NetworkState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.grachev.market.core_utils.presentation.Event
import ru.grachev.market.core_utils.presentation.asEvent
import ru.grachev.market.core_utils.presentation.view.AddToCartButton
import ru.grachev.market.core_utils.presentation.view_model.BaseViewModel
import ru.grachev.market.ui_feature_products_list_impl.domain.interactor.ProductsInListInteractor
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class ProductsViewModel @Inject constructor(
    private val productsInListInteractor: ProductsInListInteractor
    ): BaseViewModel() {
    private val _productLD: MutableLiveData<List<ProductInListVO>> = MutableLiveData()
    val productLD: LiveData<List<ProductInListVO>> = _productLD

    private val _transaction: MutableLiveData<Event<String>> = MutableLiveData()
    val transaction: LiveData<Event<String>> = _transaction

    init {
        observeNetworkState(productsInListInteractor.getNetworkState())
    }

    private fun startWorkerOnNetworkConnected(viewLifecycleOwner: LifecycleOwner) {

        networkState.observe(viewLifecycleOwner) { state ->
            when (state) {
                NetworkState.Connected -> {
                    startProductsInListWorker()
                }
                NetworkState.Disconnected -> {}
            }
        }
    }

    private fun startProductsInListWorker() {
        productsInListInteractor.startProductsInListWorker()
    }

    fun getProductsInList(viewLifecycleOwner: LifecycleOwner) {
        startWorkerOnNetworkConnected(viewLifecycleOwner)
        getProductsInListPeriodically(5.toDuration(DurationUnit.MINUTES), viewLifecycleOwner)
        getProductsInListFromCache(viewLifecycleOwner)
    }

    fun onProductInListClick(product: ProductInListVO) {
        registerPDPVisit(product)
        _transaction.postValue(product.guid.asEvent())
    }

    fun onAddToCartClick(product: ProductInListVO, state: AddToCartButton.State) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            when (state) {
                AddToCartButton.State.AddToCart -> {
                    val updatedProduct = product.copy(isInCart = true)
                    productsInListInteractor.setOrUpdateProduct(updatedProduct)
                }
                AddToCartButton.State.InCart -> {
                    val updatedProduct = product.copy(isInCart = false)
                    productsInListInteractor.setOrUpdateProduct(updatedProduct)
                }
                AddToCartButton.State.Processing -> {}
            }
        }
    }

    private fun registerPDPVisit(productInListVO: ProductInListVO) {
        viewModelScope.launch(Dispatchers.IO) {
            val counter = productInListVO.pdpVisitsCounter + 1
            val updatedProduct = productInListVO.copy(pdpVisitsCounter = counter)
            productsInListInteractor.setOrUpdateProduct(updatedProduct)
        }
    }

    private fun getProductsInListFromCache(viewLifecycleOwner: LifecycleOwner) {
        /**
         * repeatOnLifecycle used to cancel flow when onPause called
         */
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                productsInListInteractor.getProductsInList()
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        updateProductLD(it)
                    }
                    .collect()
            }
        }
    }

    private fun getProductsInListPeriodically(period: Duration, viewLifecycleOwner: LifecycleOwner) {
        /**
         * repeatOnLifecycle used to cancel flow when onPause called
         */
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                productsInListInteractor.getProductsInListPeriodically(period.inWholeMilliseconds)
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        updateProductLD(it)
                    }
                    .collect()
            }
        }
    }

    private fun updateProductLD(products: List<ProductInListVO>) {
        _productLD.value = products
    }
}
