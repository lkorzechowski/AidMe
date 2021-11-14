package com.orzechowski.saveme.tutorial

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.saveme.VersionActivity
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSetViewModel
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia
import com.orzechowski.saveme.tutorial.multimedia.database.MultimediaViewModel
import com.orzechowski.saveme.tutorial.sound.database.TutorialSoundViewModel
import org.json.JSONObject
import java.io.*
import kotlin.concurrent.thread

class RequestMedia(private val mActivity: VersionActivity, private val mTutorialId: Long)
{
    private val viewModelProvider = ViewModelProvider(mActivity)
    private val multimediaViewModel = viewModelProvider.get(MultimediaViewModel::class.java)
    private val instructionSetViewModel = viewModelProvider.get(InstructionSetViewModel::class.java)
    private val soundViewModel = viewModelProvider.get(TutorialSoundViewModel::class.java)
    private lateinit var queue: RequestQueue
    private lateinit var multimediaThread: Thread
    private lateinit var narrationThread: Thread
    private lateinit var soundThread: Thread
    private var multimediaResponse: Boolean = false

    fun end()
    {
        thread {
            queue.stop()
            multimediaThread.interrupt()
            narrationThread.interrupt()
            soundThread.interrupt()
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
                                row.getBoolean("loop"), row.getInt("position"))
                            if(!multimediaResponse) {
                                multimediaResponse = true
                                multimediaViewModel.getByMultimediaId(multimediaId)
                                    .observe(mActivity) {
                                        if (it != null) {
                                            multimediaViewModel.update(multimedia)
                                        } else {
                                            multimediaViewModel.insert(multimedia)
                                        }
                                        val file = File(dir + fileName)
                                        if (!file.exists()) {
                                            if (multimedia.type) {
                                                queue.add(
                                                    ImageRequest(url + "files/images/" +
                                                            fileName, { bitmap ->
                                                            try {
                                                                val output = FileOutputStream(file)
                                                                bitmap.compress(
                                                                    Bitmap.CompressFormat.JPEG,
                                                                    100, output
                                                                )
                                                                output.close()
                                                            } catch (e: FileNotFoundException) {
                                                                e.printStackTrace()
                                                            } catch (e: IOException) {
                                                                e.printStackTrace()
                                                            }
                                                        }, 1200, 900,
                                                        ImageView.ScaleType.FIT_CENTER,
                                                        Bitmap.Config.ARGB_8888,
                                                        { error ->
                                                            error.printStackTrace()
                                                        })
                                                )
                                            } else {
                                                queue.add(
                                                    FileRequest(
                                                        Request.Method.GET,
                                                        url + "files/vids/" + fileName,
                                                        { byteArray ->
                                                            val output = FileOutputStream(file)
                                                            output.write(byteArray)
                                                            output.close()
                                                        },
                                                        { error ->
                                                            error.printStackTrace()
                                                        },
                                                        null
                                                    )
                                                )
                                            }
                                        }
                                    }
                            }
                        }
                }, {
                    it.printStackTrace()
                }))
        }
        instructionSetViewModel.getByTutorialId(mTutorialId).observe(mActivity) { instructions ->
            narrationThread = thread {
                for (instruction in instructions) {
                    val file = File(dir + instruction.narrationFile)
                    if(!file.exists()) {
                        queue.add(FileRequest(Request.Method.GET, url + "files/narrations/" +
                                instruction.narrationFile, { byteArray ->
                            file.writeBytes(ByteArrayInputStream(byteArray).readBytes())
                        }, {
                            it.printStackTrace()
                        }, null))
                    }
                }
            }
        }
        soundViewModel.getByTutorialId(mTutorialId).observe(mActivity) { sounds ->
            soundThread = thread {
                for(sound in sounds) {
                    val file = File(dir + sound.fileName)
                    if(!file.exists()) {
                        queue.add(FileRequest(Request.Method.GET, url + "files/sounds/" +
                                sound.fileName, { byteArray ->
                            file.writeBytes(ByteArrayInputStream(byteArray).readBytes())
                        }, {
                            it.printStackTrace()
                        }, null))
                    }
                }
            }
        }
    }
}
