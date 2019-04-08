package se.mobility46.m46androidcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import se.mobility46.m46card.Card

class MainActivity : AppCompatActivity() {

    private lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        card = findViewById(R.id.card)

        card.setOnClickListener {
            card.flip()
        }
    }

}
