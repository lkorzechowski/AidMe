package com.orzechowski.prototyp

import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orzechowski.prototyp.instructionsrecycler.InstructionsRecycler
import com.orzechowski.prototyp.versionrecycler.VersionsListAdapter
import com.orzechowski.prototyp.versionrecycler.database.Version
import com.orzechowski.prototyp.versionrecycler.database.VersionViewModel

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial),
    VersionsListAdapter.OnViewClickListener
{

    private val bundle = Bundle()
    private lateinit var mVersionViewModel: VersionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()

        val recycler: RecyclerView = findViewById(R.id.versions_rv)
        mVersionViewModel = ViewModelProvider(this).get(VersionViewModel::class.java)
        val adapter = VersionsListAdapter(this)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        mVersionViewModel.getByTutorialId(0L)
            .observe(this) { versions -> adapter.setElementList(versions) }
    }

    private fun getInstructionsRecycler(versionId: Long){
        bundle.putLong("versionId", versionId)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
    }

    override fun onViewClick(version: Version) {
        val videoEmbed: VideoView = findViewById(R.id.video_embed)
        val imageEmbed: ImageView = findViewById(R.id.image_embed)

        videoEmbed.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        videoEmbed.setOnCompletionListener { videoEmbed.start() }
        getInstructionsRecycler(version.versionId)
    }
}