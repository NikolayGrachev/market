package ru.grachev.market.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.grachev.market.core_navigation_impl.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}