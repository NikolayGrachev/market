package dev.grachev.feature_product_worker.products_worker.worker

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.grachev.core_network_api.NetworkApi
import dev.grachev.core_network_api.ProductsNetworkApi
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProductsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val networkApi: NetworkApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = with(Dispatchers.IO) {
        return  try {
            val products = networkApi.getProductNetworkApi().getProducts()
            val output = workDataOf(KEY_RESPONSE_PRODUCTS to Gson().toJson(products))
            Result.success(output)
        } catch (e: Exception) {
            Result.failure()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): ProductsWorker
    }

    companion object {
        const val KEY_RESPONSE_PRODUCTS = "KEY_RESPONSE_PRODUCTS"
        const val TAG = "productsRequest"

        private val constraints = Constraints.Builder()
            .build()

        val productsRequest = OneTimeWorkRequestBuilder<ProductsWorker>()
            .addTag(TAG)
            .setConstraints(constraints)
            .build()
    }
}