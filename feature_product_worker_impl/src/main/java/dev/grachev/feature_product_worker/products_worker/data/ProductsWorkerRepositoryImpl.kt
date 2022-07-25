package dev.grachev.feature_product_worker.products_worker.data


import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.grachev.core_network_api.models.Product
import dev.grachev.core_network_api.models.ProductInList
import dev.grachev.feature_product_worker.products_worker.domain.mapper.toEntity
import dev.grachev.feature_product_worker.products_worker.worker.ProductsInListWorker
import dev.grachev.feature_product_worker.products_worker.worker.ProductsWorker
import dev.grachev.feature_product_worker_api.WorkerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.grachev.market.core_database_api.DatabaseApi
import ru.grachev.market.core_database_api.entity.ProductEntity
import ru.grachev.market.core_database_api.entity.ProductInListEntity
import javax.inject.Inject

class ProductsWorkerRepositoryImpl @Inject constructor(
    private val workManager: WorkManager,
    private val database: DatabaseApi
) : WorkerApi {
    override fun beginWorkToGetProductsInListAndProducts() {
        val workRequests = listOf(
            ProductsInListWorker.productsListRequest,
            ProductsWorker.productsRequest)

        workManager.beginUniqueWork(
            "Unique products work",
            ExistingWorkPolicy.REPLACE,
            workRequests
        ).enqueue()

        try {
            observeProductsInList()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            observeProducts()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeProductsInList() {
        workManager.getWorkInfosByTagLiveData(ProductsInListWorker.TAG).observeForever { workInfoList ->
            if (workInfoList.isNotEmpty()) {
                val workInfo = workInfoList.first()

                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val data = workInfo.outputData

                    val responseProductsInList = data.getString(ProductsInListWorker.KEY_RESPONSE_PRODUCTS_IN_LIST)

                    if (responseProductsInList != null) {
                        val type = object : TypeToken<List<ProductInList>>() {}.type
                        val products =
                            Gson().fromJson<List<ProductInList>>(responseProductsInList, type)

                        products?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                updateProductsInList(
                                    database.getProductsInListDatabaseApi().getProductsInListOnce(),
                                    it.map { it.toEntity() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateProductsInList(
        dbProducts: List<ProductInListEntity>,
        nwProducts: List<ProductInListEntity>?
    ) {
        val updatedDbProducts = mutableListOf<ProductInListEntity>()
        updatedDbProducts.addAll(dbProducts)

        nwProducts?.forEach { networkProduct ->
            if (dbProducts.find { it.guid ==  networkProduct.guid } == null) {
                updatedDbProducts.add(networkProduct)
            }
        }

        database.getProductsInListDatabaseApi().addProductsInList(updatedDbProducts)
    }

    private fun observeProducts() {
        workManager.getWorkInfosByTagLiveData(ProductsWorker.TAG).observeForever { workInfoList ->
            if (workInfoList.isNotEmpty()) {
                val workInfo = workInfoList.first()

                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val data = workInfo.outputData

                    val response = data.getString(ProductsWorker.KEY_RESPONSE_PRODUCTS)

                    if (response != null) {
                        val type = object : TypeToken<List<Product>>() {}.type
                        val products =
                            Gson().fromJson<List<Product>>(response, type)

                        products?.let {
                            CoroutineScope(Dispatchers.IO).launch {
                                updateProducts(
                                    database.getProductsDatabaseApi().getProductsOnce(),
                                    it.map { it.toEntity() })
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateProducts(
        dbProducts: List<ProductEntity>,
        nwProducts: List<ProductEntity>?
    ) {
        val updatedDbProducts = mutableListOf<ProductEntity>()
        updatedDbProducts.addAll(dbProducts)

        nwProducts?.forEach { networkProduct ->
            if (dbProducts.find { it.guid ==  networkProduct.guid} == null) {
                updatedDbProducts.add(networkProduct)
            }
        }
        database.getProductsDatabaseApi().addProducts(updatedDbProducts)
    }
}