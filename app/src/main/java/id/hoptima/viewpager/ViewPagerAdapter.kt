package id.hoptima.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> PageFragment1()
            1 -> PageFragment2()
            2 -> PageFragment3()
            3 -> PageFragment4()
            else -> PageFragment5()
        }
    }
}