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
import com.orzechowski.saveme.volley.FileRequest
import org.json.JSONObject
import java.io.*
import kotlin.concurrent.thread

class RequestMedia(private val mActivity: VersionActivity, private val mTutorialId: Long)
{
    private val mViewModelProvider = ViewModelProvider(mActivity)
    private val mMultimediaViewModel = mViewModelProvider.get(MultimediaViewModel::class.java)
    private val mInstructionSetViewModel = mViewModelProvider
        .get(InstructionSetViewModel::class.java)
    private val mSoundViewModel = mViewModelProvider.get(TutorialSoundViewModel::class.java)
    private lateinit var mQueue: RequestQueue
    private lateinit var mMultimediaThread: Thread
    private lateinit var mNarrationThread: Thread
    private lateinit var mSoundThread: Thread
    private var mMultimediaResponse: Boolean = false

    fun end()
    {
        thread {
            mQueue.stop()
            mMultimediaThread.interrupt()
            mNarrationThread.interrupt()
            mSoundThread.interrupt()
        }
    }

    fun requestData(cacheDir: File, url: String)
    {
        mQueue = RequestQueue(DiskBasedCache(cacheDir, 1024 * 1024),
            BasicNetwork(HurlStack())).apply { start() }
        val dir = File(mActivity.filesDir.absolutePath).absolutePath + "/"
        mMultimediaThread = thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "multimedia/" + mTutorialId,
                null, { array ->
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val fileName = row.getString("fileName")
                            val multimediaId = row.getLong("multimediaId")
                            val multimedia = Multimedia(multimediaId,
                                row.getLong("tutorialId"), row.getInt("displayTime"),
                                row.getBoolean("type"), fileName,
                                row.getBoolean("loop"), row.getInt("position"))
                            if(!mMultimediaResponse) {
                                mMultimediaResponse = true
                                mMultimediaViewModel.getByMultimediaId(multimediaId)
                                    .observe(mActivity) {
                                        if (it != null) {
                                            mMultimediaViewModel.update(multimedia)
                                        } else {
                                            mMultimediaViewModel.insert(multimedia)
                                        }
                                        val file = File(dir + fileName)
                                        if (!file.exists()) {
                                            if (multimedia.type) {
                                                mQueue.add(
                                                    ImageRequest(url + "files/images/" +
                                                            fileName, { bitmap ->
                                                            try {
                                                                val output = FileOutputStream(file)
                                                                bitmap.compress(
                                                                    Bitmap.CompressFormat.JPEG,
                                                                    100, output
                                                                )
                                                                output.close()
                                                            } catch (e: FileNotFoundException) {}
                                                            catch (e: IOException) {}
                                                        }, 1200, 900,
                                                        ImageView.ScaleType.FIT_CENTER,
                                                        Bitmap.Config.ARGB_8888, null)
                                                )
                                            } else {
                                                mQueue.add(
                                                    FileRequest(
                                                        Request.Method.GET,
                                                        url + "files/vids/" + fileName,
                                                        { byteArray ->
                                                            val output = FileOutputStream(file)
                                                            output.write(byteArray)
                                                            output.close()
                                                        }, null, null
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
        mInstructionSetViewModel.getByTutorialId(mTutorialId).observe(mActivity) { instructions ->
            mNarrationThread = thread {
                for (instruction in instructions) {
                    val file = File(dir + instruction.narrationFile)
                    if(!file.exists()) {
                        mQueue.add(
                            FileRequest(Request.Method.GET,
                            url + "files/narrations/" +
                                    instruction.narrationFile,
                            { byteArray ->
                                file.writeBytes(ByteArrayInputStream(byteArray).readBytes())
                            },
                            null,
                            null
                        )
                        )
                    }
                }
            }
        }
        mSoundViewModel.getByTutorialId(mTutorialId).observe(mActivity) { sounds ->
            mSoundThread = thread {
                for(sound in sounds) {
                    val file = File(dir + sound.fileName)
                    if(!file.exists()) {
                        mQueue.add(
                            FileRequest(Request.Method.GET,
                            url + "files/sounds/" +
                                    sound.fileName,
                            { byteArray ->
                                file.writeBytes(ByteArrayInputStream(byteArray).readBytes())
                            },
                            null,
                            null
                        )
                        )
                    }
                }
            }
        }
    }
}
