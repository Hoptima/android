package id.hoptima.ui.bookmarks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.hoptima.databinding.ItemBookmarkBinding

data class Property(
    val name: String,
    val location: String,
    val price: String,
    val description: String,
    val photo: Int,
)

class BookmarkAdapter : ListAdapter<Property, BookmarkViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding =
            ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val story = getItem(position)
        holder.bindTo(story)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Property>() {
            override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class BookmarkViewHolder(private val binding: ItemBookmarkBinding) : ViewHolder(binding.root) {
    fun bindTo(property: Property) {
        binding.apply {
            tvPropertyName.text = property.name
            tvPropertyLocation.text = property.location
            tvPropertyPrice.text = property.price
            tvPropertyDescription.text = property.description
            ivPropertyPhoto.setImageResource(property.photo)

            root.setOnClickListener {
                // Handle click event
            }
        }
    }
}