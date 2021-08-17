package com.orzechowski.aidme

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.orzechowski.aidme.creator.initial.InstructionComposer
import com.orzechowski.aidme.creator.initial.MultimediaComposer
import com.orzechowski.aidme.creator.initial.SoundComposer
import com.orzechowski.aidme.creator.initial.VersionComposer
import com.orzechowski.aidme.creator.initial.imagebrowser.ImageBrowserLoader
import com.orzechowski.aidme.creator.versioninstruction.VersionInstructionComposer
import com.orzechowski.aidme.creator.versiontree.VersionTreeComposer
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import com.orzechowski.aidme.tutorial.version.database.Version
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction

class CreatorActivity : AppCompatActivity(R.layout.activity_creator),
    VersionTreeComposer.CallbackToActivity, VersionInstructionComposer.CallbackToActivity,
        MultimediaComposer.CallbackToActivity
{
    private val mInstructionComposer = InstructionComposer()
    private val mMultimediaComposer = MultimediaComposer(this)
    private val mVersionComposer = VersionComposer()
    private val mSoundComposer = SoundComposer()
    private lateinit var mVersionTreeComposer: VersionTreeComposer
    private lateinit var mVersionInstructionComposer: VersionInstructionComposer
    private lateinit var mVersions: List<Version>
    private lateinit var mMultimedias: List<Multimedia>
    private lateinit var mInstructions: List<InstructionSet>
    private lateinit var mSounds: List<TutorialSound>
    private lateinit var mVersionInstructions: Collection<VersionInstruction>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        val progressButton = findViewById<Button>(R.id.creator_step_one_button)
        progressButton.setOnClickListener {
            mVersions = mVersionComposer.versions
            mMultimedias = mMultimediaComposer.multimedias
            mInstructions = mInstructionComposer.instructions
            mSounds = mSoundComposer.sounds
            supportFragmentManager.beginTransaction().remove(mInstructionComposer).commit()
            supportFragmentManager.beginTransaction().remove(mMultimediaComposer).commit()
            supportFragmentManager.beginTransaction().remove(mVersionComposer).commit()
            supportFragmentManager.beginTransaction().remove(mSoundComposer).commit()
            progressButton.visibility = View.GONE
            mVersionTreeComposer = VersionTreeComposer(mVersions, this)
            supportFragmentManager.commit {
                add(R.id.layout_version_tree, mVersionTreeComposer)
            }
        }
        supportFragmentManager.commit {
            add(R.id.layout_version_creator, mVersionComposer)
            add(R.id.layout_instruction_creator, mInstructionComposer)
            add(R.id.layout_multimedia_creator, mMultimediaComposer)
            add(R.id.layout_sound_creator, mSoundComposer)
        }
    }

    override fun finalizeVersionTree(versions: MutableList<Version>)
    {
        mVersions = versions
        mVersionInstructionComposer = VersionInstructionComposer(mVersions, mInstructions,
            this)
        supportFragmentManager.beginTransaction().remove(mVersionTreeComposer).commit()
        supportFragmentManager.commit {
            add(R.id.layout_version_instruction, mVersionInstructionComposer)
        }
    }

    override fun finalizeVersionInstructions(versionInstructions: Collection<VersionInstruction>)
    {
        mVersionInstructions = versionInstructions
        supportFragmentManager.beginTransaction().remove(mVersionInstructionComposer).commit()
    }

    override fun callImageGallery()
    {
        findViewById<ScrollView>(R.id.initial_creator_view).visibility = View.GONE
        val browser =
            ImageBrowserLoader()
        supportFragmentManager.commit {
            add(R.id.layout_image_browser, browser)
        }
    }
}
