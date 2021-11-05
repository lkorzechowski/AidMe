package com.orzechowski.aidme

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.orzechowski.aidme.browser.search.database.Keyword
import com.orzechowski.aidme.creator.categorypicker.CategoryAssignment
import com.orzechowski.aidme.creator.initial.InstructionComposer
import com.orzechowski.aidme.creator.initial.MultimediaComposer
import com.orzechowski.aidme.creator.initial.SoundComposer
import com.orzechowski.aidme.creator.initial.VersionComposer
import com.orzechowski.aidme.creator.initial.soundbrowser.Sound
import com.orzechowski.aidme.creator.initial.soundbrowser.SoundBrowserLoader
import com.orzechowski.aidme.creator.initial.soundbrowser.narrationbrowser.NarrationBrowserLoader
import com.orzechowski.aidme.creator.keywordassignment.KeywordAssignment
import com.orzechowski.aidme.creator.tutoriallinks.TutorialLinkComposer
import com.orzechowski.aidme.creator.versioninstruction.VersionInstructionComposer
import com.orzechowski.aidme.creator.versiontree.VersionTreeComposer
import com.orzechowski.aidme.database.tag.Tag
import com.orzechowski.aidme.database.tag.TutorialTag
import com.orzechowski.aidme.imagebrowser.ImageBrowserLoader
import com.orzechowski.aidme.tutorial.database.TutorialLink
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound
import com.orzechowski.aidme.tutorial.version.database.Version
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction
import kotlin.concurrent.thread

