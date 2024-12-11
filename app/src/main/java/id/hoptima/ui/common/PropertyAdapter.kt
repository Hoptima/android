package id.hoptima.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import id.hoptima.R
import id.hoptima.data.local.entity.PropertyEntity
import id.hoptima.databinding.ItemPropertyBinding
import id.hoptima.ui.detail.DetailFragment
import id.hoptima.util.NumberUtil

class PropertyAdapter(
    private val navActionId: Int,
    private val onBookmarkCLick: (property: PropertyEntity) -> Unit
) :
    PagingDataAdapter<PropertyEntity, PropertyAdapter.PropertyViewHolder>(PropertyDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding =
            ItemPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bindTo(it) }
    }

    inner class PropertyViewHolder(private val binding: ItemPropertyBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindTo(property: PropertyEntity) {
            binding.apply {
                tvName.text = property.name
                tvPrice.text = NumberUtil.formatRupiah(property.price)
                tvLocation.text = property.location
                tvBedroomCount.text = property.bedrooms.toString()
                tvToiletCount.text = property.toilets.toString()
                tvGarageCount.text = property.garages.toString()
                tvBuildingArea.text = property.buildingArea.toString() + " m²"
                tvLandArea.text = property.landArea.toString() + " m²"

                Glide.with(itemView.context)
                    .load(property.imageUrl)
                    .placeholder(R.drawable.outline_image_24)
                    .into(ivPhoto)

                btnBookmark.apply {
                    val icon =
                        if (property.bookmarkedAt != null) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
                    setImageResource(icon)
                    setOnClickListener { onBookmarkCLick(property) }
                }

                root.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt(DetailFragment.EXTRA_ID, property.id!!)
                    }
                    it.findNavController().navigate(navActionId, bundle)
                }
            }
        }
    }
}

