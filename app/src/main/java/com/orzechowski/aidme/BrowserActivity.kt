package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.aidme.browser.categories.CategoryRecycler
import com.orzechowski.aidme.browser.results.ResultsRecycler
import com.orzechowski.aidme.browser.search.Search
import com.orzechowski.aidme.tutorial.database.Tutorial

class BrowserActivity : AppCompatActivity(), CategoryRecycler.CallbackToResults,
    ResultsRecycler.CallbackForTutorial, Search.CallbackForTutorial
{
    private lateinit var mCategory: CategoryRecycler
    private val mSearch = Search(this)
    private val mResults = ResultsRecycler(this)
    private var returning = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_browser)
        mCategory = CategoryRecycler(this)
        val searchButton: Button = findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            searchButton.visibility = View.GONE
            val t: FragmentTransaction = supportFragmentManager.beginTransaction()
            t.remove(mCategory).commit()
            supportFragmentManager.commit {
                add(R.id.search_fragment_layout, mSearch)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(!returning) {
            commitBrowser()
        }
        else {
            commitResults()
            returning = false
        }
    }

    private fun commitBrowser()
    {
        supportFragmentManager.commit {
            add(R.id.tutorials_recycler_browser, mCategory)
        }
    }

    private fun commitResults()
    {
        supportFragmentManager.commit {
            add(R.id.tutorials_recycler_browser, mResults)
        }
    }

    override fun serveResults(tagId: Long)
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mCategory).commit()
        mResults.arguments = bundleOf(Pair("tagId", tagId))
        commitResults()
    }

    override fun onBackPressed() {
        val fragmentList: List<*> = supportFragmentManager.fragments
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        var handled = false
        for (f in fragmentList) {
            if(f is CategoryRecycler) {
                val currentLevel = f.level
                if(currentLevel>1) {
                    mCategory.restorePrevious()
                    handled = true
                } else if(currentLevel==1) {
                    t.remove(mCategory).commit()
                    mCategory = CategoryRecycler(this)
                    commitBrowser()
                    handled = true
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

    override fun serveTutorial(tutorial: Tutorial)
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mResults).commit()
        startTutorial(tutorial.tutorialId)
    }

    override fun serveSearchedTutorial(tutorial: Tutorial)
    {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mSearch).commit()
        startTutorial(tutorial.tutorialId)
    }

    private fun startTutorial(tutorialId: Long)
    {
        val intent = Intent(this@BrowserActivity, VersionActivity::class.java)
        intent.putExtra("tutorialId", tutorialId)
        startActivity(intent)
    }
}
