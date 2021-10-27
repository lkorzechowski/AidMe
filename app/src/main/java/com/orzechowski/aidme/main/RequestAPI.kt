package com.orzechowski.aidme.main

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.aidme.MainActivity
import com.orzechowski.aidme.browser.categories.database.Category
import com.orzechowski.aidme.browser.categories.database.CategoryViewModel
import com.orzechowski.aidme.browser.search.database.Keyword
import com.orzechowski.aidme.browser.search.database.KeywordViewModel
import com.orzechowski.aidme.browser.search.database.TagKeyword
import com.orzechowski.aidme.browser.search.database.TagKeywordViewModel
import com.orzechowski.aidme.database.helper.Helper
import com.orzechowski.aidme.database.helper.HelperViewModel
import com.orzechowski.aidme.database.tag.*
import com.orzechowski.aidme.tutorial.database.Tutorial
import com.orzechowski.aidme.tutorial.database.TutorialLink
import com.orzechowski.aidme.tutorial.database.TutorialLinkViewModel
import com.orzechowski.aidme.tutorial.database.TutorialViewModel
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetViewModel
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundViewModel
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

class RequestAPI (val activity: MainActivity)
{
    private val viewModelProvider = ViewModelProvider(activity)
    private val tutorialViewModel = viewModelProvider.get(TutorialViewModel::class.java)
    private val categoryViewModel = viewModelProvider.get(CategoryViewModel::class.java)
    private val tagViewModel = viewModelProvider.get(TagViewModel::class.java)
    private val keywordViewModel = viewModelProvider.get(KeywordViewModel::class.java)
    private val helperViewModel = viewModelProvider.get(HelperViewModel::class.java)
    private val helperTagViewModel = viewModelProvider.get(HelperTagViewModel::class.java)
    private val tutorialTagViewModel = viewModelProvider.get(TutorialTagViewModel::class.java)
    private val tagKeywordViewModel = viewModelProvider.get(TagKeywordViewModel::class.java)
    private val categoryTagViewModel = viewModelProvider.get(CategoryTagViewModel::class.java)
    private val instructionSetViewModel = viewModelProvider.get(InstructionSetViewModel::class.java)
    private val tutorialLinkViewModel = viewModelProvider.get(TutorialLinkViewModel::class.java)
    private val tutorialSoundViewModel = viewModelProvider.get(TutorialSoundViewModel::class.java)
    private lateinit var queue: RequestQueue
    private lateinit var tutorialThread: Thread
    private lateinit var categoryThread: Thread
    private lateinit var tagThread: Thread
    private lateinit var keywordThread: Thread
    private lateinit var helperThread: Thread
    private lateinit var helperTagThread: Thread
    private lateinit var tutorialTagThread: Thread
    private lateinit var tagKeywordThread: Thread
    private lateinit var categoryTagThread: Thread
    private lateinit var instructionThread: Thread
    private lateinit var tutorialLinkThread: Thread
    private lateinit var tutorialSoundThread: Thread

