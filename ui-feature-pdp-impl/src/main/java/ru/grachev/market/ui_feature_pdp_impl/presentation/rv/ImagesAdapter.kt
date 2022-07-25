package ru.grachev.market.ui_feature_pdp_impl.presentation.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.grachev.market.ui_feature_pdp_impl.R

class ImagesAdapter(
    val images: MutableList<String> = mutableListOf()): RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pdp_image_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.imageViewPDP?.let {
            Glide.with(holder.itemView.context)
                .load(images[position])
                .centerCrop()
                .into(it)
        }
    }

    override fun getItemCount(): Int = images.size

    fun submitList(newImages: List<String>?) {
        newImages?.let {
            images.clear()
            images.addAll(it)
            notifyDataSetChanged()
        }

    }

    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageViewPDP: ImageView = view.findViewById<ImageView>(R.id.imageViewPDP)
    }
}