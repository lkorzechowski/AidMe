package com.orzechowski.saveme.browser.results

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.orzechowski.saveme.volley.StringPost
import java.io.File

class RequestLiveAid(private val mCallback: ActivityCallback, private val mUrl: String)
{
    private lateinit var mQueue: RequestQueue
    private lateinit var mId: String

    fun getRequest(cacheDir: File, tagId: Long, id: String)
    {
        mId = id
        mQueue = RequestQueue(DiskBasedCache(cacheDir, 1024 * 1024),
            BasicNetwork(HurlStack())).apply { start() }
        mQueue.add(JsonObjectRequest(Request.Method.GET, mUrl + "number/" + tagId + "/" + mId,
            null, {
            val helper = HelperFull(it.getInt("phone"),
                it.getString("title") + " " + it.getString("name") + " " +
                        it.getString("surname") + ", " + it.getString("profession"))
                mCallback.suggestHelper(helper, tagId)
        }, null))
    }

    fun postRequest(number: Int)
    {
        mQueue.add(StringPost(Request.Method.POST, mUrl + "switchoccupied/" + number + "/" +
                mId, null, null))
    }

    interface ActivityCallback
    {
        fun suggestHelper(helper: HelperFull, tagId: Long)
    }
}