    fun end()
    {
        thread {
            Thread.sleep(5000)
            queue.stop()
            tutorialThread.interrupt()
            categoryThread.interrupt()
            tagThread.interrupt()
            keywordThread.interrupt()
            helperThread.interrupt()
            helperTagThread.interrupt()
            tagKeywordThread.interrupt()
            categoryTagThread.interrupt()
            tutorialLinkThread.interrupt()
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
        val imageDir = File(activity.filesDir.absolutePath).absolutePath + "/"
        tutorialThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorials", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val miniatureName = row.getString("miniatureName")
                    val tutorialId = row.getLong("tutorialId")
                    val tutorial = Tutorial(tutorialId, row.getString("tutorialName"),
                        row.getLong("authorId"), miniatureName,
                        row.getDouble("rating").toFloat()
                    )
                    tutorialViewModel.getByTutorialId(tutorialId).observe(activity) {
                        if(it!=null && !it.equals(tutorial)) {
                            tutorialViewModel.update(tutorial)
                        } else {
                            tutorialViewModel.insert(tutorial)
                        }
                    }
                    queue.add(ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
                        try {
                            val file = File(imageDir + miniatureName)
                            if(file.exists()) {
                                throw IOException()
                            } else {
                                val output = FileOutputStream(file)
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                                output.close()
                            }
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }}, 160, 160, ImageView.ScaleType.FIT_CENTER,
                        Bitmap.Config.ARGB_8888, { error ->
                            error.printStackTrace()
                        })
                    )
                }
            }, {
                it.printStackTrace()
            }))
        }
        categoryThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categories", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val miniatureName = row.getString("miniatureName")
                        val categoryId = row.getLong("categoryId")
                        val category = Category(categoryId,
                            row.getString("categoryName"),
                            row.getBoolean("hasSubcategories"), miniatureName,
                            row.getInt("categoryLevel")
                        )
                        categoryViewModel.getByCategoryId(categoryId).observe(activity) {
                            if(it!=null && !it.equals(category)) {
                                categoryViewModel.update(category)
                            } else {
                                categoryViewModel.insert(category)
                            }
                        }
                        queue.add(
                            ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
                                try {
                                    val file = File(imageDir + miniatureName)
                                    if (file.exists()) {
                                        throw IOException()
                                    } else {
                                        val output = FileOutputStream(file)
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                            output)
                                        output.close()
                                    }
                                } catch (e: FileNotFoundException) {
                                    e.printStackTrace()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }, 160, 160, ImageView.ScaleType.FIT_CENTER,
                                Bitmap.Config.ARGB_8888, { error ->
                                    error.printStackTrace()
                                })
                        )
                    }
            }, {
                it.printStackTrace()
            }))
        }
        tagThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tags", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagId = row.getLong("tagId")
                        val tag = Tag(tagId, row.getString("tagName"),
                            row.getInt("tagLevel")
                        )
                        tagViewModel.getByTagId(tagId).observe(activity) {
                            if(it!=null && !it.equals(tag)) {
                                tagViewModel.update(tag)
                            } else {
                                tagViewModel.insert(tag)
                            }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        keywordThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "keywords", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val keywordId = row.getLong("keywordId")
                        val keyword = Keyword(keywordId, row.getString("word"))
                        keywordViewModel.getByKeywordId(keywordId).observe(activity) {
                            if(it!=null && !it.equals(keyword)) {
                                keywordViewModel.update(keyword)
                            } else {
                                keywordViewModel.insert(keyword)
                            }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        helperThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helperlist", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helperId = row.getLong("helperId")
                        val helper = Helper(helperId, row.getString("name"),
                            row.getString("surname"), row.getString("title"),
                            row.getString("profession")
                        )
                        helperViewModel.getByHelperId(helperId).observe(activity) {
                            if(it!=null && !it.equals(helper)) {
                                helperViewModel.update(helper)
                            } else {
                                helperViewModel.insert(helper)
                            }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        helperTagThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helpertags", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helperTagId = row.getLong("helperTagId")
                        val helperTag = HelperTag(helperTagId,
                            row.getLong("helperId"), row.getLong("tagId")
                        )
                        helperTagViewModel.getByHelperTagId(helperTagId).observe(activity) {
                            if(it!=null && !it.equals(helperTag)) {
                                helperTagViewModel.update(helperTag)
                            } else {
                                helperTagViewModel.insert(helperTag)
                           }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        tutorialTagThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialtags", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tutorialTagId = row.getLong("tutorialTagId")
                        val tutorialTag = TutorialTag(tutorialTagId,
                            row.getLong("tutorialId"), row.getLong("tagId")
                        )
                        tutorialTagViewModel.getByTutorialTagId(tutorialTagId).observe(activity) {
                            if (it!=null && !it.equals(tutorialTag)) {
                                tutorialTagViewModel.update(tutorialTag)
                            } else {
                                tutorialTagViewModel.insert(tutorialTag)
                            }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        tagKeywordThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tagkeywords", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagKeywordId = row.getLong("tagKeywordId")
                        val tagKeyword = TagKeyword(tagKeywordId,
                            row.getLong("keywordId"), row.getLong("tagId")
                        )
                        tagKeywordViewModel.getByTagKeywordId(tagKeywordId).observe(activity) {
                            if(it!=null && !it.equals(tagKeyword)) {
                                tagKeywordViewModel.update(tagKeyword)
                            } else {
                                tagKeywordViewModel.insert(tagKeyword)
                            }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        categoryTagThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categorytags", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val categoryTagId = row.getLong("categoryTagId")
                        val categoryTag = CategoryTag(categoryTagId,
                            row.getLong("categoryId"), row.getLong("tagId")
                        )
                        categoryTagViewModel.getByCategoryTagId(categoryTagId).observe(activity) {
                            if(it!=null && !it.equals(categoryTag)) {
                                categoryTagViewModel.update(categoryTag)
                            } else {
                                categoryTagViewModel.insert(categoryTag)
                            }
                        }
                    }
            }, {
                it.printStackTrace()
            }))
        }
        instructionThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "instructions",
                null, { array ->
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val instructionSetId = row.getLong("instructionSetId")
                            val instructionSet = InstructionSet(instructionSetId,
                                row.getString("title"), row.getString("instructions"),
                                row.getInt("time"), row.getLong("tutorialId"),
                                row.getInt("position"), row.getString("narrationName")
                            )
                            instructionSetViewModel.getByInstructionSetId(instructionSetId)
                                .observe(activity) {
                                    if(it!=null && !it.equals(instructionSet)) {
                                        instructionSetViewModel.update(instructionSet)
                                    } else {
                                        instructionSetViewModel.insert(instructionSet)
                                    }
                                }
                        }
                }, {
                    it.printStackTrace()
                }))
        }
        tutorialLinkThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutoriallinks",
                null, { array ->
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val tutorialLinkId = row.getLong("tutorialLinkId")
                            val tutorialLink = TutorialLink(tutorialLinkId,
                                row.getLong("tutorialId"), row.getLong("originId"),
                                row.getInt("instructionNumber")
                            )
                            tutorialLinkViewModel.getByTutorialLinkId(tutorialLinkId)
                                .observe(activity) {
                                if(it!=null && !it.equals(tutorialLink)) {
                                    tutorialLinkViewModel.update(tutorialLink)
                                } else {
                                    tutorialLinkViewModel.insert(tutorialLink)
                                }
                            }
                        }
                }, {
                    it.printStackTrace()
                }))
        }
        tutorialSoundThread = thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialsounds",
                null, { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val soundId = row.getLong("soundId")
                        val tutorialSound = TutorialSound(soundId,
                            row.getLong("soundStart"), row.getBoolean("soundLoop"),
                            row.getLong("interval"), row.getLong("tutorialId"),
                            row.getString("fileName")
                        )
                        tutorialSoundViewModel.getByTutorialSoundId(soundId).observe(activity) {
                            if(it!=null && !it.equals(tutorialSound)) {
                                tutorialSoundViewModel.update(tutorialSound)
                            } else {
                                tutorialSoundViewModel.insert(tutorialSound)
                            }
                        }
                    }
                }, {
                    it.printStackTrace()
                }))
        }
    }
}
