package com.orzechowski.aidme

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
import com.orzechowski.aidme.helper.BooleanRequest
import com.orzechowski.aidme.helper.HelperSettings

class HelperActivity : AppCompatActivity(), HelperSettings.ActivityCallback
{
    private val red = ColorStateList.valueOf(Color.argb(100, 255, 0, 0))
    private val green = ColorStateList.valueOf(Color.argb(100, 0, 255, 0))
    private val mSettings = HelperSettings(this)
    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var queue: RequestQueue
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)
        mView = findViewById(R.id.helper_primary_view)
        viewModelProvider = ViewModelProvider(this)
        var email = intent.getStringExtra("email")!!.replace(".", "xyz121")
            .replace("@", "xyz122")
        mSettings.arguments = bundleOf(Pair("email", email))
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        queue = RequestQueue(cache, network).apply {
            start()
        }
        queue.add(
            JsonObjectRequest(Request.Method.GET, url + "login/" + email, null,
            { array ->
                val verified = array.getBoolean("verified")
                var helping = array.getBoolean("helping")
                if (verified) {
                    val helperToggleButton =
                        findViewById<ExtendedFloatingActionButton>(R.id.helper_toggle_help)
                    if(helping) {
                        helperToggleButton.setIconResource(R.drawable.ic_check)
                        helperToggleButton.backgroundTintList = green
                    } else {
                        helperToggleButton.setIconResource(R.drawable.ic_cross)
                        helperToggleButton.backgroundTintList = red
                    }
                    helperToggleButton.setOnClickListener {
                        if(!helping) {
                            helping = true
                            queue.add(BooleanRequest(Request.Method.GET,
                                url + "help/" + email + "/t", null, {}, {}))
                            helperToggleButton.backgroundTintList = green
                            helperToggleButton.setIconResource(R.drawable.ic_check)
                        } else {
                            helping = false
                            queue.add(BooleanRequest(Request.Method.GET,
                                url + "help/" + email + "/f", null, {}, {}))
                            helperToggleButton.backgroundTintList = red
                            helperToggleButton.setIconResource(R.drawable.ic_cross)
                        }
                    }
                    val createTutorialButton =
                        findViewById<Button>(R.id.helper_create_tutorial_button)
                    createTutorialButton.setOnClickListener {
                        startActivity(Intent(this@HelperActivity,
                            CreatorActivity::class.java))
                    }
                    findViewById<Button>(R.id.helper_settings_button).setOnClickListener {
                        mView.visibility = View.INVISIBLE
                        supportFragmentManager.commit {
                            add(R.id.fragment_overlay_layout, mSettings)
                        }
                    }
                    findViewById<Button>(R.id.helper_chat_button).setOnClickListener {

                    }
                } else {
                    startActivity(Intent(this@HelperActivity,
                        UnverifiedHelperActivity::class.java))
                }
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun onBackPressed()
    {
        startActivity(Intent(this@HelperActivity, MainActivity::class.java))
    }

    override fun onDestroy()
    {
        queue.stop()
        super.onDestroy()
    }

    override fun submittedSettings()
    {
        supportFragmentManager.beginTransaction().remove(mSettings).commit()
        mView.visibility = View.VISIBLE
    }
}
