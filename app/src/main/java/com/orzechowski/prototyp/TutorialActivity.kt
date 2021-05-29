package com.orzechowski.prototyp

import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.prototyp.instructionsrecycler.InstructionsRecycler
import com.orzechowski.prototyp.versionrecycler.database.VersionViewModel

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial){
    private val bundle = Bundle()
    private val mVersionViewModel: VersionViewModel = ViewModelProvider(this)
        .get(VersionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initialize();
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }


    fun initialize(){
        val cprVideo = findViewById<VideoView>(R.id.video_embed)
        cprVideo.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        cprVideo.setOnCompletionListener { cprVideo.start() }
        cprVideo.start()
        getInstructionsRecycler()
    }

    fun getInstructionsRecycler(){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
    }
}