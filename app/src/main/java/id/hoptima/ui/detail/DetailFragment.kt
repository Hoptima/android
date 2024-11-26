package id.hoptima.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.hoptima.R
import id.hoptima.databinding.FragmentDetailBinding
import id.hoptima.ui.bookmarks.Property


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val KEY = "property"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val property = arguments?.getParcelable<Property>(KEY)
        property?.let {
            bindPropertyDetails(it)
        }
    }

    private fun bindPropertyDetails(property: Property) {
        binding.apply {
            tvPropertyName.text = property.name
            tvPropertyLocation.text = property.location
            tvPropertyPrice.text = property.price
            description.text = property.description
            tvBedrooms.text = property.jumlahKamar
            tvBathrooms.text = property.jumlahKamarMandi
            tvParking.text = property.jumlahParkir
            tvLandArea.text = property.luasTanah
            tvBuildingArea.text = property.luasBangunan

            Glide.with(this@DetailFragment)
                .load(property.photo)
                .error(R.drawable.ic_error)
                .into(ivPropertyPhoto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}