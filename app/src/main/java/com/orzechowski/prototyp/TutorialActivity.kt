package com.orzechowski.prototyp

import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.orzechowski.prototyp.instructionsrecycler.InstructionsRecycler

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial)
{

    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()

        val videoEmbed: VideoView = findViewById(R.id.video_embed)
        val imageEmbed: ImageView = findViewById(R.id.image_embed)
        videoEmbed.setVideoPath("android.resource://" + packageName + "/" + R.raw.cpr_video)
        videoEmbed.setOnCompletionListener { videoEmbed.start() }

        bundle = intent.extras!!
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
    }
}