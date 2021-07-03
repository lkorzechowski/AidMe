package com.orzechowski.aidme

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.aidme.browser.BrowserRecycler

class BrowserActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_browser)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<BrowserRecycler>(R.id.tutorials_recycler_browser)
        }

        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener{}
    }
}