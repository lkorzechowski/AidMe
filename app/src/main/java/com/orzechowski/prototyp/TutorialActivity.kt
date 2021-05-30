package com.orzechowski.prototyp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
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
    private val videoEmbed: VideoView = findViewById(R.id.video_embed)
    private val imageEmbed: ImageView = findViewById(R.id.image_embed)
    private var versions: List<Version>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }

    override fun onResume(){
        super.onResume()
        videoEmbed.visibility = View.GONE
        imageEmbed.visibility = View.GONE

        val mVersionViewModel = VersionViewModel(application)
        versions = mVersionViewModel.getByTutorialId(0L).value!!

        val adapter = VersionsListAdapter(this, versions, this)
        val recycler: RecyclerView = findViewById(R.id.versions_rv)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext)
    }


    private fun getInstructionsRecycler(versionId: Long){
        bundle.putLong("versionId", versionId)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
    }

    override fun onViewClick(position: Int) {
        videoEmbed.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        videoEmbed.setOnCompletionListener { videoEmbed.start() }
        if(versions!=null) getInstructionsRecycler(versions!!.get(position).versionId)
    }
}