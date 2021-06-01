package com.orzechowski.prototyp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.prototyp.database.GlobalRoomDatabase
import com.orzechowski.prototyp.emergencynumbers.EmergencyNumbersRecycler

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        GlobalRoomDatabase.getDatabase(applicationContext)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<EmergencyNumbersRecycler>(R.id.phone_number_recycler_main)
            }
            val aidButton = findViewById<Button>(R.id.pomoc_button)
            aidButton.setOnClickListener {
                val tutorial = Intent(this@MainActivity, VersionActivity::class.java)
                startActivity(tutorial)
            }
        }
    }
}