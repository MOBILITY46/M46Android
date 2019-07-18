package se.mobility46.m46androidcard

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import se.mobility46.m46card.Card
import se.mobility46.tabbar.TabAdapter
import se.mobility46.tabbar.TabBar
import se.mobility46.tabbar.TabBar.OnTabBarInteractionListener
import se.mobility46.tabbar.TabBarConfig

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

class MainActivity : AppCompatActivity(), OnTabBarInteractionListener {

    override fun onTabBarCreate(adapter: TabAdapter?) {
        adapter?.add(TabOneFragment.newInstance(), "tab 1")
        adapter?.add(TabTwoFragment.newInstance(), "tab 2")
        adapter?.add(TabThreeFragment.newInstance(), "tab 3")
    }

    private lateinit var card: Card
    private lateinit var tabBar: TabBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        card = findViewById(R.id.card)

        card.setOnClickListener {
            card.flip()
        }

        val config = TabBarConfig(false, R.color.colorPrimary)

        tabBar = TabBar.newInstance(config)

        supportFragmentManager.inTransaction {
            add(R.id.fragment_container, tabBar)
        }

    }

}
