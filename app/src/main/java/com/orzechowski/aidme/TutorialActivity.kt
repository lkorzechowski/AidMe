package com.orzechowski.aidme

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.aidme.tutorial.instructions.InstructionsRecycler
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.MultimediaPlayer
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaInVersionViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.SoundAdapter
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundViewModel

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial)
{
    private lateinit var mSoundAdapter: SoundAdapter
    private val mMediaPlayer = MultimediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()
        val bundle = intent.extras!!
        setup(intent.extras?.getString("versionGlobalSounds"))
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
    }

    private fun setup(versionGlobalSounds: String?)
    {
        val versionId = intent.extras?.getLong("versionId") ?: -1L
        val tutorialId = intent.extras?.getLong("tutorialId") ?: -1L
        mMediaPlayer.mTutorialId = tutorialId

        if(!versionGlobalSounds.isNullOrEmpty()) {
            mSoundAdapter = SoundAdapter(
                versionId,
                intent.extras?.getBoolean("delayGlobalSound") ?: false,
                versionGlobalSounds, this
            )
            val soundViewModel = ViewModelProvider(this).get(TutorialSoundViewModel::class.java)
            soundViewModel.getByTutorialId(tutorialId).observe(this, { sounds ->
                mSoundAdapter.setData(sounds)
                mSoundAdapter.deploy()
            })
        }
        val mediaInVersionViewModel = MultimediaInVersionViewModel(Application())
        val mediaViewModel = MultimediaViewModel(Application())
        mediaInVersionViewModel.getByVersionId(versionId).observe(this, { list ->
            for(id: Long in list) {
                mediaViewModel.getByMediaIdAndTutorialId(id, tutorialId).observe(this,
                    { item->
                        mMediaPlayer.appendMultimedia(item)
                        if(id==list[list.size-1]) mMediaPlayer.getPlayer(0)
                    })
            }
            supportFragmentManager.commit {
                add(R.id.layout_multimedia, mMediaPlayer)
            }
        })
    }

    override fun onStop()
    {
        try {
            mSoundAdapter.destroy()
        } catch(ignored: UninitializedPropertyAccessException){}
        super.onStop()
    }
}
