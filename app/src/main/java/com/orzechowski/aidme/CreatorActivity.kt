package com.orzechowski.aidme

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.orzechowski.aidme.creator.categorypicker.CategoryAssignment
import com.orzechowski.aidme.creator.initial.InstructionComposer
import com.orzechowski.aidme.creator.initial.MultimediaComposer
import com.orzechowski.aidme.creator.initial.SoundComposer
import com.orzechowski.aidme.creator.initial.VersionComposer
import com.orzechowski.aidme.creator.initial.imagebrowser.ImageBrowserLoader
import com.orzechowski.aidme.creator.initial.soundbrowser.Sound
import com.orzechowski.aidme.creator.initial.soundbrowser.SoundBrowserLoader
import com.orzechowski.aidme.creator.initial.soundbrowser.narrationbrowser.NarrationBrowserLoader
import com.orzechowski.aidme.creator.versioninstruction.VersionInstructionComposer
import com.orzechowski.aidme.creator.versiontree.VersionTreeComposer
import com.orzechowski.aidme.database.tag.TutorialTag
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import com.orzechowski.aidme.tutorial.version.database.Version
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction

class CreatorActivity : AppCompatActivity(R.layout.activity_creator),
    VersionTreeComposer.ActivityCallback, VersionInstructionComposer.ActivityCallback,
        MultimediaComposer.ActivityCallback, ImageBrowserLoader.ActivityCallback,
        SoundComposer.ActivityCallback, SoundBrowserLoader.ActivityCallback,
        InstructionComposer.ActivityCallback, NarrationBrowserLoader.ActivityCallback,
        CategoryAssignment.ActivityCallback
{
    private val mInstructionComposer = InstructionComposer(this)
    private val mMultimediaComposer = MultimediaComposer(this)
    private val mVersionComposer = VersionComposer()
    private val mSoundComposer = SoundComposer(this)
    private var mCategoryAssignment = CategoryAssignment(this)
    private lateinit var mView: ScrollView
    private lateinit var mSoundBrowser: SoundBrowserLoader
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var mNarrationBrowser: NarrationBrowserLoader
    private lateinit var mVersionTreeComposer: VersionTreeComposer
    private lateinit var mVersionInstructionComposer: VersionInstructionComposer
    private lateinit var mVersions: MutableList<Version>
    private lateinit var mMultimedias: List<Multimedia>
    private lateinit var mInstructions: List<InstructionSet>
    private lateinit var mSounds: List<TutorialSound>
    private lateinit var mVersionInstructions: Collection<VersionInstruction>
    private lateinit var mTutorialTags: List<TutorialTag>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        val progressButton = findViewById<Button>(R.id.creator_step_one_button)
        progressButton.setOnClickListener {
            if(mInstructionComposer.instructions.isEmpty()){
                Toast.makeText(this, R.string.instructions_not_provided, Toast.LENGTH_SHORT)
                    .show()
            } else {
                val versions = mVersionComposer.versions
                mVersions = if(versions.isEmpty()) {
                    mutableListOf(
                        Version(0, "", 0,
                            delayGlobalSound = true,
                            hasChildren = false,
                            hasParent = false,
                            parentVersionId = null
                        )
                    )
                } else versions
                mMultimedias = mMultimediaComposer.multimedias
                mInstructions = mInstructionComposer.instructions
                mSounds = mSoundComposer.sounds
                supportFragmentManager.beginTransaction().remove(mInstructionComposer).commit()
                supportFragmentManager.beginTransaction().remove(mMultimediaComposer).commit()
                supportFragmentManager.beginTransaction().remove(mVersionComposer).commit()
                supportFragmentManager.beginTransaction().remove(mSoundComposer).commit()
                mVersionTreeComposer = VersionTreeComposer(mVersions, this)
                commitVersionTree()
                mView.visibility = View.GONE
            }
        }
        mView = findViewById(R.id.initial_creator_view)
        commitInitial()
    }

    private fun commitInitial()
    {
        supportFragmentManager.commit {
            add(R.id.layout_version_creator, mVersionComposer)
            add(R.id.layout_instruction_creator, mInstructionComposer)
            add(R.id.layout_multimedia_creator, mMultimediaComposer)
            add(R.id.layout_sound_creator, mSoundComposer)
        }
    }

    private fun commitVersionTree()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mVersionTreeComposer)
        }
    }

    private fun commitVersionInstruction(versions: MutableList<Version>)
    {
        mVersionInstructionComposer = VersionInstructionComposer(versions, mInstructions,
            this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mVersionInstructionComposer)
        }
    }

    private fun commitCategoryAssignment()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mCategoryAssignment)
        }
    }

    override fun finalizeVersionTree(versions: MutableList<Version>)
    {
        mVersions = versions
        supportFragmentManager.beginTransaction().remove(mVersionTreeComposer).commit()
        commitVersionInstruction(versions)
    }

    override fun finalizeVersionInstructions(versionInstructions: Collection<VersionInstruction>)
    {
        mVersionInstructions = versionInstructions
        supportFragmentManager.beginTransaction().remove(mVersionInstructionComposer).commit()
        commitCategoryAssignment()
    }

    override fun callNarrationRecycler()
    {
        mView.visibility = View.GONE
        mNarrationBrowser = NarrationBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mNarrationBrowser)
        }
    }

    override fun callImageGallery()
    {
        mView.visibility = View.GONE
        mImageBrowser = ImageBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mImageBrowser)
        }
    }

    override fun callSoundRecycler()
    {
        mView.visibility = View.GONE
        mSoundBrowser = SoundBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mSoundBrowser)
        }
    }

    override fun narrationSubmitted(narration: Sound)
    {
        supportFragmentManager.beginTransaction().remove(mNarrationBrowser).commit()
        mInstructionComposer.instructions[mInstructionComposer.currentPosition].narrationUriString =
            narration.uri.toString()
        mInstructionComposer.resetAdapterElements()
        mView.visibility = View.VISIBLE
    }

    override fun imageSubmitted(uri: Uri)
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        mMultimediaComposer.multimedias[mMultimediaComposer.currentPosition].fileUriString =
            uri.toString()
        mMultimediaComposer.resetAdapterElements()
        mView.visibility = View.VISIBLE
    }

    override fun soundSubmitted(sound: Sound)
    {
        val position = mSoundComposer.currentPosition
        supportFragmentManager.beginTransaction().remove(mSoundBrowser).commit()
        mSoundComposer.sounds[position].fileName = sound.displayName
        mSoundComposer.sounds[position].fileUriString = sound.uri.toString()
        mSoundComposer.resetAdapterElements()
        mView.visibility = View.VISIBLE
    }

    override fun onBackPressed()
    {
        val fragmentList: List<*> = supportFragmentManager.fragments
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        var handled = false
        for(f in fragmentList) {
            when(f) {
                is ImageBrowserLoader -> {
                    t.remove(mImageBrowser).commit()
                    mView.visibility = View.VISIBLE
                    handled = true
                }
                is VersionTreeComposer -> {
                    t.remove(mVersionTreeComposer).commit()
                    mView.visibility = View.VISIBLE
                    commitInitial()
                    handled = true
                }
                is VersionInstructionComposer -> {
                    t.remove(mVersionInstructionComposer).commit()
                    commitVersionTree()
                    handled = true
                }
                is CategoryAssignment -> {
                    val currentLevel = f.level
                    if(currentLevel>1) {
                        mCategoryAssignment.restorePrevious()
                        handled = true
                    } else {
                        t.remove(mCategoryAssignment).commit()
                        mCategoryAssignment = CategoryAssignment(this)
                        if(mVersions.size>1) commitVersionInstruction(mVersions)
                        else {
                            mView.visibility = View.VISIBLE
                            commitInitial()
                        }
                        handled = true
                    }
                }
                is SoundBrowserLoader -> {
                    t.remove(mSoundBrowser).commit()
                    mView.visibility = View.VISIBLE
                    handled = true
                }
            }
        }
        if(!handled) super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==121) {
            callImageGallery()
        } else if(requestCode==122) {
            callSoundRecycler()
        }
    }

    override fun categorySelected(tutorialTags: MutableList<TutorialTag>)
    {
        mTutorialTags = tutorialTags
    }
}
