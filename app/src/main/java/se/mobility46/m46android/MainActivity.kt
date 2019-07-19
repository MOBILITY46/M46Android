package se.mobility46.m46android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.mobility46.m46androidcard.R
import se.mobility46.m46card.Card
import se.mobility46.tabbar.TabBar
import se.mobility46.tabbar.TabBarConfig

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
    beginTransaction().func().commit()

class MainActivity : AppCompatActivity() {

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

        tabBar.withTabs = { adapter ->
            adapter?.add(TabOneFragment.newInstance(), "tab 1")
            adapter?.add(TabTwoFragment.newInstance(), "tab 2")
            adapter?.add(TabThreeFragment.newInstance(), "tab 3")
            adapter?.notifyDataSetChanged()
        }

        supportFragmentManager.inTransaction {
            add(R.id.fragment_container, tabBar)
        }

    }

}
