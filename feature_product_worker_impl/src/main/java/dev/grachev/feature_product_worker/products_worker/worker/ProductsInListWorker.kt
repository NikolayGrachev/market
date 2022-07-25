package dev.grachev.feature_product_worker.products_worker.worker

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.grachev.core_network_api.NetworkApi
import dev.grachev.core_network_api.ProductsInListNetworkApi
import dev.grachev.core_network_api.ProductsNetworkApi
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProductsInListWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val networkApi: NetworkApi
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = with(Dispatchers.IO) {
        return  try {
            val products = networkApi.getProductInListNetworkApi().getProductsInList()
            val output = workDataOf(KEY_RESPONSE_PRODUCTS_IN_LIST to Gson().toJson(products))
            Result.success(output)
        } catch (e: Exception) {
            Result.failure()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): ProductsInListWorker
    }

    companion object {
        const val KEY_RESPONSE_PRODUCTS_IN_LIST = "KEY_RESPONSE_PRODUCTS_IN_LIST"
        const val TAG = "productsListRequest"

        private val constraints = Constraints.Builder()
            .build()

        val productsListRequest = OneTimeWorkRequestBuilder<ProductsInListWorker>()
            .addTag(TAG)
            .setConstraints(constraints)
            .build()
    }
}