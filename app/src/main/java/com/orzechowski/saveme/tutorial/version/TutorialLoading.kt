package com.orzechowski.saveme.tutorial.version

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.orzechowski.saveme.R
import com.orzechowski.saveme.VersionActivity
import kotlin.concurrent.thread

class TutorialLoading(private val mActivity: VersionActivity,
                      private val mCallback: ActivityCallback) : Fragment()
{
    lateinit var mProgressThread: Thread

    override fun
            onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View
    {
        return inflater.inflate(R.layout.fragment_tutorial_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        val skipButton = view.findViewById<Button>(R.id.tutorial_loading_skip_button)
        val progressOne = view.findViewById<View>(R.id.tutorial_loading_progress_1)
        val progressTwo = view.findViewById<View>(R.id.tutorial_loading_progress_2)
        val progressThree = view.findViewById<View>(R.id.tutorial_loading_progress_3)
        mProgressThread = thread {
            var timeout = 0
            try {
                while(timeout < 6) {
                    Thread.sleep(500)
                    mActivity.runOnUiThread {
                        progressOne.visibility = View.VISIBLE
                    }
                    Thread.sleep(500)
                    mActivity.runOnUiThread {
                        progressTwo.visibility = View.VISIBLE
                    }
                    Thread.sleep(500)
                    mActivity.runOnUiThread {
                        progressThree.visibility = View.VISIBLE
                    }
                    Thread.sleep(500)
                    mActivity.runOnUiThread {
                        progressOne.visibility = View.INVISIBLE
                        progressTwo.visibility = View.INVISIBLE
                        progressThree.visibility = View.INVISIBLE
                    }
                    timeout++
                }
                mCallback.callTutorial()
            } catch (ignored: InterruptedException) {}
        }
        skipButton.setOnClickListener {
            mProgressThread.interrupt()
            mCallback.callTutorial()
        }
    }

    interface ActivityCallback
    {
        fun callTutorial()
    }
}