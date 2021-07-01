package com.orzechowski.aidme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.orzechowski.aidme.tutorial.database.Tutorial
import com.orzechowski.aidme.tutorial.database.TutorialViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.Player
import com.orzechowski.aidme.tutorial.recycler.InstructionsRecycler
import com.orzechowski.aidme.tutorial.sound.SoundAdapter

class TutorialActivity : AppCompatActivity(R.layout.activity_tutorial)
{
    private var mTutorial: Tutorial? = null
    private lateinit var soundAdapter: SoundAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_tutorial)
        supportActionBar?.hide()

        soundAdapter = SoundAdapter(
            intent.extras?.getLong("VersionId") ?: -1L,
            this,
            intent.extras?.getBoolean("delayGlobalSound") ?: false,
            intent.extras?.getString("versionGlobalSounds") ?: "")

        Thread {
            this.mTutorial = ViewModelProvider(this)
                .get(TutorialViewModel::class.java)
                .getByTutorialId(intent.extras?.getLong("tutorialId") ?: -1L)
        }.start()

        val bundle = intent.extras!!

        supportFragmentManager.commit {
            add<Player>(R.id.layout_multimedia, args = bundle)
        }

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<InstructionsRecycler>(R.id.layout_instructions_list, args = bundle)
        }
        checkObtainedTutorial()
    }

    private fun checkObtainedTutorial()
    {
        if(mTutorial==null) {
            Thread {
                Thread.sleep(4)
                checkObtainedTutorial()
            }.start()
        } else soundAdapter.deploy()
    }

    override fun onStop()
    {
        soundAdapter.destroy()
        super.onStop()
    }
}