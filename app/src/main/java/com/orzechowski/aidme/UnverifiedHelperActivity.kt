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
import com.orzechowski.aidme.volley.MultipartRequest
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException
import kotlin.math.min

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
            val twoHyphens = "--"
            val lineEnd = "\r\n"
            val boundary = "apiclient-" + System.currentTimeMillis()
            val mimeType = "multipart/form-data;boundary=$boundary"
            lateinit var body: ByteArray
            val byteArrayOutputStream = ByteArrayOutputStream()
            val dataOutputStream = DataOutputStream(byteArrayOutputStream)
            try {
                val bitmap = when { Build.VERSION.SDK_INT > 28 ->
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
                    else -> MediaStore.Images.Media.getBitmap(contentResolver, uri)
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                body = byteArrayOutputStream.toByteArray()
                build(dataOutputStream, body, mEmail.replace("xyz121", ".")
                    .replace("xyz122", "@") + ".jpeg")
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val request = MultipartRequest(mUrl+"userdocumentuploadimage/"+mEmail,
                mapOf("content-type" to "multipart/form-data;boundary=$boundary"), mimeType, body, {
                    Toast.makeText(this, "turkusowy", Toast.LENGTH_SHORT).show()
                }, {
                    it.printStackTrace()
                })
            mQueue.add(request)
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

    fun build(dataOutputStream: DataOutputStream, data: ByteArray, fileName: String)
    {
        val twoHyphens = "--"
        val lineEnd = "\r\n"
        val boundary = "apiclient-" + System.currentTimeMillis()
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd)
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\"; " +
                "filename=\"$fileName\"$lineEnd")
        dataOutputStream.writeBytes(lineEnd)
        val fileInputStream = ByteArrayInputStream(data)
        var bytesAvailable = fileInputStream.available()
        val maxBufferSize = 1048576
        var bufferSize = min(bytesAvailable, maxBufferSize)
        val buffer = ByteArray(bufferSize)
        var bytesRead = fileInputStream.read(buffer, 0, bufferSize)
        while(bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize)
            bytesAvailable = fileInputStream.available()
            bufferSize = min(bytesAvailable, maxBufferSize)
            bytesRead = fileInputStream.read(buffer, 0, bufferSize)
        }
        dataOutputStream.writeBytes(lineEnd)
    }
}
