package com.orzechowski.aidme

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.aidme.creator.*
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
    private lateinit var mVersionTreeComposer: VersionTreeComposer
    private lateinit var mVersions: List<Version>
    private lateinit var mMultimedias: List<Multimedia>
    private lateinit var mInstructions: List<InstructionSet>
    private lateinit var mSounds: List<TutorialSound>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)
        val progressButton = findViewById<Button>(R.id.creator_step_button)
        progressButton.setOnClickListener {
            mVersions = mVersionComposer.versions
            mMultimedias = mMultimediaComposer.multimedias
            mInstructions = mInstructionComposer.instructions
            mSounds = mSoundComposer.sounds
            val fragmentList: List<*> = supportFragmentManager.fragments
            for(f in fragmentList) {
                val t: FragmentTransaction = supportFragmentManager.beginTransaction()
                when(f) {
                    is InstructionComposer -> t.remove(mInstructionComposer).commit()
                    is MultimediaComposer -> t.remove(mMultimediaComposer).commit()
                    is VersionComposer -> t.remove(mVersionComposer).commit()
                    is SoundComposer -> t.remove(mSoundComposer).commit()
                }
            }
            mVersionTreeComposer = VersionTreeComposer(mVersions)
            supportFragmentManager.commit {
                add(R.id.layout_version_tree, mVersionTreeComposer)
            }
            progressButton.setOnClickListener {
                for(f in fragmentList) {
                    val t: FragmentTransaction = supportFragmentManager.beginTransaction()
                    if(f is VersionTreeComposer) t.remove(mVersionTreeComposer).commit()
                }
            }
        }
        supportFragmentManager.commit {
            add(R.id.layout_version_creator, mVersionComposer)
            add(R.id.layout_instruction_creator, mInstructionComposer)
            add(R.id.layout_multimedia_creator, mMultimediaComposer)
            add(R.id.layout_sound_creator, mSoundComposer)
        }
    }
}
