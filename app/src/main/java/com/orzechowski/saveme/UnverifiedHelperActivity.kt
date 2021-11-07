package com.orzechowski.saveme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.orzechowski.saveme.imagebrowser.ImageBrowserLoader
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import java.util.*

//Aktywność do której skierowani są użytkownicy którzy zalogowali się do aplikacji przez swoje konto
//Google, ale którzy nie zostali zweryfikowani po stronie serwera, bądź odebrano im weryfikację.
//Aktywność ta nie posiada fragmentów ani podlegających jej klas, ale korzysta z przeglądarki zdjęć
//z pamięci telefonu, znajdującej się w com.orzechowski.saveme.imagebrowser oraz z konfiguracji
// kanału powiadomień mieszczącej się w com.orzechowski.saveme.volley.
class UnverifiedHelperActivity : AppCompatActivity(R.layout.activity_unverified_helper),
    ImageBrowserLoader.ActivityCallback
{
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var mQueue: RequestQueue
    private lateinit var viewModelProvider: ViewModelProvider
    private lateinit var mView: View
    private lateinit var mEmail: String
    private val mUrl = "https://aidme-326515.appspot.com/"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        mView = findViewById(R.id.unverified_view)
        viewModelProvider = ViewModelProvider(this)
        mEmail = intent.getStringExtra("email")!!
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        mQueue = RequestQueue(cache, network).apply {
            start()
        }
        val verifyButton = findViewById<Button>(R.id.verify_button)
        mQueue.add(
            JsonObjectRequest(Request.Method.GET, mUrl + "documentexists/" + mEmail,
                null, {
                    verifyButton.visibility = View.INVISIBLE
                    findViewById<TextView>(R.id.unverified_helper_info).text =
                        resources.getString(R.string.unverified_helper_awaiting_response)
            },
            {
                mView = findViewById(R.id.unverified_view)
                verifyButton.setOnClickListener {
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission
                            .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this,
                                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                    121)
                    } else {
                            mView.visibility = View.GONE
                                    mImageBrowser = ImageBrowserLoader(this)
                                    supportFragmentManager.commit {
                                add(R.id.fragment_overlay_layout, mImageBrowser)
                            }
                    }
                }
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
            UploadServiceConfig.initialize(application,
                getString(R.string.default_notification_channel_id), false)
            try {
                MultipartUploadRequest(this@UnverifiedHelperActivity,
                    mUrl + "userdocumentuploadimage/" + mEmail)
                    .addFileToUpload(uri.toString(), "file").setMaxRetries(10)
                    .setNotificationConfig { context, uploadId ->
                        UploadServiceConfig.notificationConfigFactory(context, uploadId)
                    }.startUpload()
            } catch(e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        mView.visibility = View.VISIBLE
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
}
