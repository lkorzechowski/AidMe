package com.orzechowski.saveme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.saveme.browser.categories.CategoryRecycler
import com.orzechowski.saveme.browser.results.HelperFull
import com.orzechowski.saveme.browser.results.RequestLiveAid
import com.orzechowski.saveme.browser.results.ResultsRecycler
import com.orzechowski.saveme.browser.search.Search
import com.orzechowski.saveme.tutorial.database.Tutorial

//Aktywność w której zamieszczono fragmenty przeszukiwarek kategorii i wyników, oraz fragment
//wyszukiwarki. Klasy podlegające tej aktywności znajdują się w com.orzechowski.saveme.browser.
class BrowserActivity : AppCompatActivity(R.layout.activity_browser),
    CategoryRecycler.CallbackToResults, ResultsRecycler.CallbackForTutorial,
    Search.CallbackForTutorial, RequestLiveAid.ActivityCallback
{
    private lateinit var mCategory: CategoryRecycler
    private val mSearch = Search(this)
    private val mResults = ResultsRecycler(this)
    private val mRequest = RequestLiveAid(this)
    private val mPhoneIntent = Intent(Intent.ACTION_CALL)
    private val mHelpRefusedForTag = mutableListOf<Long>()
    private var returning = false
    private lateinit var mSearchButton: Button
    private val mPermissionResult = registerForActivityResult(ActivityResultContracts
        .RequestPermission()
    ) { result ->
        if (result) startActivity(mPhoneIntent)
    }

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
        mRequest.getRequest(cacheDir, tagId)
        supportFragmentManager.beginTransaction().remove(mCategory).commit()
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
        supportFragmentManager.beginTransaction().remove(mResults).commit()
        startTutorial(tutorial.tutorialId)
    }

    override fun serveSearchedTutorial(tutorial: Tutorial)
    {
        supportFragmentManager.beginTransaction().remove(mSearch).commit()
        startTutorial(tutorial.tutorialId)
    }

    private fun startTutorial(tutorialId: Long)
    {
        val intent = Intent(this@BrowserActivity, VersionActivity::class.java)
        intent.putExtra("tutorialId", tutorialId)
        startActivity(intent)
    }

    override fun suggestHelper(helper: HelperFull, tagId: Long)
    {
        if(mResults.isAdded) {
            supportFragmentManager.beginTransaction().remove(mResults).commit()
            val suggestLayout = findViewById<View>(R.id.suggest_helper_layout)
            suggestLayout.visibility = View.VISIBLE
            findViewById<TextView>(R.id.suggest_helper_info).text = helper.helperDescription
            findViewById<Button>(R.id.suggest_helper_accept).setOnClickListener {
                suggestLayout.visibility = View.GONE
                mRequest.postRequest(helper.helperNumber)
                mPhoneIntent.data = Uri.parse("tel:" + helper.helperNumber)
                if(ContextCompat.checkSelfPermission(applicationContext, Manifest.permission
                        .CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionResult.launch(Manifest.permission.CALL_PHONE)
                } else {
                    startActivity(mPhoneIntent)
                }
            }
            findViewById<Button>(R.id.suggest_helper_refuse).setOnClickListener {
                mHelpRefusedForTag.add(tagId)
                suggestLayout.visibility = View.GONE
                commitResults()
            }
        }
    }
}
