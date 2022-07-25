package ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.grachev.market.ui_feature_products_list_impl.R
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.ViewTypes
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.diffutil.ImageDiffUtilCallback
import ru.grachev.market.ui_feature_products_list_impl.presentation.view.rv.vh.ImageViewHolder

class ImagesAdapter(
    val images: MutableList<String> = mutableListOf()
): RecyclerView.Adapter<ImageViewHolder>() {

    private var onItemClick: (() -> Unit)? = null

    fun setOnClick(onItemClick: () -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke()
        }
    }

    override fun getItemCount(): Int = images.size

    fun submitList(newImages: List<String>?) {
        if (newImages != null) {
            val diff = ImageDiffUtilCallback(images, newImages)
            val productDiffResult = DiffUtil.calculateDiff(diff)

            images.clear()
            images.addAll(newImages)

            productDiffResult.dispatchUpdatesTo(this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ViewTypes.IMAGE_VIEW_TYPE
    }
}