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
    private lateinit var mBrowser: BrowserRecycler
    private val mSearch = Search()
    private val mResults = ResultsRecycler(this)
    private var returning = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_browser)
        mBrowser = BrowserRecycler(this)
        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            val t: FragmentTransaction = supportFragmentManager.beginTransaction()
            t.remove(mBrowser).commit()
            supportFragmentManager.commit {
                add(R.id.search_fragment_layout, mSearch)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(!returning) commitBrowser()
        else commitResults()
    }

    private fun commitBrowser()
    {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.tutorials_recycler_browser, mBrowser)
        }
    }

    private fun commitResults()
    {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.tutorials_recycler_browser, mResults)
        }
    }

    override fun serveResults(tagId: Long)
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mBrowser).commit()
        mResults.arguments = bundleOf(Pair("tagId", tagId))
        commitResults()
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
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        var handled = false
        for (f in fragmentList) {
            if(f is BrowserRecycler) {
                val currentLevel = f.level
                if(currentLevel!=0) {
                    handled = mBrowser.restorePrevious()
                    if(!handled) {
                        t.remove(mBrowser).commit()
                        mBrowser = BrowserRecycler(this)
                        commitBrowser()
                        handled = true
                    }
                }
            }
            if(f is ResultsRecycler) {
                t.remove(mResults).commit()
                commitBrowser()
                handled = true
            }
        }
        if(!handled) super.onBackPressed()
    }

    override fun onRestart() {
        super.onRestart()
        returning = true
    }
}
