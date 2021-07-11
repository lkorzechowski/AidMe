package com.orzechowski.aidme

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.aidme.tutorial.database.Tutorial
import com.orzechowski.aidme.tutorial.database.TutorialViewModel
import com.orzechowski.aidme.tutorial.instructionsrecycler.InstructionsRecycler
import com.orzechowski.aidme.tutorial.mediaplayer.MultimediaPlayer
import com.orzechowski.aidme.tutorial.mediaplayer.database.MultimediaInVersionViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.database.MultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.SoundAdapter
import com.orzechowski.aidme.tutorial.mediaplayer.sound.TutorialSoundViewModel
import kotlin.properties.Delegates

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial)
{
    private var mTutorial: Tutorial? = null
    private lateinit var mSoundAdapter: SoundAdapter
    private val mMediaPlayer =
        MultimediaPlayer()
    private var mTutorialId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()
        val bundle = intent.extras!!
        mTutorialId = intent.extras?.getLong("tutorialId") ?: -1L
        soundAdapterSetup(intent.extras?.getString("versionGlobalSounds"))
        val mediaInVersionViewModel =
            MultimediaInVersionViewModel(
                Application()
            )
        mediaInVersionViewModel
            .getByVersionId(intent.extras?.getLong("versionId") ?: -1L)
            .observe(this, { list ->
                mMediaPlayer.mTutorialId = mTutorialId
                val mediaViewModel = MultimediaViewModel(Application())
                for(id: Long in list){
                    mediaViewModel.getByMediaIdAndTutorialId(id, mTutorialId)
                        .observe(this, { item ->
                            mMediaPlayer.mMultimedias.add(item)
                        })
                }
        })
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
        checkObtainedTutorial()
    }

    private fun soundAdapterSetup(versionGlobalSounds: String?)
    {
        if(!versionGlobalSounds.isNullOrEmpty()) {
            mSoundAdapter = SoundAdapter(
                intent.extras?.getLong("VersionId") ?: -1L,
                intent.extras?.getBoolean("delayGlobalSound") ?: false,
                versionGlobalSounds, this
            )
            val soundViewModel = TutorialSoundViewModel(Application())
            soundViewModel.getByTutorialId(mTutorialId).observe(this, {
                mSoundAdapter.setData(it)
            })
            Thread {
                this.mTutorial = ViewModelProvider(this)
                    .get(TutorialViewModel::class.java)
                    .getByTutorialId(mTutorialId)
            }.start()
        }
    }

    private fun checkObtainedTutorial()
    {
        if(mTutorial==null || mSoundAdapter.mSounds.isEmpty()) {
            Thread {
                Thread.sleep(2)
                checkObtainedTutorial()
            }.start()
        } else {
            if(!mMediaPlayer.isAdded) {
                supportFragmentManager.commit {
                    add(R.id.layout_multimedia, mMediaPlayer)
                }
            }
            mSoundAdapter.deploy()
        }
    }

    override fun onStop()
    {
        try {
            mSoundAdapter.destroy()
        } catch(ignored: UninitializedPropertyAccessException){}
        super.onStop()
    }
}
