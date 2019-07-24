package se.mobility46.tabbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(
    fm: FragmentManager, private val tabNames: ArrayList<String>,
    private val fragments: ArrayList<Fragment>
) : FragmentPagerAdapter(fm) {

    fun add(fragment: Fragment, title: String) {
        tabNames.add(title)
        fragments.add(fragment)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabNames[position]
    }
}