package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.tutorial.version.VersionListAdapter
import com.orzechowski.aidme.tutorial.version.VersionRecycler
import com.orzechowski.aidme.tutorial.version.database.Version

class VersionActivity : AppCompatActivity(R.layout.activity_version),
VersionListAdapter.OnClickListener, VersionRecycler.ActivityCallback
{
    private val bundle = Bundle()
    private val mVersionRecycler = VersionRecycler(this)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        bundle.putLong("tutorialId", intent.getLongExtra("tutorialId", -1L))
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            mVersionRecycler.arguments = bundle
            add(R.id.layout_versions_list, mVersionRecycler)
        }
    }

    override fun onClick(version: Version)
    {
        if(version.hasChildren) {
            mVersionRecycler.getChildVersions(version.versionId)
        }
        else {
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
}
