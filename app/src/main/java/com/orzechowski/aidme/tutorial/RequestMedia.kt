package com.orzechowski.aidme.tutorial

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.aidme.VersionActivity
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimedia
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimediaViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.VersionSound
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.VersionSoundViewModel
import org.json.JSONObject
import java.io.*
import kotlin.concurrent.thread

class RequestMedia (private val mActivity: VersionActivity, private val mTutorialId: Long)
{
    private val viewModelProvider = ViewModelProvider(mActivity)
    private val multimediaViewModel = viewModelProvider.get(MultimediaViewModel::class.java)
    private val instructionSetViewModel = viewModelProvider.get(InstructionSetViewModel::class.java)
    private val versionMultimediaViewModel =
        viewModelProvider.get(VersionMultimediaViewModel::class.java)
    private val versionSoundViewModel = viewModelProvider.get(VersionSoundViewModel::class.java)
        private lateinit var queue: RequestQueue
    private lateinit var multimediaThread: Thread
    private lateinit var versionMultimediaThread: Thread
    private lateinit var versionSoundThread: Thread

    fun end()
    {
        thread {
            Thread.sleep(5000)
            queue.stop()
            multimediaThread.interrupt()
            versionMultimediaThread.interrupt()
            versionSoundThread.interrupt()
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
        val dir = File(mActivity.filesDir.absolutePath).absolutePath + "/"
        multimediaThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "multimedia/" + mTutorialId,
                null, { array ->
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val fileName = row.getString("fileName")
                            val multimediaId = row.getLong("multimediaId")
                            val multimedia = Multimedia(multimediaId,
                                row.getLong("tutorialId"), row.getInt("displayTime"),
                                row.getBoolean("type"), fileName,
                                row.getBoolean("loop"), row.getInt("position")
                            )
                            multimediaViewModel.getByMultimediaId(multimediaId).observe(mActivity) {
                                if (it!=null) {
                                    multimediaViewModel.update(multimedia)
                                } else {
                                    multimediaViewModel.insert(multimedia)
                                }
                                if(multimedia.type) {
                                    queue.add(
                                        ImageRequest(url + "files/images/" + fileName, {
                                                bitmap ->
                                            try {
                                                val file = File(dir + fileName)
                                                if(file.exists()) {
                                                    throw IOException()
                                                } else {
                                                    val output = FileOutputStream(file)
                                                    bitmap.compress(Bitmap.CompressFormat.JPEG,
                                                        100, output)
                                                    output.close()
                                                }
                                            } catch (e: FileNotFoundException) {
                                                e.printStackTrace()
                                            } catch (e: IOException) {
                                                e. printStackTrace()
                                            }
                                        }, 420, 420,
                                            ImageView.ScaleType.FIT_CENTER,
                                            Bitmap.Config.ARGB_8888, { error ->
                                                error.printStackTrace()
                                            })
                                    )
                                } else {
                                    queue.add(FileRequest(Request.Method.GET,
                                        url + "files/vids/" + fileName,
                                        { byteArray ->
                                            val file = File(dir + fileName)
                                            file.writeBytes(ByteArrayInputStream(byteArray)
                                                .readBytes())
                                        },
                                        { error ->
                                            error.printStackTrace()
                                        }, null
                                    ))
                                }
                            }
                        }
                }, {
                    it.printStackTrace()
                }))
        }
        versionMultimediaThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "versionmultimedia/"
                    + mTutorialId, null, { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionMultimediaId = row.getLong("versionMultimediaId")
                        val versionMultimedia = VersionMultimedia(versionMultimediaId,
                            row.getLong("multimediaId"), row.getLong("versionId"))
                        versionMultimediaViewModel.getByVersionMultimediaId(versionMultimediaId)
                            .observe(mActivity) {
                                if(it!=null) {
                                    versionMultimediaViewModel.update(versionMultimedia)
                                } else {
                                    versionMultimediaViewModel.insert(versionMultimedia)
                                }
                            }
                    }
                }, {
                    it.printStackTrace()
                }))
        }
        versionSoundThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "versionsound/" + mTutorialId,
                null, { array ->
                      for(i in 0 until array.length()) {
                          val row: JSONObject = array.getJSONObject(i)
                          val versionSoundId = row.getLong("versionSoundId")
                          val versionSound = VersionSound(versionSoundId,
                              row.getLong("tutorialSoundId"), row.getLong("versionId"))
                          versionSoundViewModel.getByVersionSoundId(versionSoundId)
                              .observe(mActivity) {
                                  if(it!=null) {
                                      versionSoundViewModel.update(versionSound)
                                  } else {
                                      versionSoundViewModel.insert(versionSound)
                                  }
                              }
                      }
                }, {
                    it.printStackTrace()
                }))
        }
        instructionSetViewModel.getByTutorialId(mTutorialId).observe(mActivity) { instructions ->
            for(instruction in instructions) {
                queue.add(FileRequest(Request.Method.GET, url + "files/narrations/" +
                        instruction.narrationFile, { byteArray ->
                        val file = File(dir + instruction.narrationFile)
                        file.writeBytes(ByteArrayInputStream(byteArray).readBytes())
                    }, { error ->
                        error.printStackTrace()
                       }, null
                ))
            }
        }
    }
}