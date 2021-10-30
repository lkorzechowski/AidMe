package com.orzechowski.aidme

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.aidme.tutorial.database.TutorialLink
import com.orzechowski.aidme.tutorial.instructions.InstructionsRecycler
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.MultimediaPlayer
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.SoundAdapter
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.VersionSoundViewModel
import kotlin.properties.Delegates

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial),
    InstructionsRecycler.CallbackForTutorialLink
{
    private lateinit var mSoundAdapter: SoundAdapter
    private val mMediaPlayer = MultimediaPlayer()
    private lateinit var mInstructionsRecycler: InstructionsRecycler
    private var mTutorialId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val bundle = intent.extras!!
        mInstructionsRecycler = InstructionsRecycler(this)
        mTutorialId = intent.extras?.getLong("tutorialId") ?: -1L
        val versionId = intent.extras?.getLong("versionId") ?: -1L
        mMediaPlayer.mTutorialId = mTutorialId
        val soundInVersionViewModel = ViewModelProvider(this)
            .get(VersionSoundViewModel::class.java)
        val soundViewModel = ViewModelProvider(this).get(TutorialSoundViewModel::class.java)
        soundInVersionViewModel.getByVersionId(versionId).observe(this, { soundsInVersion ->
            soundViewModel.getByTutorialId(mTutorialId).observe(this, { sounds ->
                mSoundAdapter = SoundAdapter(
                    intent.extras?.getBoolean("delayGlobalSound") ?: false,
                    soundsInVersion, sounds, this
                )
                mSoundAdapter.deploy()
            })
        })
        val mediaInVersionViewModel = ViewModelProvider(this)
            .get(VersionMultimediaViewModel::class.java)
        val mediaViewModel = ViewModelProvider(this).get(MultimediaViewModel::class.java)
        mediaInVersionViewModel.getByVersionId(versionId).observe(this, { multimediaList ->
            mediaViewModel.getByTutorialId(mTutorialId)
                .observe(this, {
                    for(multimedia : Multimedia in it) {
                        if(multimediaList.contains(multimedia.multimediaId))
                            mMediaPlayer.appendMultimedia(multimedia)
                    }
                    if(supportFragmentManager.fragments.size==1) {
                        supportFragmentManager.commit {
                            add(R.id.layout_multimedia, mMediaPlayer)
                        }
                    }
                })
        })
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            mInstructionsRecycler.arguments = bundle
            add(R.id.layout_instructions_list, mInstructionsRecycler)
        }
    }

    override fun onStop()
    {
        try {
            mSoundAdapter.destroy()
        } catch(ignored: UninitializedPropertyAccessException){}
        super.onStop()
    }

    override fun serveNewTutorial(tutorialLink: TutorialLink)
    {
        val intent = Intent(this@TutorialActivity, VersionActivity::class.java)
        intent.putExtra("tutorialId", tutorialLink.tutorialId)
        startActivity(intent)
    }

    override fun onBackPressed()
    {
        val intent = Intent(this@TutorialActivity, VersionActivity::class.java)
        intent.putExtra("tutorialId", mTutorialId)
        startActivity(intent)
    }
}
