package com.orzechowski.aidme

import android.content.Intent
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
import com.orzechowski.aidme.helper.BooleanRequest
import com.orzechowski.aidme.imagebrowser.ImageBrowserLoader

class UnverifiedHelperActivity : AppCompatActivity(), ImageBrowserLoader.ActivityCallback
{
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var queue: RequestQueue
    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unverified_helper)
        mView = findViewById(R.id.unverified_view)
        viewModelProvider = ViewModelProvider(this)
        val email = intent.getStringExtra("email")
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        queue = RequestQueue(cache, network).apply {
            start()
        }
        queue.add(BooleanRequest(Request.Method.GET, url + "documentexists/" + email,
            null, {
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
                        resources.getString(R.string.unverified_helper_awaiting_response)
                }
            }, {
                it.printStackTrace()
            })
        )
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

    override fun onBackPressed()
    {
        startActivity(Intent(this@UnverifiedHelperActivity, MainActivity::class.java))
    }

    override fun onDestroy()
    {
        queue.stop()
        super.onDestroy()
    }
}
