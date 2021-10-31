package com.orzechowski.aidme

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonArrayRequest
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.orzechowski.aidme.helper.BooleanRequest
import com.orzechowski.aidme.helper.database.HelperSession
import com.orzechowski.aidme.helper.database.HelperSessionViewModel
import com.orzechowski.aidme.imagebrowser.ImageBrowserLoader

class HelperActivity : AppCompatActivity(), ImageBrowserLoader.ActivityCallback
{
    private val red = ColorStateList.valueOf(Color.argb(100, 255, 0, 0))
    private val green = ColorStateList.valueOf(Color.argb(100, 0, 255, 0))
    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var queue: RequestQueue
    private lateinit var mView: View
    private lateinit var helperSessionViewModel: HelperSessionViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        viewModelProvider = ViewModelProvider(this)
        helperSessionViewModel = viewModelProvider.get(HelperSessionViewModel::class.java)
        val email = intent.getStringExtra("email")
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        queue = RequestQueue(cache, network).apply {
            start()
        }
        queue.add(JsonArrayRequest(Request.Method.GET,
            url + "login/" + email!!.replace('.', '%'),
            null, { array ->
                helperSessionViewModel.setHelperSession(
                    HelperSession(array.getBoolean(2), array.getBoolean(3))
                )
                helperSessionViewModel.helperSession.observe(this) { session ->
                    if (session.verified) {
                        setContentView(R.layout.activity_helper)
                        val helperToggleButton =
                            findViewById<ExtendedFloatingActionButton>(R.id.helper_toggle_help)
                        if(session.helping) helperToggleButton.backgroundTintList = green
                        else helperToggleButton.backgroundTintList = red
                        helperToggleButton.setOnClickListener {
                            if (!session.helping) {
                                queue.add(BooleanRequest(Request.Method.PUT,
                                    url + "help/" + email
                                        .replace('.', '%') + "/t", null,
                                    {
                                        helperSessionViewModel.setHelping(it)
                                    }, {
                                        it.printStackTrace()
                                    }))
                                helperToggleButton.backgroundTintList = green
                                helperToggleButton.setIconResource(R.drawable.ic_check)
                            } else {
                                queue.add(BooleanRequest(Request.Method.PUT,
                                    url + "help/" + email
                                        .replace('.', '%') + "/f", null,
                                    {
                                        helperSessionViewModel.setHelping(it)
                                    }, {
                                        it.printStackTrace()
                                    }))
                                helperToggleButton.backgroundTintList = red
                                helperToggleButton.setIconResource(R.drawable.ic_cross)
                            }
                        }
                        val createTutorialButton =
                            findViewById<Button>(R.id.helper_create_tutorial_button)
                        createTutorialButton.setOnClickListener {
                            startActivity(
                                Intent(
                                    this@HelperActivity,
                                    CreatorActivity::class.java
                                )
                            )
                        }
                        findViewById<Button>(R.id.helper_settings_button).setOnClickListener {

                        }
                        findViewById<Button>(R.id.helper_chat_button).setOnClickListener {

                        }
                    } else queue.add(BooleanRequest(Request.Method.GET,
                        url + "documentexists/" + email.replace('.', '%'),
                        null, {
                            setContentView(R.layout.activity_unverified_helper)
                            val verifyButton = findViewById<Button>(R.id.verify_button)
                            if(!it) {
                                mView = findViewById(R.id.unverified_view)
                                verifyButton.setOnClickListener {
                                    mView.visibility = View.GONE
                                    mImageBrowser = ImageBrowserLoader(this)
                                    supportFragmentManager.commit {
                                        add(R.id.fragment_overlay_layout, mImageBrowser)
                                    }
                                }
                            } else {
                                verifyButton.visibility = View.INVISIBLE
                                findViewById<TextView>(R.id.unverified_helper_info).text =
                                    resources
                                        .getString(R.string.unverified_helper_awaiting_response)

                            }
                        }, {
                            it.printStackTrace()
                        }))
                }
            }, {
                it.printStackTrace()
            }))
    }

    override fun onBackPressed()
    {
        startActivity(Intent(this@HelperActivity, MainActivity::class.java))
    }

    override fun imageSubmitted(uri: Uri)
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        findViewById<Button>(R.id.verify_button).text =
            resources.getString(R.string.verify_button_post_upload)
        val uploadButton = findViewById<Button>(R.id.document_upload_button)
        findViewById<ImageView>(R.id.document_display).setImageURI(uri)
        uploadButton.visibility = View.VISIBLE
        uploadButton.setOnClickListener {

        }
        mView.visibility = View.VISIBLE
    }

    override fun onDestroy()
    {
        queue.stop()
        super.onDestroy()
    }
}
