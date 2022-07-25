package ru.grachev.market.ui_feature_products_list_impl.domain.mapper

import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO


fun ProductInListDTO.toVO(): ProductInListVO {
    return ProductInListVO(
        guid = guid,
        images = images,
        name = name,
        price = price,
        rating = rating,
        isInCart = isInCart,
        isFavorite = isFavorite,
        pdpVisitsCounter = pdpVisitsCounter
    )
}

fun ProductInListVO.toDTO(): ProductInListDTO {
    return ProductInListDTO(
        guid = guid,
        images = images,
        name = name,
        price = price,
        rating = rating,
        isInCart = isInCart,
        isFavorite = isFavorite,
        pdpVisitsCounter = pdpVisitsCounter
    )
}