package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.tutorial.version.VersionListAdapter
import com.orzechowski.aidme.tutorial.version.VersionRecycler
import com.orzechowski.aidme.tutorial.version.database.Version

class VersionActivity : AppCompatActivity(R.layout.activity_version),
VersionListAdapter.OnClickListener
{
    private val bundle = Bundle()
    private val mVersionRecycler = VersionRecycler()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_version)
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
            tutorial.putExtra("versionGlobalSounds", version.sounds)
            startActivity(tutorial)
        }
    }
}
