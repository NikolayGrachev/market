package ru.grachev.market.ui_feature_add_product_impl.domain.mapper

import dev.grachev.feature_product_api.model.ProductDTO
import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_add_product_impl.domain.model.ProductVO


fun ProductInListVO.toDTO(): ProductInListDTO =
    ProductInListDTO(guid, images, name, price, rating, isFavorite, isInCart, pdpVisitsCounter)

fun ProductVO.toDTO(): ProductDTO =
    ProductDTO(guid, name, price, description, rating, isFavorite, isInCart,
        images,
        weight,
        count,
        availableCount,
        additionalParams)

fun ProductInListDTO.toVO() : ProductInListVO =
    ProductInListVO(guid, images, name, price, rating, isFavorite, isInCart, pdpVisitsCounter)
