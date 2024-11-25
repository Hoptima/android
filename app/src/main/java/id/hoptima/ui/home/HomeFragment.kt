package id.hoptima.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.hoptima.R
import id.hoptima.databinding.FragmentHomeBinding
import kotlin.math.abs

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        val historyImages = (1..5).map { R.drawable.home_sample }
        binding.rvHistories.apply {
            adapter = HistoryAdapter(historyImages)
        }
    }
}