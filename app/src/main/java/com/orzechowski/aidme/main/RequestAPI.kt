package com.orzechowski.aidme.main

import android.database.sqlite.SQLiteException
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
import com.orzechowski.aidme.tutorial.database.TutorialViewModel
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

class RequestAPI (val activity: MainActivity) {

    private val tutorialViewModel = ViewModelProvider(activity).get(TutorialViewModel::class.java)
    private val categoryViewModel = ViewModelProvider(activity).get(CategoryViewModel::class.java)
    private val tagViewModel = ViewModelProvider(activity).get(TagViewModel::class.java)
    private val keywordViewModel = ViewModelProvider(activity).get(KeywordViewModel::class.java)
    private val helperViewModel = ViewModelProvider(activity).get(HelperViewModel::class.java)
    private val helperTagViewModel = ViewModelProvider(activity).get(HelperTagViewModel::class.java)
    private val tutorialTagViewModel = ViewModelProvider(activity)
        .get(TutorialTagViewModel::class.java)
    private val tagKeywordViewModel = ViewModelProvider(activity)
        .get(TagKeywordViewModel::class.java)
    private val categoryTagViewModel = ViewModelProvider(activity)
        .get(CategoryTagViewModel::class.java)

    fun requestData(cacheDir: File)
    {
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 2048*2048)
        val network = BasicNetwork(HurlStack())
        val queue = RequestQueue(cache, network).apply {
            start()
        }
        val imageDir = File(activity.filesDir.absolutePath + "/images")

        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorials", null, {
                    array ->
                tutorialViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val miniatureName = row.getString("miniatureName")
                        val tutorial = Tutorial(
                            row.getLong("tutorialId"),
                            row.getString("tutorialName"), row.getLong("authorId"),
                            miniatureName,
                            row.getDouble("rating").toFloat()
                        )
                        try {
                            tutorialViewModel.update(tutorial)
                        } catch (e: SQLiteException) {
                            tutorialViewModel.insert(tutorial)
                        }
                        queue.add(
                            ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
                                try {
                                    if (!imageDir.exists()) {
                                        if (!imageDir.mkdirs()) {
                                            throw FileNotFoundException()
                                        }
                                    }
                                    val file = File(imageDir.absolutePath + miniatureName)
                                    if (file.exists()) {
                                        throw IOException()
                                    } else {
                                        val output = FileOutputStream(file)
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                            output)
                                        output.close()
                                        bitmap.recycle()
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

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categories", null, {
                    array ->
                categoryViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val miniatureName = row.getString("miniatureName")
                        val category = Category(
                            row.getLong("categoryId"),
                            row.getString("categoryName"),
                            row.getBoolean("hasSubcategories"), miniatureName,
                            row.getInt("categoryLevel")
                        )
                        try {
                            categoryViewModel.update(category)
                        } catch (e: SQLiteException) {
                            categoryViewModel.insert(category)
                        }
                        queue.add(
                            ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
                                try {
                                    if (!imageDir.exists()) {
                                        if (!imageDir.mkdirs()) {
                                            throw FileNotFoundException()
                                        }
                                    }
                                    val file = File(imageDir.absolutePath + miniatureName)
                                    if (file.exists()) {
                                        throw IOException()
                                    } else {
                                        val output = FileOutputStream(file)
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                            output)
                                        output.close()
                                        bitmap.recycle()
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

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tags", null, {
                    array ->
                tagViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tag = Tag(
                            row.getLong("tagId"), row.getString("tagName"),
                            row.getInt("tagLevel")
                        )
                        try {
                            tagViewModel.update(tag)
                        } catch (e: SQLiteException) {
                            tagViewModel.insert(tag)
                        }
                    }
                }
            }, {

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "keywords", null, {
                    array ->
                keywordViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val keyword = Keyword(
                            row.getLong("keywordId"),
                            row.getString("word")
                        )
                        try {
                            keywordViewModel.update(keyword)
                        } catch (e: SQLiteException) {
                            keywordViewModel.insert(keyword)
                        }
                    }
                }
            }, {

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helperlist", null, {
                    array ->
                helperViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helper = Helper(
                            row.getLong("helperId"), row.getString("name"),
                            row.getString("surname"), row.getString("title"),
                            row.getString("profession")
                        )
                        try {
                            helperViewModel.update(helper)
                        } catch (e: SQLiteException) {
                            helperViewModel.insert(helper)
                        }
                    }
                }
            }, {

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helpertags", null, {
                    array ->
                helperTagViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helperTag = HelperTag(
                            row.getLong("helperTagId"),
                            row.getLong("helperId"), row.getLong("tagId")
                        )
                        try {
                            helperTagViewModel.update(helperTag)
                        } catch (e: SQLiteException) {
                            helperTagViewModel.insert(helperTag)
                        }
                    }
                }
            }, {

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialtags", null,
                { array ->
                tutorialTagViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tutorialTag = TutorialTag(
                            row.getLong("tutorialTagId"),
                            row.getLong("tutorialId"), row.getLong("tagId")
                        )
                        try {
                            tutorialTagViewModel.update(tutorialTag)
                        } catch (e: SQLiteException) {
                            tutorialTagViewModel.insert(tutorialTag)
                        }
                    }
                }
            }, {

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tagkeywords", null,
                { array ->
                tagKeywordViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagKeyword = TagKeyword(
                            row.getLong("tagKeywordId"),
                            row.getLong("keywordId"), row.getLong("tagId")
                        )
                        try {
                            tagKeywordViewModel.update(tagKeyword)
                        } catch (e: SQLiteException) {
                            tagKeywordViewModel.insert(tagKeyword)
                        }
                    }
                }
            }, {

            }))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categorytags", null,
                { array ->
                categoryTagViewModel.all.observe(activity) {
                    for (i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val categoryTag = CategoryTag(
                            row.getLong("categoryTagId"),
                            row.getLong("categoryId"), row.getLong("tagId")
                        )
                        try {
                            categoryTagViewModel.update(categoryTag)
                        } catch (e: SQLiteException) {
                            categoryTagViewModel.insert(categoryTag)
                        }
                    }
                }
            }, {

            }))
        }
    }
}