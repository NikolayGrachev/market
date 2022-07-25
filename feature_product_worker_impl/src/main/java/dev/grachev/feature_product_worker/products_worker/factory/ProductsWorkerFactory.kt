package dev.grachev.feature_product_worker.products_worker.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dev.grachev.feature_product_worker.products_worker.worker.ProductsInListWorker
import dev.grachev.feature_product_worker.products_worker.worker.ProductsWorker
import javax.inject.Inject

class ProductsWorkerFactory @Inject constructor(
    private val productsInListWorker: ProductsInListWorker.Factory,
    private val productsWorker: ProductsWorker.Factory
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        return when (workerClassName) {
            ProductsInListWorker::class.java.name ->
                productsInListWorker.create(appContext, workerParameters)

            ProductsWorker::class.java.name ->
                productsWorker.create(appContext, workerParameters)
            else -> null
        }
    }
}