package dev.grachev.feature_product_impl.domain.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.grachev.core_network_api.models.Product
import dev.grachev.feature_product_api.model.ProductDTO
import ru.grachev.market.core_database_api.entity.ProductEntity


fun ProductDTO.toEntity(): ProductEntity =
    ProductEntity(guid, name, price, description, rating, isFavorite, isInCart, Gson().toJson(images),
        weight, count, availableCount, Gson().toJson(additionalParams))

fun ProductEntity.toDTO(): ProductDTO =
    ProductDTO(guid, name, price, description, rating, isFavorite, isInCart,
        images = Gson().fromJson<List<String>>(
            images,
            object : TypeToken<List<String>>() { }.type
        ),
        weight, count, availableCount,
        additionalParams = if (additionalParams.isNotEmpty()) {
            Gson().fromJson<Map<String, String>>(
                additionalParams,
                object : TypeToken<Map<String, String>>() { }.type
            )
        } else {
            mapOf()
        }
    )

fun Product.toDTO(): ProductDTO =
    ProductDTO(guid, name, price, description, rating, isFavorite, isInCart, images,
        weight, count, availableCount, additionalParams)