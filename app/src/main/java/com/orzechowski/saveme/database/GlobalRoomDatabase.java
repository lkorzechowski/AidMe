package com.orzechowski.saveme.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.orzechowski.saveme.browser.categories.database.Category;
import com.orzechowski.saveme.browser.categories.database.CategoryDAO;
import com.orzechowski.saveme.browser.search.database.Keyword;
import com.orzechowski.saveme.browser.search.database.KeywordDAO;
import com.orzechowski.saveme.browser.search.database.TagKeyword;
import com.orzechowski.saveme.browser.search.database.TagKeywordDAO;
import com.orzechowski.saveme.browser.userrating.Rating;
import com.orzechowski.saveme.browser.userrating.RatingDAO;
import com.orzechowski.saveme.database.tag.CategoryTag;
import com.orzechowski.saveme.database.tag.CategoryTagDAO;
import com.orzechowski.saveme.database.tag.HelperTag;
import com.orzechowski.saveme.database.tag.HelperTagDAO;
import com.orzechowski.saveme.database.tag.Tag;
import com.orzechowski.saveme.database.tag.TagDAO;
import com.orzechowski.saveme.database.tag.TutorialTag;
import com.orzechowski.saveme.database.tag.TutorialTagDAO;
import com.orzechowski.saveme.helper.database.Email;
import com.orzechowski.saveme.helper.database.EmailDAO;
import com.orzechowski.saveme.helper.database.Helper;
import com.orzechowski.saveme.helper.database.HelperDAO;
import com.orzechowski.saveme.settings.database.Preference;
import com.orzechowski.saveme.settings.database.PreferenceDAO;
import com.orzechowski.saveme.tutorial.database.Tutorial;
import com.orzechowski.saveme.tutorial.database.TutorialDAO;
import com.orzechowski.saveme.tutorial.database.TutorialLink;
import com.orzechowski.saveme.tutorial.database.TutorialLinkDAO;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSetDAO;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;
import com.orzechowski.saveme.tutorial.multimedia.database.MultimediaDAO;
import com.orzechowski.saveme.tutorial.multimedia.database.VersionMultimedia;
import com.orzechowski.saveme.tutorial.multimedia.database.VersionMultimediaDAO;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSoundDAO;
import com.orzechowski.saveme.tutorial.sound.database.VersionSound;
import com.orzechowski.saveme.tutorial.sound.database.VersionSoundDAO;
import com.orzechowski.saveme.tutorial.version.database.Version;
import com.orzechowski.saveme.tutorial.version.database.VersionDAO;
import com.orzechowski.saveme.tutorial.version.database.VersionInstruction;
import com.orzechowski.saveme.tutorial.version.database.VersionInstructionDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Version.class, InstructionSet.class, Tutorial.class, VersionInstruction.class,
        TutorialSound.class, Helper.class, Multimedia.class, Category.class, TutorialLink.class,
        Tag.class, HelperTag.class, TutorialTag.class, CategoryTag.class, VersionMultimedia.class,
        Keyword.class, TagKeyword.class, VersionSound.class, Rating.class, Email.class,
        Preference.class}, version = 1, exportSchema = false)

public abstract class GlobalRoomDatabase extends RoomDatabase
{
    public abstract VersionDAO versionDAO();
    public abstract InstructionSetDAO instructionDAO();
    public abstract TutorialDAO tutorialDAO();
    public abstract TutorialSoundDAO tutorialSoundDAO();
    public abstract HelperDAO helperDAO();
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
    public abstract EmailDAO emailDAO();
    public abstract PreferenceDAO preferenceDAO();

    private static volatile GlobalRoomDatabase INSTANCE;
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static GlobalRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GlobalRoomDatabase.class, "SaveMe")
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
                PreferenceDAO preferenceDAO = INSTANCE.preferenceDAO();
                preferenceDAO.insert(new Preference(0, false));
            });
        }
    };
}
