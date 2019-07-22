package se.mobility46.tabbar

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val CONFIG = "config"

class TabBar : Fragment() {
    private var config: TabBarConfig? = null

    private lateinit var viewPager: TabPager
    private lateinit var layout: TabLayout
    private lateinit var adapter: TabAdapter

    var tabs: Map<String, Fragment> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            config = it.getParcelable(CONFIG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_tab_bar, container, false)
        fragmentManager?.let {
            adapter = TabAdapter(it)
            tabs.forEach { tab ->
                adapter.add(tab.value, tab.key)
            }

            viewPager = v.findViewById(R.id.view_pager)

            viewPager.adapter = adapter
            layout = v.findViewById(R.id.tabs)

            layout.setupWithViewPager(viewPager)
        }

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        config?.let {
            configure(it)
        }
    }

    private fun configure(config: TabBarConfig) {
        viewPager.isPagingEnabled = config.swipeable
        layout.setSelectedTabIndicatorColor(config.indicatorColor)
    }

    companion object {
        @JvmStatic
        fun newInstance(config: TabBarConfig) = TabBar().apply {
            arguments = Bundle().apply {
                putParcelable(CONFIG, config)
            }
        }
    }
}
