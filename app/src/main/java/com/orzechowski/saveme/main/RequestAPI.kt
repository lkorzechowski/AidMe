package com.orzechowski.saveme.main

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.saveme.browser.categories.database.Category
import com.orzechowski.saveme.browser.categories.database.CategoryViewModel
import com.orzechowski.saveme.browser.search.database.Keyword
import com.orzechowski.saveme.browser.search.database.KeywordViewModel
import com.orzechowski.saveme.browser.search.database.TagKeyword
import com.orzechowski.saveme.browser.search.database.TagKeywordViewModel
import com.orzechowski.saveme.database.tag.*
import com.orzechowski.saveme.helper.database.Helper
import com.orzechowski.saveme.helper.database.HelperViewModel
import com.orzechowski.saveme.tutorial.database.Tutorial
import com.orzechowski.saveme.tutorial.database.TutorialLink
import com.orzechowski.saveme.tutorial.database.TutorialLinkViewModel
import com.orzechowski.saveme.tutorial.database.TutorialViewModel
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSetViewModel
import com.orzechowski.saveme.tutorial.mediaplayer.multimedia.database.VersionMultimedia
import com.orzechowski.saveme.tutorial.mediaplayer.multimedia.database.VersionMultimediaViewModel
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.TutorialSound
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.TutorialSoundViewModel
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.VersionSound
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.VersionSoundViewModel
import com.orzechowski.saveme.tutorial.version.database.Version
import com.orzechowski.saveme.tutorial.version.database.VersionInstruction
import com.orzechowski.saveme.tutorial.version.database.VersionInstructionViewModel
import com.orzechowski.saveme.tutorial.version.database.VersionViewModel
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

