package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.tutorial.RequestMedia
import com.orzechowski.aidme.tutorial.version.VersionListAdapter
import com.orzechowski.aidme.tutorial.version.VersionRecycler
import com.orzechowski.aidme.tutorial.version.database.Version
import kotlin.properties.Delegates

class VersionActivity : AppCompatActivity(R.layout.activity_version),
VersionListAdapter.ActivityCallback, VersionRecycler.ActivityCallback
{
    private val bundle = Bundle()
    private val mVersionRecycler = VersionRecycler(this)
    private lateinit var mRequestMedia: RequestMedia
    private var mTutorialId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mTutorialId = intent.getLongExtra("tutorialId", -1L)
        bundle.putLong("tutorialId", mTutorialId)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            mVersionRecycler.arguments = bundle
            add(R.id.layout_versions_list, mVersionRecycler)
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
            val tutorial = Intent(this@VersionActivity, TutorialActivity::class.java)
            tutorial.putExtra("versionId", version.versionId)
            tutorial.putExtra("tutorialId", version.tutorialId)
            tutorial.putExtra("delayGlobalSound", version.delayGlobalSound)
            startActivity(tutorial)
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
}
