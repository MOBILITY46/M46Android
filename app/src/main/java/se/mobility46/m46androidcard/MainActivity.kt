package se.mobility46.m46androidcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import se.mobility46.m46card.CardFragment

class MainActivity : AppCompatActivity(), CardFragment.OnFragmentInteractionListener {

    val card: CardFragment = CardFragment.newInstance()

    override fun onFlip() {
        println("flipped!")
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, card)
            .commit()

    }

}
