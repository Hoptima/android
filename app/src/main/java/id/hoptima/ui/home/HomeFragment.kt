package id.hoptima.ui.home

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.google.android.material.tabs.TabLayoutMediator
import id.hoptima.R
import id.hoptima.databinding.FragmentHomeBinding
import id.hoptima.ui.common.BaseFragment
import id.hoptima.ui.common.CompactPropertyAdapter
import kotlin.math.abs

class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = TransitionInflater.from(context).inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        postponeEnterTransition()

        binding.apply {
            btnStartChat.setOnClickListener {
                navController.navigate(R.id.action_home_to_chat)
            }

            btnToHistories.setOnClickListener {
                navController.navigate(R.id.action_home_to_histories)
            }
        }

        val carouselImages = listOf(
            R.drawable.carousel_image_1,
            R.drawable.carousel_image_2,
            R.drawable.carousel_image_3,
        )

        val viewPager = binding.vpCarousel.apply {
            adapter = CarouselAdapter(carouselImages)
            setPageTransformer { page, position ->
                page.alpha = 0.5f + (1 - abs(position)) * 0.5f
                page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
            }
        }

        val tabLayout = binding.tlCarouselIndicator
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        val propertyAdapter = CompactPropertyAdapter(R.id.action_home_to_detail) {
            viewModel.setPropertyBookmark(it, it.bookmarkedAt == null)
        }
        binding.rvHistories.adapter = propertyAdapter

        viewModel.getRecentProperties().observe(viewLifecycleOwner) {
            propertyAdapter.submitList(it)
            binding.svNoData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

            (view?.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }
}