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

    fun end()
    {
        thread {
            Thread.sleep(2000)
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
        tutorialThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorials", null, {
                    array ->
                tutorialViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(tut in it) {
                        ids.add(tut.tutorialId)
                    }
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val miniatureName = row.getString("miniatureName")
                        val tutorial = Tutorial(
                            row.getLong("tutorialId"),
                            row.getString("tutorialName"), row.getLong("authorId"),
                            miniatureName,
                            row.getDouble("rating").toFloat()
                        )
                        if(ids.contains(tutorial.tutorialId)) {
                            tutorialViewModel.update(tutorial)
                        } else {
                            tutorialViewModel.insert(tutorial)
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
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        categoryThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categories", null, {
                    array ->
                categoryViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(cat in it) {
                        ids.add(cat.categoryId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val miniatureName = row.getString("miniatureName")
                        val category = Category(
                            row.getLong("categoryId"),
                            row.getString("categoryName"),
                            row.getBoolean("hasSubcategories"), miniatureName,
                            row.getInt("categoryLevel")
                        )
                        if(ids.contains(category.categoryId)) {
                            categoryViewModel.update(category)
                        } else {
                            categoryViewModel.insert(category)
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
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        tagThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tags", null, {
                    array ->
                tagViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(t in it) {
                        ids.add(t.tagId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tag = Tag(
                            row.getLong("tagId"), row.getString("tagName"),
                            row.getInt("tagLevel")
                        )
                        if(ids.contains(tag.tagId)) {
                            tagViewModel.update(tag)
                        } else {
                            tagViewModel.insert(tag)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        keywordThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "keywords", null, {
                    array ->
                keywordViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(key in it) {
                        ids.add(key.keywordId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val keyword = Keyword(
                            row.getLong("keywordId"),
                            row.getString("word")
                        )
                        if(ids.contains(keyword.keywordId)) {
                            keywordViewModel.update(keyword)
                        } else {
                            keywordViewModel.insert(keyword)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        helperThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helperlist", null, {
                    array ->
                helperViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(hel in it) {
                        ids.add(hel.helperId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helper = Helper(
                            row.getLong("helperId"), row.getString("name"),
                            row.getString("surname"), row.getString("title"),
                            row.getString("profession")
                        )
                        if(ids.contains(helper.helperId)) {
                            helperViewModel.update(helper)
                        } else {
                            helperViewModel.insert(helper)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        helperTagThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helpertags", null, {
                    array ->
                helperTagViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(htag in it) {
                        ids.add(htag.helperTagId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helperTag = HelperTag(
                            row.getLong("helperTagId"),
                            row.getLong("helperId"), row.getLong("tagId")
                        )
                        if(ids.contains(helperTag.helperTagId)) {
                            helperTagViewModel.update(helperTag)
                        } else {
                            helperTagViewModel.insert(helperTag)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        tutorialTagThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialtags", null,
                { array ->
                tutorialTagViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(ttag in it) {
                        ids.add(ttag.tutorialTagId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tutorialTag = TutorialTag(
                            row.getLong("tutorialTagId"),
                            row.getLong("tutorialId"), row.getLong("tagId")
                        )
                        if(ids.contains(tutorialTag.tutorialTagId)) {
                            tutorialTagViewModel.update(tutorialTag)
                        } else {
                            tutorialTagViewModel.insert(tutorialTag)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        tagKeywordThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tagkeywords", null,
                { array ->
                tagKeywordViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(tkey in it) {
                        ids.add(tkey.tagKeywordId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagKeyword = TagKeyword(
                            row.getLong("tagKeywordId"),
                            row.getLong("keywordId"), row.getLong("tagId")
                        )
                        if(ids.contains(tagKeyword.tagKeywordId)) {
                            tagKeywordViewModel.update(tagKeyword)
                        } else {
                            tagKeywordViewModel.insert(tagKeyword)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        categoryTagThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categorytags", null,
                { array ->
                categoryTagViewModel.all.observe(activity) {
                    val ids = mutableListOf<Long>()
                    for(ctag in it) {
                        ids.add(ctag.categoryTagId)
                    }
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val categoryTag = CategoryTag(
                            row.getLong("categoryTagId"),
                            row.getLong("categoryId"), row.getLong("tagId")
                        )
                        if(ids.contains(categoryTag.categoryTagId)) {
                            categoryTagViewModel.update(categoryTag)
                        } else {
                            categoryTagViewModel.insert(categoryTag)
                        }
                    }
                }
            }, {
                it.printStackTrace()
            }))
        }.also { it.start() }
        instructionThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "instructions",
                null, { array ->
                    instructionSetViewModel.all.observe(activity) {
                        val ids = mutableListOf<Long>()
                        for(ins in it) {
                            ids.add(ins.instructionSetId)
                        }
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val instructionSet = InstructionSet(
                                row.getLong("instructionSetId"), row.getString("title"),
                                row.getString("instructions"), row.getInt("time"),
                                row.getLong("tutorialId"), row.getInt("position"),
                                row.getString("narrationName")
                            )
                            if(ids.contains(instructionSet.instructionSetId)) {
                                instructionSetViewModel.update(instructionSet)
                            } else {
                                instructionSetViewModel.insert(instructionSet)
                            }
                        }
                    }
                }, {
                    it.printStackTrace()
                }))
        }.also { it.start() }
        tutorialLinkThread = Thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutoriallinks",
                null, { array ->
                    tutorialLinkViewModel.all.observe(activity) {
                        val ids = mutableListOf<Long>()
                        for(lin in it) {
                            ids.add(lin.tutorialLinkId)
                        }
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val tutorialLink = TutorialLink(
                                row.getLong("tutorialLinkId"),
                                row.getLong("tutorialId"), row.getLong("originId"),
                                row.getInt("instructionNumber")
                            )
                            if(ids.contains(tutorialLink.tutorialLinkId)) {
                                tutorialLinkViewModel.update(tutorialLink)
                            } else {
                                tutorialLinkViewModel.insert(tutorialLink)
                            }
                        }
                    }
                }, {
                    it.printStackTrace()
                }))
        }.also { it.start() }
    }
}
