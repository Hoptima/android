package id.hoptima.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
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
                ivPhoto.transitionName = "photo_${property.id}"
                tvName.transitionName = "name_${property.id}"
                tvPrice.transitionName = "price_${property.id}"
                tvLocation.transitionName = "location_${property.id}"
                tvBedroomCount.transitionName = "bedrooms_${property.id}"
                tvToiletCount.transitionName = "toilets_${property.id}"
                tvGarageCount.transitionName = "garages_${property.id}"
                tvBuildingArea.transitionName = "building_area_${property.id}"
                tvLandArea.transitionName = "land_area_${property.id}"

                btnBookmark.apply {
                    transitionName = "bookmark_${property.id}"
                    val icon =
                        if (property.bookmarkedAt != null) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
                    setImageResource(icon)
                    setOnClickListener { onBookmarkCLick(property) }
                }

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

                root.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt(DetailFragment.EXTRA_ID, property.id!!)
                    }
                    val extras = FragmentNavigatorExtras(
                        ivPhoto to "photo_${property.id}",
                        tvName to "name_${property.id}",
                        tvPrice to "price_${property.id}",
                        tvLocation to "location_${property.id}",
                        tvBedroomCount to "bedrooms_${property.id}",
                        tvToiletCount to "toilets_${property.id}",
                        tvGarageCount to "garages_${property.id}",
                        tvBuildingArea to "building_area_${property.id}",
                        tvLandArea to "land_area_${property.id}"
                    )

                    it.findNavController().navigate(navActionId, bundle, null, extras)
                }
            }
        }
    }
}

