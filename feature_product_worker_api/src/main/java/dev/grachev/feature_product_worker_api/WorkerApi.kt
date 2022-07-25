package dev.grachev.feature_product_worker_api

import androidx.lifecycle.LiveData
import dev.grachev.core_network_api.models.ProductInList


interface WorkerApi {
    fun beginWorkToGetProductsInListAndProducts()
}