package com.example.cookbook1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ITEM = "item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val item = intent.getStringExtra(EXTRA_ITEM)
        // Wyświetlanie szczegółów elementu w widoku
    }

}
