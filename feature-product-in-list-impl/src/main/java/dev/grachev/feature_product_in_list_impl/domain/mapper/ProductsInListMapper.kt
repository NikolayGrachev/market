package dev.grachev.feature_product_in_list_impl.domain.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.grachev.core_network_api.models.ProductInList
import dev.grachev.feature_product_in_list_api.model.ProductInListDTO
import ru.grachev.market.core_database_api.entity.ProductInListEntity


fun ProductInListDTO.toEntity(): ProductInListEntity =
    ProductInListEntity(guid, Gson().toJson(images), name, price, rating, isFavorite, isInCart, pdpVisitsCounter)

fun ProductInListEntity.toDTO(): ProductInListDTO =
    ProductInListDTO(guid,
        Gson().fromJson<List<String>>(
            images,
            object : TypeToken<List<String>>() { }.type),
        name, price, rating, isFavorite, isInCart, pdpVisitsCounter)

fun ProductInList.toDTO(): ProductInListDTO =
    ProductInListDTO(guid, image, name, price, rating, isFavorite, isInCart, pdpVisitsCounter)