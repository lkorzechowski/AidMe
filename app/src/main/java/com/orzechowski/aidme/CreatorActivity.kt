package com.orzechowski.aidme

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.creator.InstructionComposer
import com.orzechowski.aidme.creator.MultimediaComposer
import com.orzechowski.aidme.creator.SoundComposer
import com.orzechowski.aidme.creator.VersionComposer
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import com.orzechowski.aidme.tutorial.version.database.Version

class CreatorActivity : AppCompatActivity()
{
    private val mInstructionComposer = InstructionComposer()
    private val mMultimediaComposer = MultimediaComposer()
    private val mVersionComposer = VersionComposer()
    private val mSoundComposer = SoundComposer()
    private lateinit var mVersions: List<Version>
    private lateinit var mMultimedias: List<Multimedia>
    private lateinit var mInstructions: List<InstructionSet>
    private lateinit var mSounds: List<TutorialSound>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)
        val progressButtonOne = findViewById<Button>(R.id.creator_step_one_button)
        progressButtonOne.setOnClickListener {
            mVersions = mVersionComposer.versions
            mMultimedias = mMultimediaComposer.multimedias
            mInstructions = mInstructionComposer.instructions
            mSounds = mSoundComposer.sounds
        }
        supportFragmentManager.commit {
            add(R.id.layout_version_creator, mVersionComposer)
            add(R.id.layout_instruction_creator, mInstructionComposer)
            add(R.id.layout_multimedia_creator, mMultimediaComposer)
            add(R.id.layout_sound_creator, mSoundComposer)
        }
    }
}
