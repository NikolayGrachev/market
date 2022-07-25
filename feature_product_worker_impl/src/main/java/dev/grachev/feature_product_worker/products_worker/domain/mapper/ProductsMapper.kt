package dev.grachev.feature_product_worker.products_worker.domain.mapper

import com.google.gson.Gson
import dev.grachev.core_network_api.models.Product
import dev.grachev.core_network_api.models.ProductInList
import ru.grachev.market.core_database_api.entity.ProductEntity
import ru.grachev.market.core_database_api.entity.ProductInListEntity


fun Product.toEntity(): ProductEntity =
    ProductEntity(guid, name, price, description, rating, isFavorite, isInCart, Gson().toJson(images),
        weight, count, availableCount, Gson().toJson(additionalParams))

fun ProductInList.toEntity(): ProductInListEntity =
    ProductInListEntity(guid, Gson().toJson(image), name, price, rating, isFavorite, isInCart, pdpVisitsCounter)