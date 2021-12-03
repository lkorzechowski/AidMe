package com.orzechowski.saveme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.saveme.imagebrowser.ImageBrowserLoader
import com.orzechowski.saveme.settings.database.PreferenceViewModel
import com.orzechowski.saveme.volley.StringPost
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import java.util.*
import kotlin.concurrent.thread

class UnverifiedHelperActivity : AppCompatActivity(R.layout.activity_unverified_helper),
    ImageBrowserLoader.ActivityCallback
{
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var mQueue: RequestQueue
    private lateinit var mViewModelProvider: ViewModelProvider
    private lateinit var mVerifyButton: Button
    private lateinit var mView: View
    private lateinit var mEmail: String
    private val mUrl = getString(R.string.url)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        UploadServiceConfig.initialize(application,
            getString(R.string.default_notification_channel_id), true)
        mView = findViewById(R.id.unverified_view)
        mViewModelProvider = ViewModelProvider(this)
        mEmail = intent.getStringExtra("email")!!
        mQueue = RequestQueue(DiskBasedCache(cacheDir, 1024 * 1024),
            BasicNetwork(HurlStack())).apply { start() }
        mVerifyButton = findViewById(R.id.verify_button)
        mQueue.add(StringRequest(Request.Method.GET, mUrl + "documentexists/" + mEmail,
            {
                if(it == "ok") {
                    findViewById<TextView>(R.id.unverified_helper_info).text =
                        resources.getString(R.string.unverified_helper_awaiting_response)
                } else {
                    Toast.makeText(this, R.string.document_not_found,
                        Toast.LENGTH_SHORT).show()
                }
            }, {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }))
        mView = findViewById(R.id.unverified_view)
        mVerifyButton.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 121)
            } else {
                mView.visibility = View.GONE
                mImageBrowser = ImageBrowserLoader(this)
                supportFragmentManager.commit {
                    add(R.id.fragment_overlay_layout, mImageBrowser)
                }
            }
        }
    }

    override fun imageSubmitted(uri: Uri)
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        findViewById<ImageView>(R.id.document_display).setImageURI(uri)
        mVerifyButton.text = resources.getString(R.string.verify_button_post_upload)
        val descriptionEdit = findViewById<EditText>(R.id.document_description_input)
        val descriptionInfo = findViewById<TextView>(R.id.document_description_info)
        val uploadButton = findViewById<Button>(R.id.document_upload_button)
        uploadButton.visibility = View.VISIBLE
        descriptionEdit.visibility = View.VISIBLE
        descriptionInfo.visibility = View.VISIBLE
        uploadButton.setOnClickListener {
            try {
                thread {
                    MultipartUploadRequest(this@UnverifiedHelperActivity, mUrl +
                                "userdocumentuploadimage/" + mEmail).addFileToUpload(uri.toString(),
                        "file").setMaxRetries(10).setUsesFixedLengthStreamingMode(true)
                        .setMethod("POST").setNotificationConfig { context, uploadId ->
                            UploadServiceConfig.notificationConfigFactory(context, uploadId)
                        }.startUpload()
                }
                val request = StringPost(Request.Method.POST, mUrl +
                        "userdocumentuploadinfo/" + mEmail, {
                    uploadButton.visibility = View.INVISIBLE
                    mVerifyButton.visibility = View.INVISIBLE
                    descriptionEdit.visibility = View.INVISIBLE
                    descriptionInfo.visibility = View.INVISIBLE
                    findViewById<TextView>(R.id.unverified_helper_info)
                        .setText(R.string.document_sent)
                }, {
                    val output = if(!it.message.isNullOrEmpty()) it.message!!
                    else resources.getString(R.string.document_sending_error)
                    Toast.makeText(this, output, Toast.LENGTH_SHORT).show()
                }).also {
                    val text = descriptionEdit.text.toString()
                    if(text.isNotEmpty()) it.setRequestBody(text)
                    else it.setRequestBody(" ")
                }
                mQueue.add(request)
            } catch(e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        mView.visibility = View.VISIBLE
    }

    override fun refreshGallery()
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        mImageBrowser = ImageBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mImageBrowser)
        }
    }

    override fun onBackPressed()
    {
        startActivity(Intent(this@UnverifiedHelperActivity, MainActivity::class.java))
    }

    override fun onDestroy()
    {
        mQueue.stop()
        super.onDestroy()
    }

    override fun onResume()
    {
        PreferenceViewModel(application).get().observe(this, {
            if(it.motive) setTheme(R.style.ColorBlind)
        })
        super.onResume()
    }
}
