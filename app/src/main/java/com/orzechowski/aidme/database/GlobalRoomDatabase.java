package com.orzechowski.aidme.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.orzechowski.aidme.browser.categories.database.Category;
import com.orzechowski.aidme.browser.categories.database.CategoryDAO;
import com.orzechowski.aidme.browser.search.database.Keyword;
import com.orzechowski.aidme.browser.search.database.KeywordDAO;
import com.orzechowski.aidme.browser.search.database.TagKeyword;
import com.orzechowski.aidme.browser.search.database.TagKeywordDAO;
import com.orzechowski.aidme.browser.userrating.Rating;
import com.orzechowski.aidme.browser.userrating.RatingDAO;
import com.orzechowski.aidme.database.helper.Helper;
import com.orzechowski.aidme.database.helper.HelperDAO;
import com.orzechowski.aidme.database.tag.CategoryTag;
import com.orzechowski.aidme.database.tag.CategoryTagDAO;
import com.orzechowski.aidme.database.tag.HelperTag;
import com.orzechowski.aidme.database.tag.HelperTagDAO;
import com.orzechowski.aidme.database.tag.Tag;
import com.orzechowski.aidme.database.tag.TagDAO;
import com.orzechowski.aidme.database.tag.TutorialTag;
import com.orzechowski.aidme.database.tag.TutorialTagDAO;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialDAO;
import com.orzechowski.aidme.tutorial.database.TutorialLink;
import com.orzechowski.aidme.tutorial.database.TutorialLinkDAO;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimediaDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.VersionSound;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.VersionSoundDAO;
import com.orzechowski.aidme.tutorial.version.database.Version;
import com.orzechowski.aidme.tutorial.version.database.VersionDAO;
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction;
import com.orzechowski.aidme.tutorial.version.database.VersionInstructionDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Version.class, InstructionSet.class, Tutorial.class, VersionInstruction.class,
        TutorialSound.class, Helper.class, Multimedia.class, Category.class, TutorialLink.class,
        Tag.class, HelperTag.class, TutorialTag.class, CategoryTag.class, VersionMultimedia.class,
        Keyword.class, TagKeyword.class, VersionSound.class, Rating.class},
        version = 1, exportSchema = false)

public abstract class GlobalRoomDatabase extends RoomDatabase
{
    public abstract VersionDAO versionDao();
    public abstract InstructionSetDAO instructionDao();
    public abstract TutorialDAO tutorialDao();
    public abstract TutorialSoundDAO tutorialSoundDAO();
    public abstract HelperDAO helperDao();
    public abstract MultimediaDAO multimediaDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract VersionInstructionDAO versionInstructionDAO();
    public abstract VersionMultimediaDAO versionMultimediaDAO();
    public abstract TagDAO tagDAO();
    public abstract HelperTagDAO helperTagDAO();
    public abstract TutorialTagDAO tutorialTagDAO();
    public abstract CategoryTagDAO categoryTagDAO();
    public abstract KeywordDAO keywordDAO();
    public abstract TagKeywordDAO tagKeywordDAO();
    public abstract TutorialLinkDAO tutorialLinkDAO();
    public abstract VersionSoundDAO soundInVersionDAO();
    public abstract RatingDAO ratingDAO();

    private static volatile GlobalRoomDatabase INSTANCE;
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static GlobalRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GlobalRoomDatabase.class, "AidMe")
                    .addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            executor.execute(()-> {
                Populating populating = new Populating();
                TutorialDAO tutorialDAO = INSTANCE.tutorialDao();
                InstructionSetDAO instructionDAO = INSTANCE.instructionDao();
                VersionDAO versionDAO = INSTANCE.versionDao();
                TutorialSoundDAO tutorialSoundDAO = INSTANCE.tutorialSoundDAO();
                HelperDAO helperDAO = INSTANCE.helperDao();
                MultimediaDAO multimediaDAO = INSTANCE.multimediaDAO();
                CategoryDAO categoryDAO = INSTANCE.categoryDAO();
                VersionInstructionDAO versionInstructionDAO = INSTANCE.versionInstructionDAO();
                VersionMultimediaDAO versionMultimediaDAO = INSTANCE.versionMultimediaDAO();
                TagDAO tagDAO = INSTANCE.tagDAO();
                HelperTagDAO helperTagDAO = INSTANCE.helperTagDAO();
                TutorialTagDAO tutorialTagDAO = INSTANCE.tutorialTagDAO();
                CategoryTagDAO categoryTagDAO = INSTANCE.categoryTagDAO();
                KeywordDAO keywordDAO = INSTANCE.keywordDAO();
                TagKeywordDAO tagKeywordDAO = INSTANCE.tagKeywordDAO();
                TutorialLinkDAO tutorialLinkDAO = INSTANCE.tutorialLinkDAO();
                VersionSoundDAO versionSoundDAO = INSTANCE.soundInVersionDAO();

                populating.populateHelperTags(helperTagDAO);
                populating.populateHelpers(helperDAO);
                populating.populateTutorials(tutorialDAO);
                populating.populateVersions(versionDAO);
                populating.populateInstructionSets(instructionDAO);
                populating.populateCategories(categoryDAO);
                populating.populateTags(tagDAO);
                populating.populateCategoryTags(categoryTagDAO);
                populating.populateTutorialTags(tutorialTagDAO);
                populating.populateKeywords(keywordDAO);
                populating.populateTagKeywords(tagKeywordDAO);
                populating.populateVersionInstructions(versionInstructionDAO);
                populating.populateMultimedia(multimediaDAO);
                populating.populateMultimediaInVersion(versionMultimediaDAO);
                populating.populateSounds(tutorialSoundDAO);
                populating.populateLinks(tutorialLinkDAO);
                populating.populateVersionSounds(versionSoundDAO);
            });
        }
    };
}
