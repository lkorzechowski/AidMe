package com.orzechowski.aidme.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.orzechowski.aidme.HelperActivity
import com.orzechowski.aidme.R

class HelperSettings(val mActivity: HelperActivity): Fragment()
{
    private lateinit var mQueue: RequestQueue

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View
    {
        val view = R.layout.fragment_helper_settings
        val email = bundle!!.getString("email")
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(mActivity.cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        mQueue = RequestQueue(cache, network).apply {
            start()
        }
        mQueue.add(JsonObjectRequest(Request.Method.GET, url +
                "fullhelperdetailforemail/"+email, null, {

        }, {
            it.printStackTrace()
        }))
        return inflater.inflate(view, container, false)
    }

    override fun onDestroy()
    {
        mQueue.stop()
        super.onDestroy()
    }
}