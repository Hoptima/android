package id.hoptima.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.hoptima.databinding.ItemCarouselBinding

class CarouselAdapter(private val images: List<Int>) : RecyclerView.Adapter<CarouselViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding =
            ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bindTo(images[position])
    }

    override fun getItemCount() = images.size
}

class CarouselViewHolder(private val binding: ItemCarouselBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(image: Int) {
        binding.imageView.setImageResource(image)
    }
}