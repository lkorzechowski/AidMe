package com.orzechowski.aidme

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.orzechowski.aidme.imagebrowser.ImageBrowserLoader
import com.orzechowski.aidme.volley.DataPart
import com.orzechowski.aidme.volley.VolleyMultipartRequest
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

class UnverifiedHelperActivity : AppCompatActivity(), ImageBrowserLoader.ActivityCallback
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
        setContentView(R.layout.activity_unverified_helper)
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
            lateinit var byteArray: ByteArray
            try {
                val bitmap = when { Build.VERSION.SDK_INT > 28 ->
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
                    else -> MediaStore.Images.Media.getBitmap(contentResolver, uri)
                }
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(byteArray.isNotEmpty()) {
                val volleyMultipartRequest = VolleyMultipartRequest(Request.Method.GET, mUrl +
                        "userdocumentuploadimage", {
                    try {
                        Toast.makeText(applicationContext, JSONObject(String(it.data))
                            .getString("message"), Toast.LENGTH_SHORT).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, {
                    it.printStackTrace()
                })
                volleyMultipartRequest.setData(DataPart(mEmail
                    .replace("xyz121", ".").replace("xyz122", "@")
                        + ".jpeg", byteArray, "binary"))
                mQueue.add(volleyMultipartRequest)
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
