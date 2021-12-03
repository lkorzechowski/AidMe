package com.orzechowski.saveme

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.orzechowski.saveme.helper.HelperSettings
import com.orzechowski.saveme.helper.PeerReview
import com.orzechowski.saveme.settings.database.PreferenceViewModel
import com.orzechowski.saveme.tutorial.database.Tutorial
import com.orzechowski.saveme.volley.BooleanRequest

class HelperActivity : AppCompatActivity(R.layout.activity_helper), HelperSettings.ActivityCallback,
    PeerReview.ActivityCallback
{
    private val mRed = ColorStateList.valueOf(Color.RED)
    private val mGreen = ColorStateList.valueOf(Color.GREEN)
    private val mSettings = HelperSettings(this)
    private val mPeerReview = PeerReview(this)
    private lateinit var mViewModelProvider: ViewModelProvider
    private lateinit var mQueue: RequestQueue
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        mView = findViewById(R.id.helper_primary_view)
        mViewModelProvider = ViewModelProvider(this)
        val email = intent.getStringExtra("email")!!.replace(".", "xyz121")
            .replace("@", "xyz122")
        val argument = bundleOf(Pair("email", email))
        mSettings.arguments = argument
        mPeerReview.arguments = argument
        val url = getString(R.string.url)
        mQueue = RequestQueue(DiskBasedCache(cacheDir, 1024 * 1024),
            BasicNetwork(HurlStack())).apply { start() }
        mQueue.add(
            JsonObjectRequest(Request.Method.GET, url + "login/" + email, null,
            { array ->
                val verified = array.getBoolean("verified")
                var helping = array.getBoolean("helping")
                if (verified) {
                    val helperToggleButton =
                        findViewById<ExtendedFloatingActionButton>(R.id.helper_toggle_help)
                    if(helping) {
                        helperToggleButton.setIconResource(R.drawable.ic_check)
                        helperToggleButton.backgroundTintList = mGreen
                    } else {
                        helperToggleButton.setIconResource(R.drawable.ic_cross)
                        helperToggleButton.backgroundTintList = mRed
                    }
                    helperToggleButton.setOnClickListener {
                        if(!helping) {
                            helping = true
                            mQueue.add(BooleanRequest(Request.Method.GET,
                                url + "help/" + email + "/t", null, {}, {})
                            )
                            helperToggleButton.backgroundTintList = mGreen
                            helperToggleButton.setIconResource(R.drawable.ic_check)
                        } else {
                            helping = false
                            mQueue.add(BooleanRequest(
                                Request.Method.GET, url + "help/" + email + "/f",
                                null, null, null)
                            )
                            helperToggleButton.backgroundTintList = mRed
                            helperToggleButton.setIconResource(R.drawable.ic_cross)
                        }
                    }
                    val createTutorialButton =
                        findViewById<Button>(R.id.helper_create_tutorial_button)
                    createTutorialButton.setOnClickListener {
                        intent = Intent(this@HelperActivity,
                            CreatorActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }
                    findViewById<Button>(R.id.helper_settings_button).setOnClickListener {
                        mView.visibility = View.INVISIBLE
                        supportFragmentManager.commit {
                            add(R.id.fragment_overlay_layout, mSettings)
                        }
                    }
                    findViewById<Button>(R.id.helper_peer_review_button).setOnClickListener {
                        mView.visibility = View.INVISIBLE
                        supportFragmentManager.commit {
                            add(R.id.fragment_overlay_layout, mPeerReview)
                        }
                    }
                } else {
                    intent = Intent(this@HelperActivity,
                        UnverifiedHelperActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                }
            }, {
                startActivity(Intent(this@HelperActivity, HelperActivity::class.java))
            })
        )
    }

    override fun onBackPressed()
    {
        startActivity(Intent(this@HelperActivity, MainActivity::class.java))
    }

    override fun onDestroy()
    {
        mQueue.stop()
        super.onDestroy()
    }

    override fun submittedSettings()
    {
        supportFragmentManager.beginTransaction().remove(mSettings).commit()
        mView.visibility = View.VISIBLE
    }

    override fun playTutorial(tutorial: Tutorial)
    {
        intent = Intent(this@HelperActivity, VersionActivity::class.java)
        intent.putExtra("tutorialId", tutorial.tutorialId)
        startActivity(intent)
    }

    override fun onResume()
    {
        PreferenceViewModel(application).get().observe(this, {
            if(it.motive) setTheme(R.style.ColorBlind)
        })
        super.onResume()
    }
}
