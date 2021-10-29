package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.android.volley.toolbox.*
import com.google.android.gms.common.SignInButton
import com.orzechowski.aidme.database.GlobalRoomDatabase
import com.orzechowski.aidme.main.EmergencyNumbersRecycler
import com.orzechowski.aidme.main.RequestAPI

class MainActivity : AppCompatActivity(R.layout.activity_main)
{
    private lateinit var requestAPI: RequestAPI

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
            startActivity(Intent(this@MainActivity, BrowserActivity::class.java))
        }
        val settingsButton = findViewById<Button>(R.id.settings_button_main)
        settingsButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }
        val signInCover = findViewById<Button>(R.id.sign_in_button_cover)
        signInCover.setOnClickListener {
            signInCover.visibility = View.INVISIBLE
        }
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        signInButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignInActivity::class.java))
        }
    }

    override fun onResume()
    {
        requestAPI = RequestAPI(this).also { it.requestData(cacheDir) }
        super.onResume()
    }

    override fun onDestroy()
    {
        requestAPI.end()
        super.onDestroy()
    }
}
