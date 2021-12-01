package com.orzechowski.saveme.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.orzechowski.saveme.HelperActivity
import com.orzechowski.saveme.R
import com.orzechowski.saveme.tutorial.database.Tutorial

class PeerReview(val mActivity: HelperActivity): Fragment(), PeerReviewAdapter.FragmentCallback
{
    private lateinit var mQueue: RequestQueue
    private val mCallback: ActivityCallback = mActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View?
    {
        return inflater.inflate(R.layout.fragment_peer_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        mQueue = RequestQueue(DiskBasedCache(mActivity.cacheDir, 1024*1024),
            BasicNetwork(HurlStack())).apply { start() }
        val url = getString(R.string.url)
        super.onViewCreated(view, savedInstanceState)
    }

    interface ActivityCallback
    {
        fun playTutorial()
    }

    override fun playTutorial(tutorial: Tutorial)
    {
        TODO("Not yet implemented")
    }

    override fun accept(tutorial: Tutorial)
    {
        TODO("Not yet implemented")
    }

    override fun reject(tutorial: Tutorial)
    {
        TODO("Not yet implemented")
    }
}
