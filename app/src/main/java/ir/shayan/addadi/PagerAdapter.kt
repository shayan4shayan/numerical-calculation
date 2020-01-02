package ir.shayan.addadi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fm: FragmentManager, private val items: List<PagerItem>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int) = items[position].fragment

    override fun getCount() = items.size

    override fun getPageTitle(position: Int) = items[position].name
}

class PagerItem(val fragment: Fragment, val name: String)