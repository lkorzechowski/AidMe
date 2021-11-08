package com.orzechowski.saveme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.android.volley.toolbox.*
import com.google.android.gms.common.SignInButton
import com.orzechowski.saveme.main.RequestAPI
import com.orzechowski.saveme.main.emergencynumber.EmergencyNumbersRecycler
import com.orzechowski.saveme.volley.ConfigureChannel

//Główna aktywność myląco nie jest pierwszą aktywnością uruchamianą tuż po uruchomieniu aplikacji,
//ale jest jej punktem wyjściowym. Główna aktywność to rozgałęzienie z którego użytkownik może
//przejść do dowolnej aktywności poza StartupActivity. Posiada tylko jeden fragment, którym jest
//Recycler z numerami telefonów alarmowych.
class MainActivity : AppCompatActivity(R.layout.activity_main)
{
    private lateinit var mRequestAPI: RequestAPI

    override fun onCreate(savedInstanceState: Bundle?)
    {
        ConfigureChannel().configureNotificationChannel(this)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        supportFragmentManager.commit {
            add<EmergencyNumbersRecycler>(R.id.phone_number_recycler_main)
        }
        findViewById<Button>(R.id.aid_button_main).setOnClickListener {
            startActivity(Intent(this@MainActivity, BrowserActivity::class.java))
        }
        findViewById<Button>(R.id.settings_button_main).setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
        }
        findViewById<Button>(R.id.sign_in_button_cover).setOnClickListener {
            it.visibility = View.INVISIBLE
        }
        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        signInButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignInActivity::class.java))
        }
    }

    override fun onBackPressed()
    {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

    override fun onResume()
    {
        mRequestAPI = RequestAPI(this).also { it.requestData(cacheDir) }
        super.onResume()
    }

    override fun onDestroy()
    {
        mRequestAPI.end()
        super.onDestroy()
    }
}
