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
    private val mViewModelProvider = ViewModelProvider(activity)
    private val mTutorialViewModel = mViewModelProvider.get(TutorialViewModel::class.java)
    private val mCategoryViewModel = mViewModelProvider.get(CategoryViewModel::class.java)
    private val mTagViewModel = mViewModelProvider.get(TagViewModel::class.java)
    private val mKeywordViewModel = mViewModelProvider.get(KeywordViewModel::class.java)
    private val mHelperViewModel = mViewModelProvider.get(HelperViewModel::class.java)
    private val mHelperTagViewModel = mViewModelProvider.get(HelperTagViewModel::class.java)
    private val mTutorialTagViewModel = mViewModelProvider.get(TutorialTagViewModel::class.java)
    private val mTagKeywordViewModel = mViewModelProvider.get(TagKeywordViewModel::class.java)
    private val mCategoryTagViewModel = mViewModelProvider.get(CategoryTagViewModel::class.java)
    private val mInstructionSetViewModel =
        mViewModelProvider.get(InstructionSetViewModel::class.java)
    private val mTutorialLinkViewModel = mViewModelProvider.get(TutorialLinkViewModel::class.java)
    private val mTutorialSoundViewModel = mViewModelProvider.get(TutorialSoundViewModel::class.java)
    private val mVersionViewModel = mViewModelProvider.get(VersionViewModel::class.java)
    private val mVersionSoundViewModel = mViewModelProvider.get(VersionSoundViewModel::class.java)
    private val mVersionInstructionViewModel =
        mViewModelProvider.get(VersionInstructionViewModel::class.java)
    private val mVersionMultimediaViewModel = mViewModelProvider
        .get(VersionMultimediaViewModel::class.java)
    private var mCategoryDownload = false
    private var mVersionInstructionDownload = false
    private var mVersionSoundDownload = false
    private var mVersionDownload = false
    private var mTutorialSoundDownload = false
    private var mTutorialLinkDownload = false
    private var mInstructionsDownload = false
    private var mCategoryTagDownload = false
    private var mTagKeywordDownload = false
    private var mTutorialTagDownload = false
    private var mHelperTagDownload = false
    private var mHelperDownload = false
    private var mKeywordDownload = false
    private var mTagDownload = false
    private var mTutorialDownload = false
    private var mVersionMultimediaDownload = false
    private lateinit var mQueue: RequestQueue

    fun completed() : Boolean
    {
        return mCategoryDownload && mVersionDownload && mVersionInstructionDownload &&
                mVersionSoundDownload && mTutorialSoundDownload && mTutorialLinkDownload &&
                mInstructionsDownload && mCategoryTagDownload && mTagKeywordDownload &&
                mTutorialTagDownload && mHelperTagDownload && mHelperDownload &&
                mKeywordDownload && mTagDownload && mTutorialDownload && mVersionMultimediaDownload
    }

    fun requestData(cacheDir: File)
    {
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        mQueue = RequestQueue(cache, network).apply {
            start()
        }
        val imageDir = File(activity.filesDir.absolutePath).absolutePath + "/"
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "tutorials", null, {
                    array ->
                mTutorialDownload = true
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val miniatureName = row.getString("miniatureName")
                    val tutorialId = row.getLong("tutorialId")
                    val tutorial = Tutorial(tutorialId, row.getString("tutorialName"),
                        row.getLong("authorId"), miniatureName,
                        row.getDouble("rating").toFloat())
                    mTutorialViewModel.getByTutorialId(tutorialId).observe(activity) {
                        if(it!=null) {
                            mTutorialViewModel.update(tutorial)
                        } else {
                            mTutorialViewModel.insert(tutorial)
                        }
                    }
                    mQueue.add(ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
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
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "categories", null,
                { array ->
                mCategoryDownload = true
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val miniatureName = row.getString("miniatureName")
                    val categoryId = row.getLong("categoryId")
                    val category = Category(categoryId, row.getString("categoryName"),
                        row.getBoolean("hasSubcategories"), miniatureName,
                        row.getInt("categoryLevel"))
                    mCategoryViewModel.getByCategoryId(categoryId).observe(activity) {
                        if(it!=null) {
                            mCategoryViewModel.update(category)
                        } else {
                            mCategoryViewModel.insert(category)
                        }
                    }
                    val file = File(imageDir + miniatureName)
                    if (!file.exists()) {
                        mQueue.add(
                            ImageRequest(url + "files/images/" + miniatureName, { bitmap ->
                                val output = FileOutputStream(file)
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
                                output.close()}, 160, 160,
                                ImageView.ScaleType.FIT_CENTER, Bitmap.Config.ARGB_8888,
                                null))
                    }
                }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "tags", null, {
                    array ->
                mTagDownload = true
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val tagId = row.getLong("tagId")
                    val tag = Tag(tagId, row.getString("tagName"),
                        row.getInt("tagLevel"))
                    mTagViewModel.getByTagId(tagId).observe(activity) {
                        if(it!=null) {
                            mTagViewModel.update(tag)
                        } else {
                            mTagViewModel.insert(tag)
                        }
                    }
                }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "keywords", null, {
                    array ->
                mKeywordDownload = true
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val keywordId = row.getLong("keywordId")
                    val keyword = Keyword(keywordId, row.getString("keyword"))
                    mKeywordViewModel.getByKeywordId(keywordId).observe(activity) {
                        if(it!=null) {
                            mKeywordViewModel.update(keyword)
                        } else {
                            mKeywordViewModel.insert(keyword)
                        }
                    }
                }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "helperlist", null,
                { array ->
                mHelperDownload = true
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val helperId = row.getLong("helperId")
                    val helper = Helper(helperId, row.getString("name"),
                        row.getString("surname"), row.getString("title"),
                        row.getString("profession"))
                    mHelperViewModel.getByHelperId(helperId).observe(activity) {
                        if(it!=null) {
                            mHelperViewModel.update(helper)
                        } else {
                            mHelperViewModel.insert(helper)
                        }
                    }
                }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "helpertags", null,
                { array ->
                mHelperTagDownload = true
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val helperTagId = row.getLong("helperTagId")
                    val helperTag = HelperTag(helperTagId,
                        row.getLong("helperId"), row.getLong("tagId"))
                    mHelperTagViewModel.getByHelperTagId(helperTagId).observe(activity) {
                        if(it!=null) {
                            mHelperTagViewModel.update(helperTag)
                        } else {
                            mHelperTagViewModel.insert(helperTag)
                        }
                    }
                }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialtags",
                null, { array ->
                    mTutorialTagDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tutorialTagId = row.getLong("tutorialTagId")
                        val tutorialTag = TutorialTag(tutorialTagId,
                            row.getLong("tutorialId"), row.getLong("tagId"))
                        mTutorialTagViewModel.getByTutorialTagId(tutorialTagId).observe(activity)
                        {
                            if (it!=null) {
                                mTutorialTagViewModel.update(tutorialTag)
                            } else {
                                mTutorialTagViewModel.insert(tutorialTag)
                            }
                        }
                    }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "tagkeywords", null,
                { array ->
                    mTagKeywordDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tagKeywordId = row.getLong("tagKeywordId")
                        val tagKeyword = TagKeyword(tagKeywordId,
                            row.getLong("keywordId"), row.getLong("tagId"))
                        mTagKeywordViewModel.getByTagKeywordId(tagKeywordId).observe(activity)
                        {
                            if(it!=null) {
                                mTagKeywordViewModel.update(tagKeyword)
                            } else {
                                mTagKeywordViewModel.insert(tagKeyword)
                            }
                        }
                    }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "categorytags",
                null, { array ->
                    mCategoryTagDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val categoryTagId = row.getLong("categoryTagId")
                        val categoryTag = CategoryTag(categoryTagId,
                            row.getLong("categoryId"), row.getLong("tagId"))
                        mCategoryTagViewModel.getByCategoryTagId(categoryTagId).observe(activity)
                        {
                            if(it!=null) {
                                mCategoryTagViewModel.update(categoryTag)
                            } else {
                                mCategoryTagViewModel.insert(categoryTag)
                            }
                        }
                    }
            }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "instructions",
                null, { array ->
                    mInstructionsDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val instructionSetId = row.getLong("instructionSetId")
                        val instructionSet = InstructionSet(instructionSetId,
                            row.getString("title"), row.getString("instructions"),
                            row.getInt("time"), row.getLong("tutorialId"),
                            row.getInt("position"), row.getString("narrationName"))
                        mInstructionSetViewModel.getByInstructionSetId(instructionSetId)
                            .observe(activity) {
                                if(it!=null) {
                                    mInstructionSetViewModel.update(instructionSet)
                                } else {
                                    mInstructionSetViewModel.insert(instructionSet)
                                }
                            }
                        }
                }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "tutoriallinks",
                null, { array ->
                    mTutorialLinkDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val tutorialLinkId = row.getLong("tutorialLinkId")
                        val tutorialLink = TutorialLink(tutorialLinkId,
                            row.getLong("tutorialId"), row.getLong("originId"),
                            row.getInt("instructionNumber"))
                        mTutorialLinkViewModel.getByTutorialLinkId(tutorialLinkId).observe(activity)
                        {
                            if(it!=null) {
                                mTutorialLinkViewModel.update(tutorialLink)
                            } else {
                                mTutorialLinkViewModel.insert(tutorialLink)
                            }
                        }
                    }
                }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "tutorialsounds",
                null, { array ->
                    mTutorialSoundDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val soundId = row.getLong("soundId")
                        val tutorialSound = TutorialSound(soundId, row.getLong("soundStart"),
                            row.getBoolean("soundLoop"), row.getLong("interval"),
                            row.getLong("tutorialId"), row.getString("fileName"))
                        mTutorialSoundViewModel.getByTutorialSoundId(soundId).observe(activity)
                        {
                            if(it!=null) {
                                mTutorialSoundViewModel.update(tutorialSound)
                            } else {
                                mTutorialSoundViewModel.insert(tutorialSound)
                            }
                        }
                    }
                }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "versions", null,
                { array ->
                    mVersionDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionId = row.getLong("versionId")
                        val version = Version(versionId, row.getString("text"),
                            row.getLong("tutorialId"),
                            row.getBoolean("delayGlobalSound"),
                            row.getBoolean("hasChildren"), row.getBoolean("hasParent"),
                            row.getLong("parentVersionId"))
                        mVersionViewModel.getByVersionId(versionId).observe(activity) {
                            if(it!=null) {
                                mVersionViewModel.update(version)
                            } else {
                                mVersionViewModel.insert(version)
                            }
                        }
                    }
                }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "versioninstructions",
                null, { array ->
                    mVersionInstructionDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionInstructionId = row.getLong("versionInstructionId")
                        val versionInstruction = VersionInstruction(versionInstructionId,
                            row.getLong("versionId"), row.getInt("instructionPosition"))
                        mVersionInstructionViewModel.getByVersionInstructionId(versionInstructionId)
                            .observe(activity) {
                                if(it!=null) {
                                    mVersionInstructionViewModel.update(versionInstruction)
                                } else {
                                    mVersionInstructionViewModel.insert(versionInstruction)
                                }
                            }
                    }
                }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "versionmultimedia",
                null, { array ->
                    mVersionMultimediaDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionMultimediaId = row.getLong("versionMultimediaId")
                        val versionMultimedia = VersionMultimedia(versionMultimediaId,
                            row.getLong("multimediaId"), row.getLong("versionId"))
                        mVersionMultimediaViewModel.getByVersionMultimediaId(versionMultimediaId)
                            .observe(activity) {
                                if(it!=null) {
                                    mVersionMultimediaViewModel.update(versionMultimedia)
                                } else {
                                    mVersionMultimediaViewModel.insert(versionMultimedia)
                                }
                            }
                    }
                }, null))
        }
        thread {
            mQueue.add(JsonArrayRequest(Request.Method.GET, url + "versionsound",
                null, { array ->
                    mVersionSoundDownload = true
                    for(i in 0 until array.length()) {
                        val row: JSONObject = array.getJSONObject(i)
                        val versionSoundId = row.getLong("versionSoundId")
                        val versionSound = VersionSound(versionSoundId,
                            row.getLong("tutorialSoundId"), row.getLong("versionId"))
                        mVersionSoundViewModel.getByVersionSoundId(versionSoundId).observe(activity)
                        {
                            if(it!=null) {
                                mVersionSoundViewModel.update(versionSound)
                            } else {
                                mVersionSoundViewModel.insert(versionSound)
                            }
                        }
                    }
                }, null))
        }
    }
}
