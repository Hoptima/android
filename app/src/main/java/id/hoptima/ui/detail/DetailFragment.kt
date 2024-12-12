package id.hoptima.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import id.hoptima.R
import id.hoptima.data.local.entity.PropertyEntity
import id.hoptima.databinding.FragmentDetailBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.util.NumberUtil


class DetailFragment : BaseFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = TransitionInflater.from(context)
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)
        sharedElementEnterTransition = inflater.inflateTransition(R.transition.shared_elements)
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
        initView()
    }

    override fun onResume() {
        super.onResume()
        toggleNavViewVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        toggleNavViewVisibility(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        postponeEnterTransition()
        val propertyId = requireArguments().getInt(EXTRA_ID)

        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel.getPropertyById(propertyId).observe(viewLifecycleOwner) {
            if (it == null) {
                navController.navigate(R.id.action_detail_to_home)
                return@observe
            }

            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
            bindProperty(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindProperty(property: PropertyEntity) {
        binding.apply {
            btnBookmark.transitionName = "bookmark_${property.id}"
            ivPhoto.transitionName = "photo_${property.id}"
            tvName.transitionName = "name_${property.id}"
            tvPrice.transitionName = "price_${property.id}"
            tvLocation.transitionName = "location_${property.id}"
            trBedrooms.transitionName = "bedrooms_${property.id}"
            trToilets.transitionName = "toilets_${property.id}"
            trGarages.transitionName = "garages_${property.id}"
            trBuildingArea.transitionName = "building_area_${property.id}"
            trLandArea.transitionName = "land_area_${property.id}"

            tvName.text = property.name
            tvPrice.text = NumberUtil.formatRupiah(property.price)
            tvLocation.text = property.location
            tvBedrooms.text = property.bedrooms.toString()
            tvToilets.text = property.toilets.toString()
            tvGarages.text = property.garages.toString()
            tvLandArea.text = property.landArea.toString() + " m²"
            tvBuildingArea.text = property.buildingArea.toString() + " m²"
            tvDescription.text = property.description

            Glide.with(requireContext())
                .load(property.imageUrl)
                .placeholder(R.drawable.outline_image_24)
                .into(ivPhoto)

            btnBookmark.apply {
                val icon =
                    if (property.bookmarkedAt != null) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
                setImageResource(icon)
                setOnClickListener {
                    viewModel.setPropertyBookmark(property, property.bookmarkedAt == null)
                }
            }

            btnOpenLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(property.url))
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}