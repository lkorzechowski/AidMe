package com.orzechowski.prototyp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.prototyp.mainrecycler.Recycler


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Recycler>(R.id.layout_recyclera)
            }
            val pomocButton = findViewById<Button>(R.id.pomoc_button)
            pomocButton.setOnClickListener {
                val masazSerca = Intent(this@MainActivity, PoradnikMasazSerca::class.java)
                startActivity(masazSerca)
            }
        }
    }
}