package com.orzechowski.aidme

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.aidme.settings.Policy
import com.orzechowski.aidme.settings.helper.HelperRecycler


class SettingsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val policyButton = findViewById<Button>(R.id.policy_button)
        val contributorButton = findViewById<Button>(R.id.contributor_button)
        val downloadAllTutorialsButton = findViewById<Button>(R.id.download_all_tutorials)
        val contactButton = findViewById<Button>(R.id.contact_button)
        val parentLayout = findViewById<ConstraintLayout>(R.id.settings_parent_layout)

        policyButton.setOnClickListener {
            parentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Policy>(R.id.policy_layout)
            }
        }


        contributorButton.setOnClickListener {
            parentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HelperRecycler>(R.id.helper_layout)
            }
        }


        downloadAllTutorialsButton.setOnClickListener {

        }

        contactButton.setOnClickListener {

        }
    }
}