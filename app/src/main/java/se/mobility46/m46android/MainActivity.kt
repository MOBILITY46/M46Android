package se.mobility46.m46android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import se.mobility46.m46card.Card
import se.mobility46.tabbar.TabBar
import se.mobility46.tabbar.TabBarConfig

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
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

        tabBar.tabs = mapOf(
            "tab 1" to TabOneFragment.newInstance(),
            "tab 2" to TabTwoFragment.newInstance(),
            "tab 3" to TabThreeFragment.newInstance()
            )

        supportFragmentManager.inTransaction {
            add(R.id.fragment_container, tabBar)
        }

    }

}
