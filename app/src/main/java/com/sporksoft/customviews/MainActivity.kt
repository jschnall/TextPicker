package com.sporksoft.customviews

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var picker: TextPicker
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                picker.setItems(listOf("Sphinx cat", "Honey badger", "Orangutan", "Burmese python"))
                Toast.makeText(this, "${picker.index}: ${picker.value}", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                picker.setItems(listOf("Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"))
                picker.index = 1
                Toast.makeText(this, "${picker.index}: ${picker.value}", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                picker.setItems(listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
                picker.index = 10
                Toast.makeText(this, "${picker.index}: ${picker.value}", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        picker = findViewById<TextPicker>(R.id.picker)
        picker.divider = getDrawable(R.drawable.divider)
        picker.addOnValueChangeListener(object : TextPicker.OnValueChangeListener {
            override fun onValueChange(textPicker: TextPicker, value: String, index: Int) {
                Toast.makeText(this@MainActivity, "$index: $value", Toast.LENGTH_SHORT).show()
            }
        })

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
