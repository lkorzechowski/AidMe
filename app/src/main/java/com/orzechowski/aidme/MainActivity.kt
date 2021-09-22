package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.gms.common.SignInButton
import com.orzechowski.aidme.database.GlobalRoomDatabase
import com.orzechowski.aidme.emergencynumbers.EmergencyNumbersRecycler

class MainActivity : AppCompatActivity(R.layout.activity_main)
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        GlobalRoomDatabase.getDatabase(applicationContext)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<EmergencyNumbersRecycler>(R.id.phone_number_recycler_main)
        }
        val aidButton = findViewById<Button>(R.id.aid_button_main)
        aidButton.setOnClickListener {
            val browser = Intent(this@MainActivity, BrowserActivity::class.java)
            startActivity(browser)
        }
        val settingsButton = findViewById<Button>(R.id.settings_button_main)
        settingsButton.setOnClickListener {
            val settings = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(settings)
        }
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        signInButton.setOnClickListener {
            val signIn = Intent(this@MainActivity, SignInActivity::class.java)
            startActivity(signIn)
        }
    }
}
