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
import org.json.JSONObject
import java.io.*
import kotlin.concurrent.thread

class RequestMedia (private val mActivity: VersionActivity, private val mTutorialId: Long)
{
    private val viewModelProvider = ViewModelProvider(mActivity)
    private val multimediaViewModel = viewModelProvider.get(MultimediaViewModel::class.java)
    private val instructionSetViewModel = viewModelProvider.get(InstructionSetViewModel::class.java)
    private lateinit var queue: RequestQueue
    private lateinit var multimediaThread: Thread
    private lateinit var narrationThread: Thread

    fun end()
    {
        thread {
            Thread.sleep(5000)
            queue.stop()
            multimediaThread.interrupt()
            narrationThread.interrupt()
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
                null, {
                        array ->
                    multimediaViewModel.getByTutorialId(mTutorialId).observe(mActivity)
                    {
                        val ids = mutableListOf<Long>()
                        for(med in it) {
                            ids.add(med.multimediaId)
                        }
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val fileName = row.getString("fileName")
                            val multimedia = Multimedia(row.getLong("multimediaId"),
                                row.getLong("tutorialId"), row.getInt("displaytTime"),
                                row.getBoolean("type"), fileName,
                                row.getBoolean("loop"), row.getInt("position")
                            )
                            if(ids.contains(multimedia.multimediaId)) {
                                multimediaViewModel.update(multimedia)
                            } else {
                                multimediaViewModel.insert(multimedia)
                            }
                            if(multimedia.type) {
                                queue.add(
                                    ImageRequest(url + "files/images/" + fileName, { bitmap ->
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
                                    }, 420, 420, ImageView.ScaleType.FIT_CENTER,
                                        Bitmap.Config.ARGB_8888, { error -> error.printStackTrace()
                                        })
                                )
                            } else {
                                queue.add(FileRequest(Request.Method.GET,
                                    url + "files/vids/" + fileName,
                                    { byteArray ->
                                        try {
                                            val file = File(dir + fileName)
                                            if(file.exists()) {
                                                throw IOException()
                                            } else {
                                                val inputStream = ByteArrayInputStream(byteArray)
                                                val outputStream =
                                                    BufferedOutputStream(FileOutputStream(file))
                                                val data = ByteArray(1024)
                                                var total: Long = 0
                                                var count = 0
                                                while(count != -1) {
                                                    count = inputStream.read(data)
                                                    total += count
                                                    outputStream.write(data, 0, count)
                                                }
                                                outputStream.flush()
                                                outputStream.close()
                                                inputStream.close()
                                            }
                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        }
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
        narrationThread = thread {
            instructionSetViewModel.all.observe(mActivity) { instructions ->
                for(instruction in instructions) {
                    queue.add(FileRequest(Request.Method.GET,
                        url + "files/narrations/" + instruction.narrationFile,
                        { byteArray ->
                            try {
                                val file = File(dir + instruction.narrationFile)
                                if(file.exists()) {
                                    throw IOException()
                                } else {
                                    val inputStream = ByteArrayInputStream(byteArray)
                                    val outputStream = BufferedOutputStream(FileOutputStream(file))
                                    val data = ByteArray(1024)
                                    var total: Long = 0
                                    var count = 0
                                    while(count != -1) {
                                        count = inputStream.read(data)
                                        total += count
                                        outputStream.write(data, 0, count)
                                    }
                                    outputStream.flush()
                                    outputStream.close()
                                    inputStream.close()
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }, { error ->
                           error.printStackTrace()
                        }, null
                    ))
                }
            }
        }
    }
}