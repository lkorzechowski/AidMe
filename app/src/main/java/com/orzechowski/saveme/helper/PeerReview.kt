package com.orzechowski.saveme.helper

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.saveme.HelperActivity
import com.orzechowski.saveme.R
import com.orzechowski.saveme.tutorial.database.Tutorial
import com.orzechowski.saveme.tutorial.database.TutorialViewModel
import com.orzechowski.saveme.volley.StringPost
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

class PeerReview(val mActivity: HelperActivity): Fragment(), PeerReviewAdapter.FragmentCallback
{
    private lateinit var mQueue: RequestQueue
    private lateinit var mEmail: String
    private lateinit var mUrl: String
    private lateinit var mTutorialViewModel: TutorialViewModel
    private lateinit var mPeerReviewAdapter: PeerReviewAdapter
    private val mCallback: ActivityCallback = mActivity
    private val mTutorials = mutableListOf<Tutorial>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?):
            View?
    {
        mEmail = requireArguments().getString("email") ?: " "
        mUrl = getString(R.string.url)
        mTutorialViewModel = ViewModelProvider(mActivity).get(TutorialViewModel::class.java)
        mPeerReviewAdapter = PeerReviewAdapter(mActivity, this)
        return inflater.inflate(R.layout.fragment_peer_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val recycler: RecyclerView = view.findViewById(R.id.peer_review_tutorial_rv)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
            false)
        recycler.adapter = mPeerReviewAdapter
        val imageDir = File(mActivity.filesDir.absolutePath).absolutePath + "/"
        mQueue = RequestQueue(DiskBasedCache(mActivity.cacheDir, 1024 * 1024),
            BasicNetwork(HurlStack())).apply { start() }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, mUrl + "unverifiedtutorials/" +
                    mEmail, null, { array ->
                        for (i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val miniatureName = row.getString("miniatureName")
                            val tutorialId = row.getLong("tutorialId")
                            val tutorial = Tutorial(tutorialId, row.getString("tutorialName"),
                                row.getLong("authorId"), miniatureName,
                                row.getDouble("rating").toFloat()
                            )
                            mTutorials.add(tutorial)
                            mTutorialViewModel.getByTutorialId(tutorialId).observe(mActivity)
                            {
                                if (it != null) {
                                    mTutorialViewModel.update(tutorial)
                                } else {
                                    mTutorialViewModel.insert(tutorial)
                                }
                            }
                            mQueue.add(
                                ImageRequest(mUrl + "files/images/" + miniatureName, { bitmap ->
                                        try {
                                            val file = File(imageDir + miniatureName)
                                            if (file.exists()) {
                                                throw IOException()
                                            } else {
                                                val output = FileOutputStream(file)
                                                bitmap.compress(Bitmap.CompressFormat.JPEG,
                                                    100, output)
                                                output.close()
                                            }
                                        }
                                        catch (e: FileNotFoundException) {}
                                        catch (e: IOException) {}
                                        mPeerReviewAdapter.setElementList(mTutorials)
                                    }, 160, 160, ImageView.ScaleType.FIT_CENTER,
                                    Bitmap.Config.ARGB_8888, null
                                )
                            )
                        }
                    }, null
                )
            )
        }
        super.onViewCreated(view, savedInstanceState)
    }

    interface ActivityCallback
    {
        fun playTutorial(tutorial: Tutorial)
    }

    override fun playTutorial(tutorial: Tutorial)
    {
        mCallback.playTutorial(tutorial)
    }

    override fun accept(tutorial: Tutorial)
    {
        mQueue.add(StringPost(Request.Method.POST, mUrl + "approval/" + mEmail + "/t", {},
            {
                if(it.message != null) {
                    Toast.makeText(mActivity, it.message, Toast.LENGTH_SHORT).show()
                }
        }))
        mTutorialViewModel.delete(tutorial)
        mTutorials.remove(tutorial)
        mPeerReviewAdapter.setElementList(mTutorials)
    }

    override fun reject(tutorial: Tutorial)
    {
        mQueue.add(StringPost(Request.Method.POST, mUrl + "approval/" + mEmail + "/f", {},
            {
                if(it.message != null) {
                    Toast.makeText(mActivity, it.message, Toast.LENGTH_SHORT).show()
                }
        }))
        mTutorialViewModel.delete(tutorial)
        mTutorials.remove(tutorial)
        mPeerReviewAdapter.setElementList(mTutorials)
    }
}
