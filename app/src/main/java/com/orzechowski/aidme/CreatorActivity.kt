package com.orzechowski.aidme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.creator.InstructionComposer
import com.orzechowski.aidme.creator.MultimediaComposer
import com.orzechowski.aidme.creator.SoundComposer
import com.orzechowski.aidme.creator.VersionComposer

class CreatorActivity : AppCompatActivity()
{
    private val mInstructionCOmposer = InstructionComposer()
    private val mMultimediaComposer = MultimediaComposer()
    private val mVersionComposer = VersionComposer()
    private val mSoundComposer = SoundComposer()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)
        supportFragmentManager.commit {
            add(R.id.layout_version_creator, mVersionComposer)
            add(R.id.layout_instruction_creator, mInstructionCOmposer)
            add(R.id.layout_multimedia_creator, mMultimediaComposer)
            add(R.id.layout_sound_creator, mSoundComposer)
        }
    }
}
