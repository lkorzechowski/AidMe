package com.orzechowski.prototyp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.prototyp.version.VersionListAdapter
import com.orzechowski.prototyp.version.VersionRecycler
import com.orzechowski.prototyp.version.database.Version

class VersionActivity : AppCompatActivity(R.layout.activity_version),
VersionListAdapter.OnClickListener {

    private val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_version)
        supportActionBar?.hide()

        bundle.putLong("tutorialId", 0L)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<VersionRecycler>(R.id.layout_versions_list, args = bundle)
        }
    }

    override fun onClick(version: Version) {
        val tutorial = Intent(this@VersionActivity, TutorialActivity::class.java)
        tutorial.putExtra("versionTutorialParts", version.instructions)
        tutorial.putExtra("versionSkipDelay", version.skipDelayOnGlobalSound)
        tutorial.putExtra("tutorialId", 0L)
        startActivity(tutorial)
    }
}