package dev.grachev.feature_product_worker.products_worker.domain

import dev.grachev.feature_product_worker_api.WorkerApi

interface WorkerRepository {
    fun getWorkerApi(): WorkerApi
}