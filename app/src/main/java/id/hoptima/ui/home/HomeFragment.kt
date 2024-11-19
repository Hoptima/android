package id.hoptima.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.hoptima.R
import id.hoptima.databinding.FragmentHomeBinding
import id.hoptima.viewpager.ImagePagerAdapter
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

        val images = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
        )

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = ImagePagerAdapter(images)
        viewPager.setPageTransformer { page, position ->
            page.alpha = 0.5f + (1 - abs(position)) * 0.5f
            page.scaleY = 0.85f + (1 - abs(position)) * 0.15f
        }


        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

        }.attach()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}