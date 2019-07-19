package se.mobility46.tabbar

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private const val CONFIG = "config"

class TabBar : Fragment() {
    private var listener: Listener? = null
    private var adapter: TabAdapter? = null
    private var config: TabBarConfig? = null

    private lateinit var viewPager: TabPager
    private lateinit var tabs: TabLayout

    var withTabs: ((adapter: TabAdapter?) -> Unit)? = null

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
        }

        viewPager = v.findViewById(R.id.view_pager)

        viewPager.adapter = adapter
        tabs = v.findViewById(R.id.tabs)

        tabs.setupWithViewPager(viewPager)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         config?.let {
            configure(it)
        }
    }



    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun configure(config: TabBarConfig) {
        viewPager.isPagingEnabled = config.swipeable
        tabs.setSelectedTabIndicatorColor(config.indicatorColor)
        withTabs?.invoke(adapter)
    }

    interface Listener {
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
