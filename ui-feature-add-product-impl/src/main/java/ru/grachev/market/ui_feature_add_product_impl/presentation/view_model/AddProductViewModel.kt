package ru.grachev.market.ui_feature_add_product_impl.presentation.view_model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.grachev.market.core_utils.presentation.view_model.BaseViewModel
import ru.grachev.market.ui_feature_add_product_impl.domain.interactor.AddProductInteractor
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductVO
import java.lang.Exception
import javax.inject.Inject

class AddProductViewModel @Inject constructor(
    private val addProductsInteractor: AddProductInteractor): BaseViewModel() {

    private val _productCreated: MutableLiveData<Boolean> = MutableLiveData()
    val productCreated: LiveData<Boolean> = _productCreated

    init {
        observeNetworkState(addProductsInteractor.getNetworkState())
    }

    fun createProduct(name: String?, image: String?, price: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productInListVO = ProductInListVO(
                    guid = generateGuid(),
                    images = listOf(image ?: ""),
                    name = name ?: "",
                    price = (price ?: "0").toInt().toString(),
                    rating = 0.0,
                    isFavorite = false,
                    isInCart = false
                )

                val productVO = ProductVO(
                    guid = productInListVO.guid,
                    images = productInListVO.images,
                    name = productInListVO.name,
                    price = productInListVO.price,
                    rating = productInListVO.rating,
                    isFavorite = productInListVO.isFavorite,
                    isInCart = productInListVO.isInCart,
                    description = "",
                    additionalParams = mapOf()
                )

                addProductsInteractor.setOrUpdateProductInList(productInListVO)
                addProductsInteractor.setOrUpdateProduct(productVO)

                withContext(Dispatchers.Main) {
                    _productCreated.postValue(true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _productCreated.postValue(false)
                }
            }
        }
    }

    private fun generateGuid(): String = "custom-guid-${System.currentTimeMillis()}"
}
