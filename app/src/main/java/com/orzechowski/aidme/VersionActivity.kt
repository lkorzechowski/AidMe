package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.tutorial.RequestMedia
import com.orzechowski.aidme.tutorial.version.TutorialLoading
import com.orzechowski.aidme.tutorial.version.VersionListAdapter
import com.orzechowski.aidme.tutorial.version.VersionRecycler
import com.orzechowski.aidme.tutorial.version.database.Version
import kotlin.properties.Delegates

class VersionActivity : AppCompatActivity(R.layout.activity_version),
    VersionListAdapter.ActivityCallback, VersionRecycler.ActivityCallback,
    TutorialLoading.ActivityCallback
{
    private val bundle = Bundle()
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
        bundle.putLong("tutorialId", mTutorialId)
        supportFragmentManager.commit {
            mVersionRecycler.arguments = bundle
            add(R.id.layout_version_fragment, mVersionRecycler)
        }

    }

    override fun onResume()
    {
        mRequestMedia = RequestMedia(this, mTutorialId).also { it.requestData(cacheDir) }
        super.onResume()
    }

    override fun onClick(version: Version)
    {
        if(version.hasChildren) {
            mVersionRecycler.getChildVersions(version.versionId)
        } else {
            mPickedVersion = version
            supportFragmentManager.beginTransaction().remove(mVersionRecycler).commit()
            supportFragmentManager.commit {
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