class CreatorActivity : AppCompatActivity(R.layout.activity_creator),
    VersionTreeComposer.ActivityCallback, VersionInstructionComposer.ActivityCallback,
        MultimediaComposer.ActivityCallback, ImageBrowserLoader.ActivityCallback,
        SoundComposer.ActivityCallback, SoundBrowserLoader.ActivityCallback,
        InstructionComposer.ActivityCallback, NarrationBrowserLoader.ActivityCallback,
        CategoryAssignment.ActivityCallback, KeywordAssignment.ActivityCallback,
        TutorialLinkComposer.ActivityCallback
{
    private val mInstructionComposer = InstructionComposer(this)
    private val mMultimediaComposer = MultimediaComposer(this)
    private val mVersionComposer = VersionComposer()
    private val mSoundComposer = SoundComposer(this)
    private var mCategoryAssignment = CategoryAssignment(this)
    private var mKeywordAssignment = KeywordAssignment(this)
    private var mTutorialLinkComposer = TutorialLinkComposer(this)
    private lateinit var mView: ScrollView
    private lateinit var mSoundBrowser: SoundBrowserLoader
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var mNarrationBrowser: NarrationBrowserLoader
    private lateinit var mVersionTreeComposer: VersionTreeComposer
    private lateinit var mVersionInstructionComposer: VersionInstructionComposer
    private lateinit var mVersions: MutableList<Version>
    private lateinit var mMultimedias: List<Multimedia>
    private lateinit var mInstructions: List<InstructionSet>
    private lateinit var mSounds: List<TutorialSound>
    private lateinit var mTutorialLinks: List<TutorialLink>
    private lateinit var mVersionInstructions: Collection<VersionInstruction>
    private lateinit var mTutorialTags: List<TutorialTag>
    private lateinit var mKeywords: List<Keyword>
    private lateinit var mUniqueTag: Tag
    private var pickingMiniature = false
    private lateinit var miniatureFileUri: String
    private lateinit var mQueue: RequestQueue
    private lateinit var mProgressThread: Thread

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.creator_step_one_button).setOnClickListener {
            val instructions = mInstructionComposer.instructions
            var lengthRegex = true
            for(instruction in instructions) {
                if(instruction.instructions.length<4) lengthRegex = false
            }
            if(instructions.isEmpty() || !lengthRegex) {
                Toast.makeText(this, R.string.instructions_not_provided,
                    Toast.LENGTH_SHORT).show()
            } else {
                val versions = mVersionComposer.versions
                mVersions = if(versions.isEmpty()) {
                    mutableListOf(
                        Version(0, "", 0,
                            delayGlobalSound = true,
                            hasChildren = false,
                            hasParent = false,
                            parentVersionId = null
                        )
                    )
                } else versions
                mMultimedias = mMultimediaComposer.multimedias
                mInstructions = instructions
                mTutorialLinkComposer.setInstructions(mInstructions)
                mSounds = mSoundComposer.sounds
                supportFragmentManager.beginTransaction().remove(mInstructionComposer).commit()
                supportFragmentManager.beginTransaction().remove(mMultimediaComposer).commit()
                supportFragmentManager.beginTransaction().remove(mVersionComposer).commit()
                supportFragmentManager.beginTransaction().remove(mSoundComposer).commit()
                mVersionTreeComposer = VersionTreeComposer(mVersions, this)
                commitVersionTree()
                mView.visibility = View.GONE
            }
        }
        findViewById<Button>(R.id.tutorial_miniature_upload_button).setOnClickListener {
            pickingMiniature = true
        }
        mView = findViewById(R.id.initial_creator_view)
        commitInitial()
    }

    private fun commitInitial()
    {
        supportFragmentManager.commit {
            add(R.id.layout_version_creator, mVersionComposer)
            add(R.id.layout_instruction_creator, mInstructionComposer)
            add(R.id.layout_multimedia_creator, mMultimediaComposer)
            add(R.id.layout_sound_creator, mSoundComposer)
        }
    }

    private fun commitVersionTree()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mVersionTreeComposer)
        }
    }

    private fun commitVersionInstruction(versions: MutableList<Version>)
    {
        mVersionInstructionComposer = VersionInstructionComposer(versions, mInstructions,
            this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mVersionInstructionComposer)
        }
    }

    private fun commitCategoryAssignment()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mCategoryAssignment)
        }
    }

    override fun finalizeVersionTree(versions: MutableList<Version>)
    {
        mVersions = versions
        supportFragmentManager.beginTransaction().remove(mVersionTreeComposer).commit()
        commitVersionInstruction(versions)
    }

    override fun finalizeVersionInstructions(versionInstructions: Collection<VersionInstruction>)
    {
        mVersionInstructions = versionInstructions
        supportFragmentManager.beginTransaction().remove(mVersionInstructionComposer).commit()
        commitCategoryAssignment()
    }

    override fun callNarrationRecycler()
    {
        mView.visibility = View.GONE
        mNarrationBrowser = NarrationBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mNarrationBrowser)
        }
    }

    override fun callImageGallery()
    {
        mView.visibility = View.GONE
        mImageBrowser = ImageBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mImageBrowser)
        }
    }

    override fun callSoundRecycler()
    {
        mView.visibility = View.GONE
        mSoundBrowser = SoundBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mSoundBrowser)
        }
    }

    override fun narrationSubmitted(narration: Sound)
    {
        supportFragmentManager.beginTransaction().remove(mNarrationBrowser).commit()
        mInstructionComposer.instructions[mInstructionComposer.currentPosition].narrationFile =
            narration.displayName
        mInstructionComposer.resetAdapterElements()
        mView.visibility = View.VISIBLE
    }

    override fun imageSubmitted(uri: Uri)
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        if(!pickingMiniature) {
            mMultimediaComposer.multimedias[mMultimediaComposer.currentPosition].fileUriString =
                uri.toString()
            mMultimediaComposer.resetAdapterElements()
        } else {
            findViewById<ImageView>(R.id.tutorial_miniature).setImageURI(uri)
            miniatureFileUri = uri.toString()
        }
        mView.visibility = View.VISIBLE
    }

    override fun soundSubmitted(sound: Sound)
    {
        val position = mSoundComposer.currentPosition
        supportFragmentManager.beginTransaction().remove(mSoundBrowser).commit()
        mSoundComposer.sounds[position].fileName = sound.displayName
        mSoundComposer.resetAdapterElements()
        mView.visibility = View.VISIBLE
    }

    override fun onBackPressed()
    {
        val fragmentList: List<*> = supportFragmentManager.fragments
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        var handled = false
        for(f in fragmentList) {
            when(f) {
                is ImageBrowserLoader -> {
                    t.remove(mImageBrowser).commit()
                    mView.visibility = View.VISIBLE
                    handled = true
                }
                is VersionTreeComposer -> {
                    t.remove(mVersionTreeComposer).commit()
                    mView.visibility = View.VISIBLE
                    commitInitial()
                    handled = true
                }
                is VersionInstructionComposer -> {
                    t.remove(mVersionInstructionComposer).commit()
                    commitVersionTree()
                    handled = true
                }
                is CategoryAssignment -> {
                    if(f.level>1) {
                        mCategoryAssignment.restorePrevious()
                        handled = true
                    } else {
                        t.remove(mCategoryAssignment).commit()
                        mCategoryAssignment = CategoryAssignment(this)
                        if(mVersions.size>1) commitVersionInstruction(mVersions)
                        else {
                            mView.visibility = View.VISIBLE
                            commitInitial()
                        }
                        handled = true
                    }
                }
                is KeywordAssignment -> {
                    t.remove(mKeywordAssignment).commit()
                    commitCategoryAssignment()
                    handled = true
                }
                is SoundBrowserLoader -> {
                    t.remove(mSoundBrowser).commit()
                    mView.visibility = View.VISIBLE
                    handled = true
                }
                is TutorialLinkComposer -> {
                    if(f.mPrimaryLayout.visibility==View.VISIBLE) {
                        t.remove(mTutorialLinkComposer).commit()
                        commitKeywordAssignment()
                    } else f.back()
                    handled = true
                }
            }
        }
        if(!handled) super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==121) {
            callImageGallery()
        } else if(requestCode==122) {
            callSoundRecycler()
        }
    }

    override fun categorySelected(tutorialTags: MutableList<TutorialTag>, uniqueTag: Tag)
    {
        mUniqueTag = uniqueTag
        mTutorialTags = tutorialTags
        supportFragmentManager.beginTransaction().remove(mCategoryAssignment).commit()
        commitKeywordAssignment()
    }

    private fun commitKeywordAssignment()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mKeywordAssignment)
        }
    }

    override fun submitKeywords(keywords: MutableList<Keyword>)
    {
        mKeywords = keywords
        supportFragmentManager.beginTransaction().remove(mKeywordAssignment).commit()
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mTutorialLinkComposer)
        }
    }

    override fun finalizeTutorialLinks(tutorialLinks: MutableList<TutorialLink>?)
    {
        if(tutorialLinks!=null && tutorialLinks.isNotEmpty()) {
            mTutorialLinks = tutorialLinks
        }
        supportFragmentManager.beginTransaction().remove(mTutorialLinkComposer).commit()
        val uploadLayout: ConstraintLayout = findViewById(R.id.creator_uploading_data)
        uploadLayout.visibility = View.VISIBLE
        val uploaded = false
        val url = "https://aidme-326515.appspot.com/"
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        mQueue = RequestQueue(cache, network).apply {
            start()
        }
        for(multimedia in mMultimedias) {
            if (multimedia.type) {
                /**
                lateinit var byteArray: ByteArray
                try {
                    val uri = Uri.parse(multimedia.fileUriString)
                    val bitmap = when { Build.VERSION.SDK_INT > 28 ->
                            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver,
                                uri))
                        else -> MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    }
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                    byteArray = byteArrayOutputStream.toByteArray()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                if (byteArray.isNotEmpty()) {
                    val volleyMultipartRequest =
                        MultipartRequest(Request.Method.GET,
                            url + "tutorialcreationuploadimagemultimedia",
                            {
                                try {
                                    Toast.makeText(
                                        applicationContext, JSONObject(String(it.data))
                                            .getString("message"), Toast.LENGTH_SHORT
                                    ).show()
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            },
                            {
                                it.printStackTrace()
                            })
                    volleyMultipartRequest.setData(DataPart(System.currentTimeMillis()
                        .toString() + ".jpeg", byteArray, "binary"))
                    mQueue.add(volleyMultipartRequest)
                }*/
            }
        }
        mProgressThread = thread {
            val progressOne: View = findViewById(R.id.creator_upload_progress_1)
            val progressTwo: View = findViewById(R.id.creator_upload_progress_2)
            val progressThree: View = findViewById(R.id.creator_upload_progress_3)
            try {
                while (!uploaded) {
                    Thread.sleep(500)
                    this@CreatorActivity.runOnUiThread {
                        progressOne.visibility = View.VISIBLE
                    }
                    Thread.sleep(500)
                    this@CreatorActivity.runOnUiThread {
                        progressTwo.visibility = View.VISIBLE
                    }
                    Thread.sleep(500)
                    this@CreatorActivity.runOnUiThread {
                        progressThree.visibility = View.VISIBLE
                    }
                    Thread.sleep(500)
                    this@CreatorActivity.runOnUiThread {
                        progressOne.visibility = View.INVISIBLE
                        progressTwo.visibility = View.INVISIBLE
                        progressThree.visibility = View.INVISIBLE
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy()
    {
        mQueue.stop()
        mProgressThread.interrupt()
        super.onDestroy()
    }
}
