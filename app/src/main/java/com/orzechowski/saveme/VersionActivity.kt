package com.orzechowski.saveme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.saveme.settings.database.PreferenceViewModel
import com.orzechowski.saveme.tutorial.RequestMedia
import com.orzechowski.saveme.tutorial.version.TutorialLoading
import com.orzechowski.saveme.tutorial.version.VersionListAdapter
import com.orzechowski.saveme.tutorial.version.VersionRecycler
import com.orzechowski.saveme.tutorial.version.database.Version
import kotlin.properties.Delegates

class VersionActivity : AppCompatActivity(R.layout.activity_version),
    VersionListAdapter.ActivityCallback, VersionRecycler.ActivityCallback,
    TutorialLoading.ActivityCallback
{
    private val mBundle = Bundle()
    private val mVersionRecycler = VersionRecycler(this)
    private val mTutorialLoading = TutorialLoading(this, this)
    private var mTutorialId by Delegates.notNull<Long>()
    private lateinit var mRequestMedia: RequestMedia
    private lateinit var mPickedVersion: Version

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mTutorialId = intent.getLongExtra("tutorialId", -1L)
        mBundle.putLong("tutorialId", mTutorialId)
        supportFragmentManager.commit {
            mVersionRecycler.arguments = mBundle
            add(R.id.layout_version_fragment, mVersionRecycler)
        }
    }

    override fun onResume()
    {
        mRequestMedia = RequestMedia(this, mTutorialId).also {
            it.requestData(cacheDir, getString(R.string.url))
        }
        PreferenceViewModel(application).get().observe(this, {
            if(it.motive) setTheme(R.style.ColorBlind)
        })
        super.onResume()
    }

    override fun onClick(version: Version)
    {
        if(version.hasChildren) {
            mVersionRecycler.getChildVersions(version.versionId)
        } else {
            mPickedVersion = version
            supportFragmentManager.commit {
                remove(mVersionRecycler)
                add(R.id.layout_version_fragment, mTutorialLoading)
            }
        }
    }

    override fun defaultVersion(version: Version)
    {
        onClick(version)
    }

    override fun onDestroy()
    {
        mRequestMedia.end()
        super.onDestroy()
    }

    override fun callTutorial()
    {
        val intent = Intent(this@VersionActivity, TutorialActivity::class.java)
        intent.putExtra("versionId", mPickedVersion.versionId)
        intent.putExtra("tutorialId", mPickedVersion.tutorialId)
        intent.putExtra("delayGlobalSound", mPickedVersion.delayGlobalSound)
        supportFragmentManager.beginTransaction().remove(mTutorialLoading)
        startActivity(intent)
    }

    override fun onBackPressed()
    {
        mRequestMedia.end()
        if(mTutorialLoading.isAdded) mTutorialLoading.mProgressThread.interrupt()
        supportFragmentManager.beginTransaction().remove(mTutorialLoading)
        startActivity(Intent(this@VersionActivity, BrowserActivity::class.java))
    }
}
