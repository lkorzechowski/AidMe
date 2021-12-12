package com.orzechowski.saveme

import android.app.job.JobService
import android.app.job.JobParameters
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.orzechowski.saveme.browser.categories.database.Category
import com.orzechowski.saveme.browser.search.database.Keyword
import com.orzechowski.saveme.browser.search.database.TagKeyword
import com.orzechowski.saveme.database.GlobalRoomDatabase
import com.orzechowski.saveme.database.tag.CategoryTag
import com.orzechowski.saveme.database.tag.Tag
import com.orzechowski.saveme.database.tag.TutorialTag
import com.orzechowski.saveme.tutorial.database.Tutorial
import com.orzechowski.saveme.tutorial.database.TutorialLink
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet
import com.orzechowski.saveme.tutorial.version.database.Version
import com.orzechowski.saveme.tutorial.version.database.VersionInstruction
import org.json.JSONObject
import kotlin.concurrent.thread

class AutoUpdater : JobService()
{
    override fun onStartJob(jobParameters: JobParameters): Boolean
    {
        val url = getString(R.string.url)
        val database = GlobalRoomDatabase.getDatabase(applicationContext)
        val queue = RequestQueue(DiskBasedCache(cacheDir, 1024 * 1024),
            BasicNetwork(HurlStack())).apply { start() }
        queue.add(JsonArrayRequest(Request.Method.GET, url + "tutorials", null,
            { array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val miniatureName = row.getString("miniatureName")
                    val tutorialId = row.getLong("tutorialId")
                    val tutorial = Tutorial(tutorialId, row.getString("tutorialName"),
                        row.getLong("authorId"), miniatureName,
                        row.getDouble("rating").toFloat())
                    thread {
                        if (database.tutorialDAO().getByTutorialId(tutorialId) != null) {
                            database.tutorialDAO().update(tutorial)
                        } else {
                            database.tutorialDAO().insert(tutorial)
                        }
                    }
                }
            }, null))
        queue.add(JsonArrayRequest(Request.Method.GET, url + "categories", null,
            { array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val miniatureName = row.getString("miniatureName")
                    val categoryId = row.getLong("categoryId")
                    val category = Category(categoryId, row.getString("categoryName"),
                        row.getBoolean("hasSubcategories"), miniatureName,
                        row.getInt("categoryLevel"))
                    thread {
                        if (database.categoryDAO().getByCategoryId(categoryId) != null) {
                            database.categoryDAO().update(category)
                        } else {
                            database.categoryDAO().insert(category)
                        }
                    }
                }
            }, null))
        queue.add(JsonArrayRequest(Request.Method.GET, url + "tags", null, { array ->
            for(i in 0 until array.length()) {
                val row: JSONObject = array.getJSONObject(i)
                val tagId = row.getLong("tagId")
                val tag = Tag(tagId, row.getString("tagName"), row.getInt("tagLevel"))
                thread {
                    if (database.tagDAO().getByTagId(tagId) != null) {
                        database.tagDAO().update(tag)
                    } else {
                        database.tagDAO().insert(tag)
                    }
                }
            }
        }, null))
        queue.add(JsonArrayRequest(Request.Method.GET, url + "keywords", null, {
                array ->
            for(i in 0 until array.length()) {
                val row: JSONObject = array.getJSONObject(i)
                val keywordId = row.getLong("keywordId")
                val keyword = Keyword(keywordId, row.getString("keyword"))
                thread {
                    if (database.keywordDAO().getByKeywordId(keywordId) != null) {
                        database.keywordDAO().update(keyword)
                    } else {
                        database.keywordDAO().insert(keyword)
                    }
                }
            }
        }, null))
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "tutorialtags", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val tutorialTagId = row.getLong("tutorialTagId")
                    val tutorialTag = TutorialTag(tutorialTagId, row.getLong("tutorialId"),
                        row.getLong("tagId"))
                    thread {
                        if (database.tutorialTagDAO().getByTutorialTagId(tutorialTagId) != null) {
                            database.tutorialTagDAO().update(tutorialTag)
                        } else {
                            database.tutorialTagDAO().insert(tutorialTag)
                        }
                    }
                }
            }, null)
        )
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "tagkeywords", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val tagKeywordId = row.getLong("tagKeywordId")
                    val tagKeyword = TagKeyword(tagKeywordId,
                        row.getLong("keywordId"), row.getLong("tagId"))
                    thread {
                        if (database.tagKeywordDAO().getByTagKeywordId(tagKeywordId) != null) {
                            database.tagKeywordDAO().update(tagKeyword)
                        } else {
                            database.tagKeywordDAO().insert(tagKeyword)
                        }
                    }
                }
            }, null)
        )
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "categorytags", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val categoryTagId = row.getLong("categoryTagId")
                    val categoryTag = CategoryTag(categoryTagId,
                        row.getLong("categoryId"), row.getLong("tagId"))
                    thread {
                        if (database.categoryTagDAO().getByCategoryTagId(categoryTagId) != null) {
                            database.categoryTagDAO().update(categoryTag)
                        } else {
                            database.categoryTagDAO().insert(categoryTag)
                        }
                    }
                }
            }, null)
        )
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "instructions", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val instructionSetId = row.getLong("instructionSetId")
                    val instructionSet = InstructionSet(instructionSetId,
                        row.getString("title"), row.getString("instructions"),
                        row.getInt("time"), row.getLong("tutorialId"),
                        row.getInt("position"), row.getString("narrationFile"))
                    thread {
                        if (database.instructionDAO()
                                .getByInstructionSetId(instructionSetId) != null) {
                            database.instructionDAO().update(instructionSet)
                        } else {
                            database.instructionDAO().insert(instructionSet)
                        }
                    }
                }
            }, null)
        )
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "tutoriallinks", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val tutorialLinkId = row.getLong("tutorialLinkId")
                    val tutorialLink = TutorialLink(tutorialLinkId,
                        row.getLong("tutorialId"), row.getLong("originId"),
                        row.getInt("instructionNumber"))
                    thread {
                        if (database.tutorialLinkDAO()
                                .getByTutorialLinkId(tutorialLinkId) != null) {
                            database.tutorialLinkDAO().update(tutorialLink)
                        } else {
                            database.tutorialLinkDAO().insert(tutorialLink)
                        }
                    }
                }
            }, null)
        )
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "versions", null, { array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val versionId = row.getLong("versionId")
                    val version = Version(versionId, row.getString("text"),
                        row.getLong("tutorialId"), row.getBoolean("delayGlobalSound"),
                        row.getBoolean("hasChildren"), row.getBoolean("hasParent"),
                        row.getLong("parentVersionId"))
                    thread {
                        if (database.versionDAO().getByVersionId(versionId) != null) {
                            database.versionDAO().update(version)
                        } else {
                            database.versionDAO().insert(version)
                        }
                    }
                }
            }, null)
        )
        queue.add(
            JsonArrayRequest(Request.Method.GET, url + "versioninstructions", null, {
                    array ->
                for(i in 0 until array.length()) {
                    val row: JSONObject = array.getJSONObject(i)
                    val versionInstructionId = row.getLong("versionInstructionId")
                    val versionInstruction = VersionInstruction(versionInstructionId,
                        row.getLong("versionId"), row.getInt("instructionPosition"))
                    thread {
                        if (database.versionInstructionDAO()
                                .getByVersionInstructionId(versionInstructionId) != null) {
                            database.versionInstructionDAO().update(versionInstruction)
                        } else {
                            database.versionInstructionDAO().insert(versionInstruction)
                        }
                    }
                }
            }, null)
        )
        return true
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean
    {
        return true
    }
}
