package com.orzechowski.saveme

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.saveme.browser.categories.CategoryRecycler
import com.orzechowski.saveme.browser.results.ResultsRecycler
import com.orzechowski.saveme.browser.search.Search
import com.orzechowski.saveme.tutorial.database.Tutorial

//Aktywność w której zamieszczono fragmenty przeszukiwarek kategorii i wyników, oraz fragment
//wyszukiwarki. Klasy podlegające tej aktywności znajdują się w com.orzechowski.saveme.browser.
class BrowserActivity : AppCompatActivity(R.layout.activity_browser),
    CategoryRecycler.CallbackToResults, ResultsRecycler.CallbackForTutorial,
    Search.CallbackForTutorial
{
    private lateinit var mCategory: CategoryRecycler
    private val mSearch = Search(this)
    private val mResults = ResultsRecycler(this)
    private var returning = false
    private lateinit var mSearchButton: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mCategory = CategoryRecycler(this)
        mSearchButton = findViewById(R.id.search_button)
        mSearchButton.setOnClickListener {
            mSearchButton.visibility = View.GONE
            val t: FragmentTransaction = supportFragmentManager.beginTransaction()
            t.remove(mCategory).commit()
            supportFragmentManager.commit {
                add(R.id.search_fragment_layout, mSearch)
            }
        }
    }

    override fun onStart()
    {
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

    override fun onBackPressed()
    {
        val fragmentList: List<*> = supportFragmentManager.fragments
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        var handled = false
        for (f in fragmentList) {
            when(f) {
                is CategoryRecycler -> {
                    val currentLevel = f.level
                    if (currentLevel>1) {
                        mCategory.restorePrevious()
                        handled = true
                    } else if (currentLevel==1) {
                        t.remove(mCategory).commit()
                        mCategory = CategoryRecycler(this)
                        commitBrowser()
                        handled = true
                    }
                }
                is ResultsRecycler -> {
                    t.remove(mResults).commit()
                    commitBrowser()
                    handled = true
                }
                is Search -> {
                    mSearchButton.visibility = View.VISIBLE
                    t.remove(mSearch).commit()
                    commitBrowser()
                    handled = true
                }
            }
        }
        if(!handled) {
            startActivity(Intent(this@BrowserActivity, MainActivity::class.java))
        }
    }

    override fun onRestart()
    {
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