package ru.grachev.market.ui_feature_products_list_impl.products_worker.worker

/*
import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.grachev.feature_product_api.ProductsApi
import kotlinx.coroutines.Dispatchers

class ProductsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters,
    private val productsApi: ProductsApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = with(Dispatchers.IO) {
            val products = productsApi.getProducts()
            val output = workDataOf(KEY_RESPONSE_PRODUCTS to Gson().toJson(products))
            return Result.success(output)
    }

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): ProductsWorker
    }

    companion object {
        const val KEY_RESPONSE_PRODUCTS = "KEY_RESPONSE_PRODUCTS"
    }
}*/
