package com.orzechowski.aidme

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.aidme.settings.Policy
import com.orzechowski.aidme.settings.helper.HelperRecycler


class SettingsActivity : AppCompatActivity()
{
    val mPolicy: Policy = Policy()
    val mHelpers: HelperRecycler = HelperRecycler()
    lateinit var mParentLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val policyButton = findViewById<Button>(R.id.policy_button)
        val contributorButton = findViewById<Button>(R.id.contributor_button)
        val downloadAllTutorialsButton = findViewById<Button>(R.id.download_all_tutorials)
        val contactButton = findViewById<Button>(R.id.contact_button)
        mParentLayout = findViewById(R.id.settings_parent_layout)

        policyButton.setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.policy_layout, mPolicy)
            }
        }

        contributorButton.setOnClickListener {
            mParentLayout.visibility = View.INVISIBLE
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.helper_layout, mHelpers)
            }
        }


        downloadAllTutorialsButton.setOnClickListener {

        }

        contactButton.setOnClickListener {

        }
    }

    override fun onBackPressed() {
        mParentLayout.visibility = View.VISIBLE
        val fragmentList: List<*> = supportFragmentManager.fragments
        var handled = false
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (f in fragmentList) {
            if (f is Policy) {
                handled = f.onBackPressed()
                t.remove(mPolicy).commit()
                if(handled) break
            }
            else if (f is HelperRecycler) {
                handled = f.onBackPressed()
                t.remove(mHelpers).commit()
                if(handled) break
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }
}