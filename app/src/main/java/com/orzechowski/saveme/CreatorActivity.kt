package com.orzechowski.saveme

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.google.gson.Gson
import com.orzechowski.saveme.browser.search.database.Keyword
import com.orzechowski.saveme.creator.GsonConverter
import com.orzechowski.saveme.creator.categorypicker.CategoryAssignment
import com.orzechowski.saveme.creator.initial.InstructionComposer
import com.orzechowski.saveme.creator.initial.MultimediaComposer
import com.orzechowski.saveme.creator.initial.SoundComposer
import com.orzechowski.saveme.creator.initial.VersionComposer
import com.orzechowski.saveme.creator.initial.soundbrowser.Sound
import com.orzechowski.saveme.creator.initial.soundbrowser.SoundBrowserLoader
import com.orzechowski.saveme.creator.initial.soundbrowser.narrationbrowser.NarrationBrowserLoader
import com.orzechowski.saveme.creator.keywordassignment.KeywordAssignment
import com.orzechowski.saveme.creator.tutoriallinks.TutorialLinkComposer
import com.orzechowski.saveme.creator.versioninstruction.VersionInstructionComposer
import com.orzechowski.saveme.creator.versionmultimedia.VersionMultimediaComposer
import com.orzechowski.saveme.creator.versionsound.VersionSoundComposer
import com.orzechowski.saveme.creator.versiontree.VersionTreeComposer
import com.orzechowski.saveme.database.tag.Tag
import com.orzechowski.saveme.database.tag.TutorialTag
import com.orzechowski.saveme.imagebrowser.ImageBrowserLoader
import com.orzechowski.saveme.tutorial.database.Tutorial
import com.orzechowski.saveme.tutorial.database.TutorialLink
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia
import com.orzechowski.saveme.tutorial.multimedia.database.VersionMultimedia
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound
import com.orzechowski.saveme.tutorial.sound.database.VersionSound
import com.orzechowski.saveme.tutorial.version.database.Version
import com.orzechowski.saveme.tutorial.version.database.VersionInstruction
import com.orzechowski.saveme.volley.StringPost
import net.gotev.uploadservice.UploadServiceConfig
import net.gotev.uploadservice.protocols.multipart.MultipartUploadRequest
import java.util.*

