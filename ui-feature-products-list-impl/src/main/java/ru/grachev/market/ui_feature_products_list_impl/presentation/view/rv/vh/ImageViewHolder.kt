package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.image_item.view.*

class ImageViewHolder(
    override val containerView: View
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(imageUrl: String) {
        containerView.apply {
            Glide.with(containerView.context)
                .load(imageUrl)
                .fitCenter()
                .into(image)
        }
    }
}