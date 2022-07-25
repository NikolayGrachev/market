package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv

import android.content.Context
import ru.grachev.market.ui_feature_products_list_impl.R
import ru.grachev.market.ui_feature_products_list_impl.domain.model.ProductInListVO
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.BaseRvModel
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.ProductListRvModel
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.model.TextRvModel

object ListGenerator {
    fun generateListPrice100(context: Context, list: List<ProductInListVO>): List<BaseRvModel> {
        val header1 = TextRvModel(context.getString(R.string.less_100_rub))

        val list1 = list
            .filter { it.price.toDouble() < 100 }
            .sortedBy { it.name }
            .map { ProductListRvModel(it) }

        val header2 = TextRvModel(context.getString(R.string.more_100_rub))

        val list2 = list
            .filter { it.price.toDouble() >= 100 }
            .sortedBy { it.name }
            .map { ProductListRvModel(it) }

        val resultList = mutableListOf<BaseRvModel>()
        resultList.add(header1)
        resultList.addAll(list1)
        resultList.add(header2)
        resultList.addAll(list2)

        return resultList
    }
}