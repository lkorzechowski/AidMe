package com.orzechowski.aidme.browser.results

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.VersionSoundViewModel
import com.orzechowski.aidme.tutorial.version.database.VersionInstructionViewModel
import com.orzechowski.aidme.tutorial.version.database.VersionViewModel
import java.io.File
import kotlin.concurrent.thread

class Requestor (val activity: FragmentActivity)
{
    private val viewModelProvider = ViewModelProvider(activity)
    private val versionViewModel = viewModelProvider.get(VersionViewModel::class.java)
    private val versionInstructionViewModel = viewModelProvider
        .get(VersionInstructionViewModel::class.java)
    private val versionMultimediaViewModel = viewModelProvider
        .get(VersionMultimediaViewModel::class.java)
    private val versionSoundViewModel = viewModelProvider.get(VersionSoundViewModel::class.java)
    private val tutorialSoundViewModel = viewModelProvider.get(TutorialSoundViewModel::class.java)
    private lateinit var queue: RequestQueue
    private lateinit var versionThread: Thread
    private lateinit var versionInstructionThread: Thread
    private lateinit var versionMultimediaThread: Thread
    private lateinit var versionSoundThread: Thread
    private lateinit var tutorialSoundThread: Thread

    fun end()
    {
        thread {
            Thread.sleep(2000)
            queue.stop()
            versionThread.interrupt()
            versionInstructionThread.interrupt()
            versionMultimediaThread.interrupt()
            versionSoundThread.interrupt()
            tutorialSoundThread.interrupt()
        }
    }

    fun requestData(cacheDir: File)
    {
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        queue = RequestQueue(cache, network).apply {
            start()
        }
    }

    //tutorialLinkThread = Thread {
    //            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutoriallinks",
    //                null, { array ->
    //                    tutorialLinkViewModel.all.observe(activity) {
    //                        val ids = mutableListOf<Long>()
    //                        for(lin in it) {
    //                            ids.add(lin.tutorialLinkId)
    //                        }
    //                        for(i in 0 until array.length()) {
    //                            val row: JSONObject = array.getJSONObject(i)
    //                            val tutorialLink = TutorialLink(
    //                                row.getLong("tutorialLinkId"),
    //                                row.getLong("tutorialId"), row.getLong("originId"),
    //                                row.getInt("instructionNumber")
    //                            )
    //                            if(ids.contains(tutorialLink.tutorialLinkId)) {
    //                                tutorialLinkViewModel.update(tutorialLink)
    //                            } else {
    //                                tutorialLinkViewModel.insert(tutorialLink)
    //                            }
    //                        }
    //                    }
    //                }, {
    //                    it.printStackTrace()
    //                }))
    //        }.also { it.start() }
}