//Aktywność w której tworzone są poradniki. Począwszy od wpisania tytyłu i wybrania miniaturki,
//kończywszy na przesłaniu całej zawartości na serwer. Klasy podlegające tej aktywności znajdują
//się w com.orzechowski.saveme.creator. Klasa korzysta z przeglądarki zdjęć z pamięci telefonu,
//znajdującej się w com.orzechowski.saveme.imagebrowser oraz z konfiguracji kanału powiadomień
//mieszczącej się w com.orzechowski.saveme.volley.
class CreatorActivity : AppCompatActivity(R.layout.activity_creator),
    VersionTreeComposer.ActivityCallback, VersionInstructionComposer.ActivityCallback,
        MultimediaComposer.ActivityCallback, ImageBrowserLoader.ActivityCallback,
        SoundComposer.ActivityCallback, SoundBrowserLoader.ActivityCallback,
        InstructionComposer.ActivityCallback, NarrationBrowserLoader.ActivityCallback,
        CategoryAssignment.ActivityCallback, KeywordAssignment.ActivityCallback,
        TutorialLinkComposer.ActivityCallback, VersionMultimediaComposer.ActivityCallback,
        VersionSoundComposer.ActivityCallback
{
    private val mInstructionComposer = InstructionComposer(this)
    private val mMultimediaComposer = MultimediaComposer(this)
    private val mVersionComposer = VersionComposer()
    private val mSoundComposer = SoundComposer(this)
    private val mUrl = "https://aidme-326515.appspot.com/"
    private val mSoundUris = mutableMapOf<Int, String>()
    private val mNarrationUris = mutableMapOf<Int, String>()
    private val mGson = Gson()
    private val mGsonConverter = GsonConverter()
    private var mCategoryAssignment = CategoryAssignment(this)
    private var mKeywordAssignment = KeywordAssignment(this)
    private var mTutorialLinkComposer = TutorialLinkComposer(this)
    private var pickingMiniature = false
    private var mInstructionUpload = false
    private var mVersionUpload = false
    private var mVersionInstructionUpload = false
    private var mNarrationUpload = false
    private var mTutorialLinkUpload = false
    private var mTagUpload = false
    private var mVersionMultimediaUpload = false
    private var mMultimediaUpload = false
    private var mSoundUpload = false
    private var mVersionSoundUpload = false
    private var mKeywordUpload = false
    private var mMultimediaInfoUpload = false
    private var mSoundInfoUpload = false
    private var mMiniatureUpload = false
    private var mTutorialId = -1L
    private val mMiniatureId = UUID.randomUUID().toString()
    private lateinit var mView: ScrollView
    private lateinit var mSoundBrowser: SoundBrowserLoader
    private lateinit var mImageBrowser: ImageBrowserLoader
    private lateinit var mNarrationBrowser: NarrationBrowserLoader
    private lateinit var mVersionTreeComposer: VersionTreeComposer
    private lateinit var mVersionInstructionComposer: VersionInstructionComposer
    private lateinit var mVersionMultimediaComposer: VersionMultimediaComposer
    private lateinit var mVersionSoundComposer: VersionSoundComposer
    private lateinit var mVersions: MutableList<Version>
    private lateinit var mMultimedia: List<Multimedia>
    private lateinit var mInstructions: List<InstructionSet>
    private lateinit var mSounds: List<TutorialSound>
    private lateinit var mTutorialLinks: List<TutorialLink>
    private lateinit var mVersionInstructions: Collection<VersionInstruction>
    private lateinit var mVersionMultimedia: MutableList<VersionMultimedia>
    private lateinit var mVersionSounds: MutableList<VersionSound>
    private lateinit var mTutorialTags: List<TutorialTag>
    private lateinit var mKeywords: List<Keyword>
    private lateinit var mUniqueTag: Tag
    private lateinit var mMiniatureFileUri: String
    private lateinit var mTutorialTitle: String
    private lateinit var mQueue: RequestQueue
    private lateinit var mEmail: String
    private val mProgressThread = Thread {
        val layout: ConstraintLayout = findViewById(R.id.creator_uploading_data)
        this@CreatorActivity.runOnUiThread {
            layout.visibility = View.VISIBLE
        }
        val progressOne: View = findViewById(R.id.creator_upload_progress_1)
        val progressTwo: View = findViewById(R.id.creator_upload_progress_2)
        val progressThree: View = findViewById(R.id.creator_upload_progress_3)
        var timeout = 0
        try {
            while (timeout < 60 && !completedUpload()) {
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
                timeout++
            }
            this@CreatorActivity.runOnUiThread {
                layout.visibility = View.INVISIBLE
            }
        } catch (e: InterruptedException) {
            this@CreatorActivity.runOnUiThread {
                layout.visibility = View.INVISIBLE
            }
            Toast.makeText(this, getString(R.string.interrupted_upload), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        mEmail = intent.getStringExtra("email") ?: "f"
        findViewById<Button>(R.id.creator_step_one_button).setOnClickListener {
            mInstructions = mInstructionComposer.instructions
            mMultimedia = mMultimediaComposer.multimedias
            mSounds = mSoundComposer.sounds
            var lengthRegex = true
            var displayMedia = true
            var soundLogicalTime = true
            val tutorialName = this::mTutorialTitle.isInitialized
            for(instruction in mInstructions) {
                if(instruction.instructions.length < 4) lengthRegex = false
            }
            for(multimedia in mMultimedia) {
                if(multimedia.displayTime <= 0) displayMedia = false
            }
            for(sound in mSounds) {
                if(sound.soundStart < 0 || sound.interval < 0) soundLogicalTime = false
            }
            if(mInstructions.isEmpty() || !lengthRegex) {
                Toast.makeText(this, R.string.instructions_not_provided,
                    Toast.LENGTH_SHORT).show()
            } else if(!displayMedia) {
                Toast.makeText(this, R.string.media_length_not_set,
                    Toast.LENGTH_SHORT)
                    .show()
            } else if(!soundLogicalTime) {
                Toast.makeText(this, R.string.illogical_sound_time,
                    Toast.LENGTH_SHORT).show()
            } else if(!tutorialName) {
                Toast.makeText(this, R.string.tutorial_name_not_set,
                    Toast.LENGTH_SHORT).show()
            } else {
                val versions = mVersionComposer.versions
                mVersions = if(versions.isEmpty()) {
                    mutableListOf(Version(0, "", 0,
                            delayGlobalSound = true,
                            hasChildren = false,
                            hasParent = false,
                            parentVersionId = -1L
                        )
                    )
                } else versions
                mTutorialLinkComposer.setInstructions(mInstructions)
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
            callImageGallery()
            pickingMiniature = true
        }
        mView = findViewById(R.id.initial_creator_view)
        commitInitial()
        findViewById<EditText>(R.id.tutorial_title_input).addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?)
                {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
                {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
                {
                    if(!s.isNullOrEmpty() && s.length > 4) {
                        mTutorialTitle = s.toString()
                    }
                }
            }
        )
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

    override fun imageSubmitted(uri: Uri)
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        val type = contentResolver.getType(uri)
        var boolType = false
        if(type == "image/jpeg" || type == "image/bmp" || type == "image/gif" ||
            type == "image/jpg" || type == "image/png") {
            boolType = true
        }
        if(!pickingMiniature) {
            val multimedia = mMultimediaComposer.multimedias[mMultimediaComposer.currentPosition]
            if(boolType) multimedia.type = true
            if(boolType || type == "video/wav" || type == "video/mp4") multimedia.fileName =
                uri.toString()
            else Toast.makeText(this, R.string.multimedia_bad_format, Toast.LENGTH_SHORT)
                .show()
                mMultimediaComposer.resetAdapterElements()
        } else {
            if(boolType) {
                findViewById<ImageView>(R.id.tutorial_miniature).setImageURI(uri)
                mMiniatureFileUri = uri.toString()
                pickingMiniature = false
            } else {
                Toast.makeText(this, R.string.miniature_bad_format, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        mView.visibility = View.VISIBLE
    }

    override fun refreshGallery()
    {
        supportFragmentManager.beginTransaction().remove(mImageBrowser).commit()
        mImageBrowser = ImageBrowserLoader(this)
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mImageBrowser)
        }
    }

    override fun soundSubmitted(sound: Sound)
    {
        val position = mSoundComposer.currentPosition
        supportFragmentManager.beginTransaction().remove(mSoundBrowser).commit()
        mSoundComposer.sounds[position].fileName = sound.displayName
        mSoundComposer.resetAdapterElements()
        mSoundUris[position] = sound.uri.toString()
        mView.visibility = View.VISIBLE
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

    private fun commitVersionMultimediaComposer()
    {
        mVersionMultimediaComposer = VersionMultimediaComposer(mVersions, mMultimedia, this)
        if(mMultimedia.isNotEmpty()) {
            if(mVersions.size > 1) {
                supportFragmentManager.commit {
                    add(R.id.fragment_overlay_layout, mVersionMultimediaComposer)
                }
            } else {
                mVersionMultimedia = mutableListOf()
                for(multimedia in mMultimedia) {
                    mVersionMultimedia.add(VersionMultimedia(0,
                        multimedia.multimediaId, 0))
                }
                commitVersionSoundComposer()
            }
        } else {
            commitVersionSoundComposer()
        }
    }

    private fun commitVersionSoundComposer()
    {
        mVersionSoundComposer = VersionSoundComposer(mVersions, mSounds, this)
        if(mSounds.isNotEmpty()) {
            if(mVersions.size > 1) {
                supportFragmentManager.commit {
                    add(R.id.fragment_overlay_layout, mVersionSoundComposer)
                }
            } else {
                mVersionSounds = mutableListOf()
                for(sound in mSounds) {
                    mVersionSounds.add(VersionSound(0, sound.soundId, 0))
                }
                commitCategoryAssignment()
            }
        } else {
            commitCategoryAssignment()
        }
    }

    private fun commitKeywordAssignment()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mKeywordAssignment)
        }
    }

    private fun commitTutorialLinks()
    {
        supportFragmentManager.commit {
            add(R.id.fragment_overlay_layout, mTutorialLinkComposer)
        }
    }

    override fun narrationSubmitted(narration: Sound)
    {
        supportFragmentManager.beginTransaction().remove(mNarrationBrowser).commit()
        val position = mInstructionComposer.currentPosition
        mInstructionComposer.instructions[position].narrationFile = narration.displayName
        mNarrationUris[position] = narration.uri.toString()
        mInstructionComposer.resetAdapterElements()
        mView.visibility = View.VISIBLE
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
        commitVersionMultimediaComposer()
    }

    override fun finalizeCategory(tutorialTags: MutableList<TutorialTag>, uniqueTag: Tag)
    {
        mUniqueTag = uniqueTag
        mTutorialTags = tutorialTags
        supportFragmentManager.beginTransaction().remove(mCategoryAssignment).commit()
        commitKeywordAssignment()
    }

    override fun finalizeKeywords(keywords: MutableList<Keyword>)
    {
        mKeywords = keywords
        supportFragmentManager.beginTransaction().remove(mKeywordAssignment).commit()
        commitTutorialLinks()
    }

    override fun finalizeVersionMultimedia(versionMultimedia: MutableList<VersionMultimedia>)
    {
        mVersionMultimedia = versionMultimedia
        supportFragmentManager.beginTransaction().remove(mVersionMultimediaComposer).commit()
        commitVersionSoundComposer()
    }

    override fun finalizeVersionSounds(versionSounds: MutableList<VersionSound>)
    {
        mVersionSounds = versionSounds
        supportFragmentManager.beginTransaction().remove(mVersionSoundComposer).commit()
        commitCategoryAssignment()
    }

    override fun finalizeTutorialLinks(tutorialLinks: MutableList<TutorialLink>?)
    {
        UploadServiceConfig.initialize(application,
            getString(R.string.default_notification_channel_id), false)
        if(tutorialLinks != null && tutorialLinks.isNotEmpty()) {
            mTutorialLinks = tutorialLinks
        }
        supportFragmentManager.beginTransaction().remove(mTutorialLinkComposer).commit()
        val cache = DiskBasedCache(cacheDir, 1024*1024)
        val network = BasicNetwork(HurlStack())
        mQueue = RequestQueue(cache, network).apply { start() }
        mProgressThread.start()
        uploadTutorial()
        uploadMultimedia()
        uploadMiniature()
        uploadNarrations()
        uploadSounds()
    }

    private fun completedUpload() : Boolean
    {
        return mVersionMultimediaUpload && mVersionSoundUpload && mVersionInstructionUpload &&
                mKeywordUpload && mTutorialLinkUpload && mInstructionUpload && mSoundInfoUpload &&
                mMultimediaInfoUpload
    }

    private fun uploadTutorial()
    {
        mQueue.add(StringPost(Request.Method.POST, mUrl + "create/tutorial/" + mEmail,
            {
                mTutorialId = it.toLong()
                if(mTutorialId != -1L) {
                    for (multimedia in mMultimedia) multimedia.tutorialId = mTutorialId
                    for (sound in mSounds) sound.tutorialId = mTutorialId
                    for (instruction in mInstructions) instruction.tutorialId = mTutorialId
                    for (tutorialLink in mTutorialLinks) tutorialLink.originId = mTutorialId
                    for (version in mVersions) version.tutorialId = mTutorialId
                    for (tag in mTutorialTags) tag.tutorialId = mTutorialId
                    uploadInstructions()
                    uploadTutorialLinks()
                    uploadVersions()
                    uploadTags()
                    uploadMultimediaInfo()
                    uploadSoundInfo()
                }
            }, {
                if(it.message != null) {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", it.message!!)
                }
            }).also { it.setRequestBody(mGson.toJson(Tutorial(0, mTutorialTitle, 0,
            mMiniatureId, 0F))) })
    }

    private fun uploadInstructions()
    {
        if(!mInstructionUpload) {
            if (this::mInstructions.isInitialized && mInstructions.isNotEmpty()) {
                val request = StringPost(Request.Method.POST, mUrl + "create/instructions",
                    {
                        mInstructionUpload = true
                        if (mVersionUpload) uploadVersionInstructions()
                    }, {
                        if (it.message != null) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("error", it.message!!)
                        }
                    }
                ).also { it.setRequestBody(mGson.toJson(mInstructions)) }
                mQueue.add(request)
            } else {
                Toast.makeText(this@CreatorActivity, getString(R.string
                    .unexpected_missing_instructions), Toast.LENGTH_SHORT).show()
                val request = StringPost(Request.Method.POST, mUrl + "create/uploaderror",
                {
                    mInstructionUpload = true
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }).also { it.setRequestBody(mTutorialId.toString()) }
                mQueue.add(request)
            }
        }
    }

    private fun uploadMultimediaInfo()
    {
        if(!mMultimediaInfoUpload) {
            if(this::mMultimedia.isInitialized && mMultimedia.isNotEmpty()) {
                val request = StringPost(Request.Method.POST, mUrl + "create/multimedia",
                {
                    mMultimediaInfoUpload = true
                    val multimedia = mGsonConverter.obtainMultimedia(it)
                    for (i in 0 until multimedia.size) {
                        mMultimedia[i].multimediaId = multimedia[i].multimediaId
                    }
                    if (mVersionUpload) uploadVersionMultimedia()
                }, {
                    if (it.message != null) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        Log.e("error", it.message!!)
                    }
                }).also { it.setRequestBody(mGson.toJson(mMultimedia)) }
                mQueue.add(request)
            } else {
                mMultimediaUpload = true
            }
        }
    }

    private fun uploadSoundInfo()
    {
        if(!mSoundInfoUpload) {
            if(this::mSounds.isInitialized && mSounds.isNotEmpty()) {
                val request = StringPost(Request.Method.POST, mUrl + "create/sounds", {
                    mSoundInfoUpload = true
                    val sounds = mGsonConverter.obtainSounds(it)
                    for (i in 0 until sounds.size) {
                        mSounds[i].soundId = sounds[i].soundId
                    }
                    if (mVersionUpload) uploadVersionSounds()
                }, {
                    if (it.message != null) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        Log.e("error", it.message!!)
                    }
                }).also { it.setRequestBody(mGson.toJson(mSounds)) }
                mQueue.add(request)
            } else {
                mSoundInfoUpload = true
            }
        }
    }

    private fun uploadVersions()
    {
        if(!mVersionUpload) {
            if(!this::mVersions.isInitialized || mVersions.isEmpty()) {
                mVersions.add(Version(0, "default", mTutorialId,
                    delayGlobalSound = false,
                    hasChildren = false,
                    hasParent = false, -1))
            }
            val request = StringPost(Request.Method.POST, mUrl + "create/versions", {
                mVersionUpload = true
                val versions = mGsonConverter.obtainVersions(it)
                for (i in 0 until versions.size) {
                    val id = mVersions[i].versionId
                    val newId = versions[i].versionId
                    mVersions[i].versionId = newId
                    for (versionInstruction in mVersionInstructions) {
                        if (versionInstruction.versionId == id) {
                            versionInstruction.versionId = newId
                        }
                    }
                    if(this::mVersionMultimedia.isInitialized) {
                        for (versionMultimedia in mVersionMultimedia) {
                            if (versionMultimedia.versionId == id) {
                                versionMultimedia.versionId = newId
                            }
                        }
                    }
                    if(this::mVersionSounds.isInitialized) {
                        for (versionSound in mVersionSounds) {
                            if (versionSound.versionId == id) {
                                versionSound.versionId = newId
                            }
                        }
                    }
                }
                if (mSoundInfoUpload) uploadVersionSounds()
                if (mInstructionUpload) uploadVersionInstructions()
                if (mMultimediaInfoUpload) uploadVersionMultimedia()
            }, {
                if (it.message != null) {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.e("error", it.message!!)
                }
            }).also { it.setRequestBody(mGson.toJson(mVersions)) }
            mQueue.add(request)
        }
    }

    private fun uploadVersionInstructions()
    {
        if(!mVersionInstructionUpload) {
            if(this::mVersionInstructions.isInitialized && mVersionInstructions.isNotEmpty()) {
                val request = StringPost(Request.Method.POST, mUrl +
                        "create/versioninstructions", {
                        mVersionInstructionUpload = true
                    },
                    {
                        if (it.message != null) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("error", it.message!!)
                        }
                    }
                ).also { it.setRequestBody(mGson.toJson(mVersionInstructions)) }
                mQueue.add(request)
            } else {
                val request = StringPost(Request.Method.POST, mUrl + "create/uploaderror",
                {
                    mVersionInstructionUpload = true
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }).also { it.setRequestBody(mTutorialId.toString()) }
                mQueue.add(request)
            }
        }
    }

    private fun uploadTutorialLinks()
    {
        if(!mTutorialLinkUpload) {
            if(this::mTutorialLinks.isInitialized && mTutorialLinks.isNotEmpty()) {
                mQueue.add(StringPost(Request.Method.POST, mUrl + "create/tutoriallinks",
                    {
                        mTutorialLinkUpload = true
                    }, {
                        if (it.message != null) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("error", it.message!!)
                        }
                    }
                ).also { it.setRequestBody(mGson.toJson(mTutorialLinks)) })
            } else {
                mTutorialLinkUpload = true
            }
        }
    }

    private fun uploadTags()
    {
        if(!mTagUpload) {
            if(this::mTutorialTags.isInitialized && mTutorialTags.isNotEmpty()) {
                mQueue.add(StringPost(Request.Method.POST, mUrl + "create/tutorialtags/" +
                        mTutorialId + "/" + mUniqueTag.tagName + "/" + mUniqueTag.tagLevel,
                    {
                        mTagUpload = true
                        uploadKeywords(it.toLong())
                    }, {
                        if (it.message != null) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("error", it.message!!)
                        }
                    }
                ).also { it.setRequestBody(mGson.toJson(mTutorialTags)) })
            } else {
                val request = StringPost(Request.Method.POST, mUrl + "create/uploaderror",
                {
                    mTagUpload = true
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }).also { it.setRequestBody(mTutorialId.toString()) }
                mQueue.add(request)
            }
        }
    }

    private fun uploadVersionMultimedia()
    {
        if(!mVersionMultimediaUpload) {
            if (this::mVersionMultimedia.isInitialized && mVersionMultimedia.isNotEmpty()) {
                mQueue.add(StringPost(Request.Method.POST, mUrl + "create/versionmultimedia",
                    {
                        mVersionMultimediaUpload = true
                    }, {
                        if (it.message != null) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("error", it.message!!)
                        }
                    }).also { it.setRequestBody(mGson.toJson(mVersionMultimedia)) }
                )
            } else {
                mVersionMultimediaUpload = true
            }
        }
    }

    private fun uploadVersionSounds()
    {
        if(!mVersionSoundUpload) {
            if(this::mVersionSounds.isInitialized && mVersionSounds.isNotEmpty()) {
                mQueue.add(StringPost(Request.Method.POST, mUrl + "create/versionsounds",
                    {
                        mVersionSoundUpload = true
                    }, {
                        if (it.message != null) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            Log.e("error", it.message!!)
                        }
                    }).also { it.setRequestBody(mGson.toJson(mVersionSounds)) }
                )
            } else {
                mVersionSoundUpload = true
            }
        }
    }

    private fun uploadKeywords(uniqueTagId: Long)
    {
        if(!mKeywordUpload) {
            if(this::mKeywords.isInitialized && mKeywords.isNotEmpty()) {
                mQueue.add(StringPost(Request.Method.POST, mUrl + "create/keywords/" +
                        uniqueTagId, {
                    mKeywordUpload = true
                }, {
                    if (it.message != null) {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        Log.e("error", it.message!!)
                    }
                }).also { it.setRequestBody(mGson.toJson(mKeywords)) })
            } else {
                mKeywordUpload = true
            }
        }
    }

    private fun uploadNarrations()
    {
        if(!mNarrationUpload) {
            for(instruction in mInstructions) {
                val fileLocation = mNarrationUris[instruction.instructionSetId.toInt()] ?: ""
                if(fileLocation.isNotEmpty()) {
                    instruction.narrationFile = UUID.randomUUID().toString()
                    try {
                        MultipartUploadRequest(this@CreatorActivity,
                        mUrl + "upload/narration" + instruction.narrationFile)
                            .setMaxRetries(10).addFileToUpload(fileLocation, "file")
                            .setMethod("POST").setNotificationConfig { context, uploadId ->
                                UploadServiceConfig.notificationConfigFactory(context, uploadId)
                            }.startUpload()
                    } catch(e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun uploadMiniature()
    {
        if(!mMiniatureUpload) {
            if(this::mMiniatureFileUri.isInitialized) {
                try {
                    MultipartUploadRequest(this@CreatorActivity, mUrl +
                            "upload/image/" + mMiniatureId).setMaxRetries(10)
                        .addFileToUpload(mMiniatureFileUri, "file").setMethod("POST")
                        .setNotificationConfig { context, uploadId ->
                            UploadServiceConfig.notificationConfigFactory(context, uploadId)
                        }.startUpload()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun uploadMultimedia()
    {
        if(!mMultimediaUpload) {
            var uri: String
            var id: String
            for(multimedia in mMultimedia) {
                uri = multimedia.fileName
                id = UUID.randomUUID().toString()
                multimedia.fileName = id
                if(multimedia.type) {
                    try {
                        MultipartUploadRequest(this@CreatorActivity,
                            mUrl + "upload/image/" + id)
                            .setMaxRetries(10).addFileToUpload(uri, "file")
                            .setMethod("POST").setNotificationConfig { context, uploadId ->
                            UploadServiceConfig.notificationConfigFactory(context, uploadId)
                        }.startUpload()
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                } else {
                    try {
                        MultipartUploadRequest(this@CreatorActivity, mUrl +
                                "upload/video" + id).setMaxRetries(10).addFileToUpload(uri,
                            "file").setMethod("POST")
                            .setNotificationConfig { context, uploadId ->
                            UploadServiceConfig.notificationConfigFactory(context, uploadId)
                        }.startUpload()
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun uploadSounds()
    {
        if(!mSoundUpload) {
            for(sound in mSounds) {
                sound.fileName = UUID.randomUUID().toString()
                try {
                    MultipartUploadRequest(this@CreatorActivity, mUrl +
                            "upload/sound/" + sound.fileName).setMaxRetries(10)
                        .addFileToUpload(mSoundUris[sound.soundId.toInt()] ?: "",
                            "file").setMethod("POST")
                        .setNotificationConfig { context, uploadId ->
                            UploadServiceConfig.notificationConfigFactory(context, uploadId)
                        }.startUpload()
                } catch(e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
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
                    pickingMiniature = false
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
                    if(f.level > 1) {
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
                    if(f.mPrimaryLayout.visibility == View.VISIBLE) {
                        t.remove(mTutorialLinkComposer).commit()
                        commitKeywordAssignment()
                    } else f.back()
                    handled = true
                }
            }
        }
        if(!handled && mProgressThread.isAlive) {
            mProgressThread.interrupt()
            if(mTutorialId != -1L) {
                val request = StringPost(Request.Method.POST, mUrl + "create/uploaderror",
                {
                    super.onBackPressed()
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }).also { it.setRequestBody(mTutorialId.toString()) }
                mQueue.add(request)
            }
            handled = true
        }
        if(!handled) super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 121) {
            callImageGallery()
        } else if(requestCode == 122) {
            callSoundRecycler()
        }
    }

    override fun onDestroy()
    {
        if(this::mQueue.isInitialized) mQueue.stop()
        if(mProgressThread.isAlive) mProgressThread.interrupt()
        super.onDestroy()
    }
}
