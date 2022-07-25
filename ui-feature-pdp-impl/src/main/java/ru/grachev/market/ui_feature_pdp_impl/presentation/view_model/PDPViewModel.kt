package ru.grachev.market.ui_feature_pdp_impl.presentation.view_model

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.grachev.market.core_utils.presentation.view_model.BaseViewModel
import ru.grachev.market.ui_feature_pdp_impl.domain.interactor.PDPInteractor
import ru.grachev.market.ui_feature_pdp_impl.domain.model.ProductVO
import javax.inject.Inject

class PDPViewModel @Inject constructor(private val interactor: PDPInteractor): BaseViewModel() {
    private val _productPDP: MutableLiveData<ProductVO> = MutableLiveData()
    val productPDP: LiveData<ProductVO> = _productPDP

    init {
        observeNetworkState(interactor.getNetworkState())
    }

    fun getProductById(id: String, viewLifecycleOwner: LifecycleOwner) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                interactor.getProductById(id)
                    .flowOn(Dispatchers.IO)
                    .onEach {
                        _productPDP.value = it
                    }
                    .collect()
            }
        }
    }

    fun updateProduct(quantity: Int) {
        val isInCart = quantity > 0
        productPDP.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                interactor.setOrUpdateProduct(it.copy(count = quantity, isInCart = isInCart))
            }
        }
    }
}