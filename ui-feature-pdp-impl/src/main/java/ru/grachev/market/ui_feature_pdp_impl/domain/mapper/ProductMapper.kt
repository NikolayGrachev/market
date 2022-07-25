package ru.grachev.market.ui_feature_pdp_impl.domain.mapper

import dev.grachev.feature_product_api.model.ProductDTO
import ru.grachev.market.ui_feature_pdp_impl.domain.model.ProductVO


fun ProductDTO.toVO(): ProductVO {
    return ProductVO(
        guid = guid,
        images = images,
        name = name,
        price = price,
        rating = rating,
        description = description,
        additionalParams = additionalParams,
        isInCart = isInCart,
        isFavorite = isFavorite,
        count = count
    )
}

fun ProductVO.toDTO(): ProductDTO {
    return ProductDTO(
        guid = guid,
        images = images,
        name = name,
        price = price,
        rating = rating,
        description = description,
        additionalParams = additionalParams,
        isInCart = isInCart,
        isFavorite = isFavorite,
        count = count,
        availableCount = availableCount,
        weight = weight
    )
}


