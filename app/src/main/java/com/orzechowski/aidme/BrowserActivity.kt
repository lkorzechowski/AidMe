package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.aidme.browser.BrowserRecycler
import com.orzechowski.aidme.browser.ResultsRecycler
import com.orzechowski.aidme.browser.Search
import com.orzechowski.aidme.tutorial.database.Tutorial

class BrowserActivity : AppCompatActivity(), BrowserRecycler.CallbackToResults,
    ResultsRecycler.CallbackForTutorial
{
    private val mBrowser = BrowserRecycler(this)
    private val mSearch = Search()
    private val mResults = ResultsRecycler(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_browser)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.tutorials_recycler_browser, mBrowser)
        }

        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            val t: FragmentTransaction = supportFragmentManager.beginTransaction()
            t.remove(mBrowser).commit()
            supportFragmentManager.commit {
                add(R.id.search_fragment_layout, mSearch)
            }
        }
    }

    override fun serveResults(tagId: Long)
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mBrowser).commit()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            mResults.arguments = bundleOf(Pair("tagId", tagId))
            add(R.id.tutorials_recycler_browser, mResults)
        }
    }

    override fun serveTutorial(tutorial: Tutorial)
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mResults).commit()
        val intent = Intent(this@BrowserActivity, VersionActivity::class.java)
        intent.putExtra("tutorialId", tutorial.tutorialId)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val fragmentList: List<*> = supportFragmentManager.fragments
        var handled = false
        var currentLevel: Int
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        for (f in fragmentList) {
            if(f is BrowserRecycler) {
                currentLevel = f.level
                t.remove(mBrowser).commit()
                if(currentLevel>0) {
                    handled = true
                }
            }
        }
        if(!handled) super.onBackPressed()
    }
}
