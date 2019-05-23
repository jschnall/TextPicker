package com.example.customviews

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var picker: TextPicker
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                picker.setItems(listOf("Cat", "Dog"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                picker.setItems(listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                picker.setItems(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        picker = findViewById(R.id.picker)
        picker.divider = getDrawable(R.drawable.divider)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
