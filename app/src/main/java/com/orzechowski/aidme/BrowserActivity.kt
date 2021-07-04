package com.orzechowski.aidme

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.aidme.browser.BrowserRecycler
import com.orzechowski.aidme.browser.ResultsRecycler
import com.orzechowski.aidme.browser.Search

class BrowserActivity : AppCompatActivity(), BrowserRecycler.CallbackToResults
{
    private val mBrowser: BrowserRecycler = BrowserRecycler(this)
    private val mSearch: Search = Search()
    private val mResults: ResultsRecycler = ResultsRecycler()

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
        searchButton.setOnClickListener{
            val t: FragmentTransaction = supportFragmentManager.beginTransaction()
            t.remove(mBrowser).commit()
            supportFragmentManager.commit {
                add(R.id.search_fragment_layout, mSearch)
            }
        }
    }

    override fun serveResults() {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.remove(mBrowser).commit()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            mResults.arguments = Bundle()
            add(R.id.tutorials_recycler_browser, mResults)
        }
    }
}