class RequestAPI(val activity: AppCompatActivity)
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
    private val versionViewModel = viewModelProvider.get(VersionViewModel::class.java)
    private val versionSoundViewModel = viewModelProvider.get(VersionSoundViewModel::class.java)
    private val versionInstructionViewModel =
        viewModelProvider.get(VersionInstructionViewModel::class.java)
    private val versionMultimediaViewModel = viewModelProvider
        .get(VersionMultimediaViewModel::class.java)
    private lateinit var queue: RequestQueue

    fun requestData(cacheDir: File)
    {
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        queue = RequestQueue(cache, network).apply {
            start()
        }
        val imageDir = File(activity.filesDir.absolutePath).absolutePath + "/"
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorials", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val miniatureName = row.getString("miniatureName")
                    val tutorialId = row.getLong("tutorialId")
                    val tutorial = Tutorial(tutorialId, row.getString("tutorialName"),
                        row.getLong("authorId"), miniatureName,
                        row.getDouble("rating").toFloat())
                    tutorialViewModel.getByTutorialId(tutorialId).observe(activity) {
                        if(it!=null) {
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
                        Bitmap.Config.ARGB_8888, null)
                    )
                }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categories", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val miniatureName = row.getString("miniatureName")
                        val categoryId = row.getLong("categoryId")
                        val category = Category(categoryId, row.getString("categoryName"),
                            row.getBoolean("hasSubcategories"), miniatureName,
                            row.getInt("categoryLevel"))
                        categoryViewModel.getByCategoryId(categoryId).observe(activity) {
                            if(it!=null) {
                                categoryViewModel.update(category)
                            } else {
                                categoryViewModel.insert(category)
                            }
                        }
                        val file = File(imageDir + miniatureName)
                        if (!file.exists()) {
                            queue.add(
                                ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
                                    val output = FileOutputStream(file)
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                                    output.close()
                                }, 160, 160, ImageView.ScaleType.FIT_CENTER,
                                    Bitmap.Config.ARGB_8888, null))
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tags", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagId = row.getLong("tagId")
                        val tag = Tag(tagId, row.getString("tagName"),
                            row.getInt("tagLevel"))
                        tagViewModel.getByTagId(tagId).observe(activity) {
                            if(it!=null) {
                                tagViewModel.update(tag)
                            } else {
                                tagViewModel.insert(tag)
                            }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "keywords", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val keywordId = row.getLong("keywordId")
                        val keyword = Keyword(keywordId, row.getString("keyword"))
                        keywordViewModel.getByKeywordId(keywordId).observe(activity) {
                            if(it!=null) {
                                keywordViewModel.update(keyword)
                            } else {
                                keywordViewModel.insert(keyword)
                            }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helperlist", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helperId = row.getLong("helperId")
                        val helper = Helper(helperId, row.getString("name"),
                            row.getString("surname"), row.getString("title"),
                            row.getString("profession"))
                        helperViewModel.getByHelperId(helperId).observe(activity) {
                            if(it!=null) {
                                helperViewModel.update(helper)
                            } else {
                                helperViewModel.insert(helper)
                            }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "helpertags", null, {
                    array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val helperTagId = row.getLong("helperTagId")
                        val helperTag = HelperTag(helperTagId,
                            row.getLong("helperId"), row.getLong("tagId"))
                        helperTagViewModel.getByHelperTagId(helperTagId).observe(activity) {
                            if(it!=null) {
                                helperTagViewModel.update(helperTag)
                            } else {
                                helperTagViewModel.insert(helperTag)
                           }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialtags", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tutorialTagId = row.getLong("tutorialTagId")
                        val tutorialTag = TutorialTag(tutorialTagId,
                            row.getLong("tutorialId"), row.getLong("tagId"))
                        tutorialTagViewModel.getByTutorialTagId(tutorialTagId).observe(activity) {
                            if (it!=null) {
                                tutorialTagViewModel.update(tutorialTag)
                            } else {
                                tutorialTagViewModel.insert(tutorialTag)
                            }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tagkeywords", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagKeywordId = row.getLong("tagKeywordId")
                        val tagKeyword = TagKeyword(tagKeywordId,
                            row.getLong("keywordId"), row.getLong("tagId"))
                        tagKeywordViewModel.getByTagKeywordId(tagKeywordId).observe(activity) {
                            if(it!=null) {
                                tagKeywordViewModel.update(tagKeyword)
                            } else {
                                tagKeywordViewModel.insert(tagKeyword)
                            }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "categorytags", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val categoryTagId = row.getLong("categoryTagId")
                        val categoryTag = CategoryTag(categoryTagId,
                            row.getLong("categoryId"), row.getLong("tagId"))
                        categoryTagViewModel.getByCategoryTagId(categoryTagId).observe(activity) {
                            if(it!=null) {
                                categoryTagViewModel.update(categoryTag)
                            } else {
                                categoryTagViewModel.insert(categoryTag)
                            }
                        }
                    }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "instructions",
                null, { array ->
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val instructionSetId = row.getLong("instructionSetId")
                            val instructionSet = InstructionSet(instructionSetId,
                                row.getString("title"), row.getString("instructions"),
                                row.getInt("time"), row.getLong("tutorialId"),
                                row.getInt("position"), row.getString("narrationName"))
                            instructionSetViewModel.getByInstructionSetId(instructionSetId)
                                .observe(activity) {
                                    if(it!=null) {
                                        instructionSetViewModel.update(instructionSet)
                                    } else {
                                        instructionSetViewModel.insert(instructionSet)
                                    }
                                }
                        }
                }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutoriallinks",
                null, { array ->
                        for(i in 0 until array.length()) {
                            val row: JSONObject = array.getJSONObject(i)
                            val tutorialLinkId = row.getLong("tutorialLinkId")
                            val tutorialLink = TutorialLink(tutorialLinkId,
                                row.getLong("tutorialId"), row.getLong("originId"),
                                row.getInt("instructionNumber"))
                            tutorialLinkViewModel.getByTutorialLinkId(tutorialLinkId)
                                .observe(activity) {
                                if(it!=null) {
                                    tutorialLinkViewModel.update(tutorialLink)
                                } else {
                                    tutorialLinkViewModel.insert(tutorialLink)
                                }
                            }
                        }
                }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialsounds",
                null, { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val soundId = row.getLong("soundId")
                        val tutorialSound = TutorialSound(soundId, row.getLong("soundStart"),
                            row.getBoolean("soundLoop"), row.getLong("interval"),
                            row.getLong("tutorialId"), row.getString("fileName"))
                        tutorialSoundViewModel.getByTutorialSoundId(soundId).observe(activity) {
                            if(it!=null) {
                                tutorialSoundViewModel.update(tutorialSound)
                            } else {
                                tutorialSoundViewModel.insert(tutorialSound)
                            }
                        }
                    }
                }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "versions", null,
                { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionId = row.getLong("versionId")
                        val version = Version(versionId, row.getString("text"),
                            row.getLong("tutorialId"),
                            row.getBoolean("delayGlobalSound"),
                            row.getBoolean("hasChildren"), row.getBoolean("hasParent"),
                            row.getLong("parentVersionId"))
                        versionViewModel.getByVersionId(versionId).observe(activity) {
                            if(it!=null) {
                                versionViewModel.update(version)
                            } else {
                                versionViewModel.insert(version)
                            }
                        }
                    }
                }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "versioninstructions",
                null, { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionInstructionId = row.getLong("versionInstructionId")
                        val versionInstruction = VersionInstruction(versionInstructionId,
                            row.getLong("versionId"), row.getInt("instructionPosition"))
                        versionInstructionViewModel.getByVersionInstructionId(versionInstructionId)
                            .observe(activity) {
                                if(it!=null) {
                                    versionInstructionViewModel.update(versionInstruction)
                                } else {
                                    versionInstructionViewModel.insert(versionInstruction)
                                }
                            }
                    }
                }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "versionmultimedia",
                null, { array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val versionMultimediaId = row.getLong("versionMultimediaId")
                    val versionMultimedia = VersionMultimedia(versionMultimediaId,
                        row.getLong("multimediaId"), row.getLong("versionId"))
                    versionMultimediaViewModel.getByVersionMultimediaId(versionMultimediaId)
                        .observe(activity) {
                            if(it!=null) {
                                versionMultimediaViewModel.update(versionMultimedia)
                            } else {
                                versionMultimediaViewModel.insert(versionMultimedia)
                            }
                        }
                }
            }, null))
        }
        thread {
            queue.add(JsonArrayRequest(Request.Method.GET, url + "versionsound",
                null, { array ->
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionSoundId = row.getLong("versionSoundId")
                        val versionSound = VersionSound(versionSoundId,
                            row.getLong("tutorialSoundId"), row.getLong("versionId"))
                        versionSoundViewModel.getByVersionSoundId(versionSoundId).observe(activity)
                        {
                                if(it!=null) {
                                    versionSoundViewModel.update(versionSound)
                                } else {
                                    versionSoundViewModel.insert(versionSound)
                                }
                            }
                    }
                }, null))
        }
    }
}
