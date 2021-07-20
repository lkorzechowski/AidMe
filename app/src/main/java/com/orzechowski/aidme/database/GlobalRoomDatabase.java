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
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaInVersion;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaInVersionDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundDAO;
import com.orzechowski.aidme.tutorial.version.database.Version;
import com.orzechowski.aidme.tutorial.version.database.VersionDAO;
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction;
import com.orzechowski.aidme.tutorial.version.database.VersionInstructionDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Version.class, InstructionSet.class, Tutorial.class, VersionInstruction.class,
        TutorialSound.class, Helper.class, Multimedia.class, Category.class, MultimediaInVersion.class,
        Tag.class, HelperTag.class, TutorialTag.class, CategoryTag.class, Keyword.class, TagKeyword.class},
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
    public abstract MultimediaInVersionDAO multimediaInVersionDAO();
    public abstract TagDAO tagDAO();
    public abstract HelperTagDAO helperTagDAO();
    public abstract TutorialTagDAO tutorialTagDAO();
    public abstract CategoryTagDAO categoryTagDAO();
    public abstract KeywordDAO keywordDAO();
    public abstract TagKeywordDAO tagKeywordDAO();

    private static volatile GlobalRoomDatabase INSTANCE;

    public static GlobalRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GlobalRoomDatabase.class, "AidMe")
                    .addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //hard coded database content injection
            databaseWriteExecutor.execute(()-> {
                TutorialDAO tutorialDAO = INSTANCE.tutorialDao();
                InstructionSetDAO instructionDAO = INSTANCE.instructionDao();
                VersionDAO versionDAO = INSTANCE.versionDao();
                TutorialSoundDAO tutorialSoundDAO = INSTANCE.tutorialSoundDAO();
                HelperDAO helperDAO = INSTANCE.helperDao();
                MultimediaDAO multimediaDAO = INSTANCE.multimediaDAO();
                CategoryDAO categoryDAO = INSTANCE.categoryDAO();
                VersionInstructionDAO versionInstructionDAO = INSTANCE.versionInstructionDAO();
                MultimediaInVersionDAO multimediaInVersionDAO = INSTANCE.multimediaInVersionDAO();
                TagDAO tagDAO = INSTANCE.tagDAO();
                HelperTagDAO helperTagDAO = INSTANCE.helperTagDAO();
                TutorialTagDAO tutorialTagDAO = INSTANCE.tutorialTagDAO();
                CategoryTagDAO categoryTagDAO = INSTANCE.categoryTagDAO();
                KeywordDAO keywordDAO = INSTANCE.keywordDAO();
                TagKeywordDAO tagKeywordDAO = INSTANCE.tagKeywordDAO();

                //beginning of helpers
                helperDAO.insert(new Helper(0L, "Ania", "Kozłowska", "", "Studentka"));
                helperDAO.insert(new Helper(1L, "Łukasz", "Orzechowski", "", "Twórca"));
                helperDAO.insert(new Helper(2L, "Kasia", "Kulpa", "", "Studentka"));

                //beginning of CPR tutorial
                tutorialDAO.insert(new Tutorial(0L, "Masaż serca", 1L, "heart_massage.jpg"));

                instructionDAO.insert(new InstructionSet(0L, "Wstęp", "Jeżeli ofiara nie jest w stanie samodzielnie oddychać…", 5000, 0L, 0));
                instructionDAO.insert(new InstructionSet(1L, "Ułożenie ofiary", "Upewnij się, że ofiara leży na plecach, jest ułożona prosto, a jej drogi oddechowe są udrożnione.", 8000, 0L, 1));
                instructionDAO.insert(new InstructionSet(2L, "Pozycja do udzielania pomocy", "Uklęknij wygodnie nad ofiarą tak, by twoje dłonie mogły być wyprostowane prostopadle do jej klatki piersiowej.", 8000, 0L, 2));
                instructionDAO.insert(new InstructionSet(3L, "Ułożenie dłoni", "Umieść dłoń na środku klatki piersiowej, drugą dłoń umieść nad pierwszą tak, aby palce się przeplatały.", 9000, 0L, 3));
                instructionDAO.insert(new InstructionSet(4L, "Palce", "Nie wywieraj nacisku na klatkę piersiową palcami, utrzymaj je lekko uniesione i splecione.", 8000, 0L, 4));
                instructionDAO.insert(new InstructionSet(5L, "Głębokość uciśnięć", "Staraj się wywierać nacisk o 5 centymetrów prosto w dół ciężarem swojego ciała.", 7000, 0L, 5));
                instructionDAO.insert(new InstructionSet(6L, "Uciśnięcia", "Kontynuuj uciśnięcia do momentu przybycia pomocy zgodnie z tempem dźwięku który słyszysz w tle.", 14000, 0L, 6));
                instructionDAO.insert(new InstructionSet(7L, "W razie zwymiotowania ofiary", "Jeśli ofiara zwymiotuje w trakcie, przekręć ją na bok tak by głowa była skierowana w dół i poczekaj aż jej usta się opróżnią, przetrzyj je, po czym wróć do procedury.", 8000, 0L, 7));

                versionDAO.insert(new Version(0L, "Przeprowadź mnie przez wszystkie podstawowe kroki!", 0L, true,"0", false, false,  null));
                versionDAO.insert(new Version(1L, "Wiem, co robię, potrzebne mi jest tylko tempo!", 0L, false, "0", false, false, null));

                versionInstructionDAO.insert(new VersionInstruction(0L, 0L, 0));
                versionInstructionDAO.insert(new VersionInstruction(1L, 0L, 1));
                versionInstructionDAO.insert(new VersionInstruction(2L, 0L, 2));
                versionInstructionDAO.insert(new VersionInstruction(3L, 0L, 3));
                versionInstructionDAO.insert(new VersionInstruction(4L, 0L, 4));
                versionInstructionDAO.insert(new VersionInstruction(5L, 0L, 5));
                versionInstructionDAO.insert(new VersionInstruction(6L, 0L, 6));
                versionInstructionDAO.insert(new VersionInstruction(7L, 0L, 7));
                versionInstructionDAO.insert(new VersionInstruction(8L, 0L, 8));

                versionInstructionDAO.insert(new VersionInstruction(9L, 1L, 6));

                tutorialSoundDAO.insert(new TutorialSound(0L, 45000L, true, 545L, 0L));

                multimediaDAO.insert(new Multimedia(0L, 0L, -1,false, "m0_0.mp4" ,true, 0));

                multimediaInVersionDAO.insert(new MultimediaInVersion(0L, 0L, 0L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(1L, 0L, 1L));

                //beginning of broken limb general tutorial
                tutorialDAO.insert(new Tutorial(1L, "Złamana kończyna", 1L, "broken_bone.jpeg"));

                instructionDAO.insert(new InstructionSet(8L, "Wstęp", "Jeżeli istnieje podejrzenie złamania", 5000, 1L, 0));
                instructionDAO.insert(new InstructionSet(9L, "Nie udało się wezwać pomocy", "...i nie możesz dosięgnąć numeru alarmowego", 5000, 1L, 1));
                instructionDAO.insert(new InstructionSet(10L, "Upomnienie ofiary", "Ofiara powinna pozostać w bezruchu. Złamane fragmenty kostne są ostre, i każdy ruch stwarza ryzyko dalszego okaleczenia.", 5000, 1L, 2));
                instructionDAO.insert(new InstructionSet(11L, "Upomnienie użytkownika", "Nie próbuj ustawiać ani prostować kończyny, nie przemieszczaj jej w żaden sposób.", 5000, 1L, 3));
                instructionDAO.insert(new InstructionSet(12L, "Upomnienie użytkownika, złamanie otwarte", "Nie dotykaj wystającego fragmentu kostnego.", 5000, 1L, 4));
                instructionDAO.insert(new InstructionSet(13L, "Pomoc wezwana, koniec", "Nie podejmuj dalszych działań, upewnij się, że ofiara jest bezpieczna i ułożona bądź usadzona w wygodnej pozycji.", 5000, 1L, 5));
                instructionDAO.insert(new InstructionSet(14L, "Jedzenie i picie", "Nie pozwalaj ofierze niczego jeść ani pić, może to mieć negatywny wpływ na działanie znieczulenia.", 5000, 1L, 6));
                instructionDAO.insert(new InstructionSet(15L, "Unieruchamianie kości", "Unieruchomienie kończyny jest wymagane w celu przetransportowania ofiary do najbliższego szpitala.", 5000, 1L, 7));
                instructionDAO.insert(new InstructionSet(24L, "Upomnienie o stawach", "Unieruchomione muszą zostać również dwa sąsiadujące z kością stawy.", 5000, 1L, 8));
                instructionDAO.insert(new InstructionSet(16L, "Unieruchamianie dolnej kończyny", "Częściowe unieruchomienie kończyny dolnej może zostać osiągnięte poprzez przymocowanie jej do drugiej kończyny.", 5000, 1L, 9));
                instructionDAO.insert(new InstructionSet(23L, "Unieruchomienie zdrowej kończyny", "Należy w takim wypadku w miarę możliwości odpowiednio usztywnić też zdrową kończynę.", 5000, 1L, 10));
                instructionDAO.insert(new InstructionSet(17L, "Upomnienie o wiązaniu kończyn", "W przypadku gdy rozwiązanie to nie będzie wygodne dla ofiary, nie należy go stosować.", 5000, 1L, 11));
                instructionDAO.insert(new InstructionSet(18L, "Temblak", "Wykonanie prowizorycznego temblaka do usztywniania górnej kończyny zaprezentowane zostało w galerii zdjęć.", 5000, 1L, 12));
                instructionDAO.insert(new InstructionSet(19L, "Opatrywanie złamania otwartego", "Opatrunek złamania otwartego należy wykonać poprzez stopniowe owijanie czystego materiału wokół i ponad, lecz nie bezpośrednio na miejscu złamania.", 5000, 1L, 13));
                instructionDAO.insert(new InstructionSet(20L, "Upomienie o złamaniu otwartym", "Nie należy stosować łatwo rozpadających się przedmiotów takich jak waciki do podwyższenia opatrunku.", 5000, 1L, 14));
                instructionDAO.insert(new InstructionSet(21L, "Opatrywanie złamanie otwartego ciąg dalszy", "Należy wykonać dostatecznie dużo warstw, aby opatrunek bezpośrednio nad raną nie wywierał nacisku na kość.", 5000, 1L, 15));
                instructionDAO.insert(new InstructionSet(22L, "Upomnienie o nacisku na kość", "Każdy nacisk na wystający fragment kostny powoduje komplikacje.", 5000, 1L, 16));
                instructionDAO.insert(new InstructionSet(25L, "Sprawdzanie krwiobiegu - wezwana pomoc", "Do czasu przybycia pomocy sprawdzaj co kilka minut krwiobieg poniżej opatrunku…", 5000, 1L, 17));
                instructionDAO.insert(new InstructionSet(26L, "Sprawdzanie krwiobiegu - niewezwana pomoc", "Jeżeli jest z tobą osoba towarzysząca, poproś ją o sprawdzanie co kilka minut krwiobiegu poniżej opatrunku…", 5000, 1L, 18));
                instructionDAO.insert(new InstructionSet(27L, "Upomnienie o krwiobiegu", "…w celu upewnienia się, że sztywny opatrunek go nie tamuje. W przypadku gdy krwiobieg jest zatamowany, należy poluzować opatrunek.", 5000, 1L, 19));
                instructionDAO.insert(new InstructionSet(28L, "Wstrząs", "Ryzyko wstrząsu, czyli niedostatecznej ilości tlenu w organizmie, mogącej być spowodowanej utratą dużej ilości krwi, omówione jest w oddzielnym poradniku. W razie potrzeby naciśnij tutaj aby do niego przejść.", 5000, 1L, 20));

                versionDAO.insert(new Version(9L, "Złamana ręka", 1L, false, null, true, false, null));
                versionDAO.insert(new Version(10L, "Złamana noga", 1L, false, null, true, false, null));
                versionDAO.insert(new Version(3L, "Udało mi się wezwać pomoc, jestem z ofiarą do czasu jej przybycia", 1L, false, null, true, true, 9L));
                versionDAO.insert(new Version(4L, "Muszę sam/a zawieźć ofiarę do szpitala", 1L, false, null, true, true, 9L));
                versionDAO.insert(new Version(11L, "Udało mi się wezwać pomoc, jestem z ofiarą do czasu jej przybycia", 1L, false, null, true, true, 10L));
                versionDAO.insert(new Version(12L, "Muszę sam/a zawieźć ofiarę do szpitala", 1L, false, null, true, true, 10L));
                versionDAO.insert(new Version(5L, "Złamanie zamknięte", 1L, false, null, false, true, 3L));
                versionDAO.insert(new Version(6L, "Złamanie otwarte", 1L, false, null, false, true, 3L));
                versionDAO.insert(new Version(7L, "Złamanie zamknięte", 1L, false, null, false, true, 4L));
                versionDAO.insert(new Version(8L, "Złamanie otwarte", 1L, false, null, false, true, 4L));
                versionDAO.insert(new Version(13L, "Złamanie zamknięte", 1L, false, null, false, true, 11L));
                versionDAO.insert(new Version(14L, "Złamanie otwarte", 1L, false, null, false, true, 11L));
                versionDAO.insert(new Version(15L, "Złamanie zamknięte", 1L, false, null, false, true, 12L));
                versionDAO.insert(new Version(16L, "Złamanie otwarte", 1L, false, null, false, true, 12L));

                versionInstructionDAO.insert(new VersionInstruction(10L, 5L, 0));
                versionInstructionDAO.insert(new VersionInstruction(11L, 5L, 2));
                versionInstructionDAO.insert(new VersionInstruction(12L, 5L, 3));
                versionInstructionDAO.insert(new VersionInstruction(13L, 5L, 5));
                versionInstructionDAO.insert(new VersionInstruction(14L, 5L, 6));
                versionInstructionDAO.insert(new VersionInstruction(15L, 5L, 17));
                versionInstructionDAO.insert(new VersionInstruction(38L, 5L, 19));

                versionInstructionDAO.insert(new VersionInstruction(16L, 6L, 0));
                versionInstructionDAO.insert(new VersionInstruction(17L, 6L, 2));
                versionInstructionDAO.insert(new VersionInstruction(18L, 6L, 3));
                versionInstructionDAO.insert(new VersionInstruction(19L, 6L, 4));
                versionInstructionDAO.insert(new VersionInstruction(20L, 6L, 5));
                versionInstructionDAO.insert(new VersionInstruction(21L, 6L, 6));
                versionInstructionDAO.insert(new VersionInstruction(22L, 6L, 17));
                versionInstructionDAO.insert(new VersionInstruction(37L, 6L, 19));

                versionInstructionDAO.insert(new VersionInstruction(23L, 7L, 0));
                versionInstructionDAO.insert(new VersionInstruction(24L, 7L, 1));
                versionInstructionDAO.insert(new VersionInstruction(25L, 7L, 2));
                versionInstructionDAO.insert(new VersionInstruction(26L, 7L, 3));
                versionInstructionDAO.insert(new VersionInstruction(27L, 7L, 6));
                versionInstructionDAO.insert(new VersionInstruction(28L, 7L, 7));
                versionInstructionDAO.insert(new VersionInstruction(29L, 7L, 8));
                versionInstructionDAO.insert(new VersionInstruction(33L, 7L, 12));
                versionInstructionDAO.insert(new VersionInstruction(34L, 7L, 18));
                versionInstructionDAO.insert(new VersionInstruction(35L, 7L, 19));
                versionInstructionDAO.insert(new VersionInstruction(36L, 7L, 20));

                versionInstructionDAO.insert(new VersionInstruction(39L, 8L, 0));
                versionInstructionDAO.insert(new VersionInstruction(40L, 8L, 1));
                versionInstructionDAO.insert(new VersionInstruction(41L, 8L, 2));
                versionInstructionDAO.insert(new VersionInstruction(42L, 8L, 3));
                versionInstructionDAO.insert(new VersionInstruction(43L, 8L, 4));
                versionInstructionDAO.insert(new VersionInstruction(44L, 8L, 6));
                versionInstructionDAO.insert(new VersionInstruction(45L, 8L, 7));
                versionInstructionDAO.insert(new VersionInstruction(46L, 8L, 8));
                versionInstructionDAO.insert(new VersionInstruction(50L, 8L, 12));
                versionInstructionDAO.insert(new VersionInstruction(51L, 8L, 13));
                versionInstructionDAO.insert(new VersionInstruction(52L, 8L, 14));
                versionInstructionDAO.insert(new VersionInstruction(53L, 8L, 15));
                versionInstructionDAO.insert(new VersionInstruction(54L, 8L, 16));
                versionInstructionDAO.insert(new VersionInstruction(55L, 8L, 18));
                versionInstructionDAO.insert(new VersionInstruction(56L, 8L, 19));
                versionInstructionDAO.insert(new VersionInstruction(57L, 8L, 20));

                versionInstructionDAO.insert(new VersionInstruction(58L, 13L, 0));
                versionInstructionDAO.insert(new VersionInstruction(59L, 13L, 2));
                versionInstructionDAO.insert(new VersionInstruction(60L, 13L, 3));
                versionInstructionDAO.insert(new VersionInstruction(61L, 13L, 5));
                versionInstructionDAO.insert(new VersionInstruction(62L, 13L, 6));
                versionInstructionDAO.insert(new VersionInstruction(63L, 13L, 17));
                versionInstructionDAO.insert(new VersionInstruction(64L, 13L, 19));

                versionInstructionDAO.insert(new VersionInstruction(65L, 14L, 0));
                versionInstructionDAO.insert(new VersionInstruction(66L, 14L, 2));
                versionInstructionDAO.insert(new VersionInstruction(67L, 14L, 3));
                versionInstructionDAO.insert(new VersionInstruction(68L, 14L, 4));
                versionInstructionDAO.insert(new VersionInstruction(69L, 14L, 5));
                versionInstructionDAO.insert(new VersionInstruction(70L, 14L, 6));
                versionInstructionDAO.insert(new VersionInstruction(71L, 14L, 17));
                versionInstructionDAO.insert(new VersionInstruction(72L, 14L, 19));

                versionInstructionDAO.insert(new VersionInstruction(73L, 15L, 0));
                versionInstructionDAO.insert(new VersionInstruction(74L, 15L, 1));
                versionInstructionDAO.insert(new VersionInstruction(75L, 15L, 2));
                versionInstructionDAO.insert(new VersionInstruction(76L, 15L, 3));
                versionInstructionDAO.insert(new VersionInstruction(77L, 15L, 6));
                versionInstructionDAO.insert(new VersionInstruction(78L, 15L, 7));
                versionInstructionDAO.insert(new VersionInstruction(79L, 15L, 8));
                versionInstructionDAO.insert(new VersionInstruction(80L, 15L, 9));
                versionInstructionDAO.insert(new VersionInstruction(81L, 15L, 10));
                versionInstructionDAO.insert(new VersionInstruction(82L, 15L, 11));
                versionInstructionDAO.insert(new VersionInstruction(83L, 15L, 12));
                versionInstructionDAO.insert(new VersionInstruction(84L, 15L, 19));
                versionInstructionDAO.insert(new VersionInstruction(85L, 15L, 20));

                versionInstructionDAO.insert(new VersionInstruction(86L, 16L, 0));
                versionInstructionDAO.insert(new VersionInstruction(87L, 16L, 1));
                versionInstructionDAO.insert(new VersionInstruction(89L, 16L, 2));
                versionInstructionDAO.insert(new VersionInstruction(90L, 16L, 3));
                versionInstructionDAO.insert(new VersionInstruction(91L, 16L, 4));
                versionInstructionDAO.insert(new VersionInstruction(92L, 16L, 6));
                versionInstructionDAO.insert(new VersionInstruction(93L, 16L, 7));
                versionInstructionDAO.insert(new VersionInstruction(94L, 16L, 8));
                versionInstructionDAO.insert(new VersionInstruction(95L, 16L, 9));
                versionInstructionDAO.insert(new VersionInstruction(96L, 16L, 10));
                versionInstructionDAO.insert(new VersionInstruction(97L, 16L, 11));
                versionInstructionDAO.insert(new VersionInstruction(98L, 16L, 12));
                versionInstructionDAO.insert(new VersionInstruction(99L, 16L, 13));
                versionInstructionDAO.insert(new VersionInstruction(100L, 16L, 14));
                versionInstructionDAO.insert(new VersionInstruction(101L, 16L, 15));
                versionInstructionDAO.insert(new VersionInstruction(102L, 16L, 16));
                versionInstructionDAO.insert(new VersionInstruction(103L, 16L, 19));
                versionInstructionDAO.insert(new VersionInstruction(104L, 16L, 20));

                multimediaDAO.insert(new Multimedia(1L, 1L, 5000, true, "m1_0.jpg", true, 0));
                multimediaDAO.insert(new Multimedia(2L, 1L, 5000, true, "m1_1.jpg", true, 1));

                multimediaInVersionDAO.insert(new MultimediaInVersion(3L, 1L, 5L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(4L, 2L, 5L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(5L, 1L, 6L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(6L, 2L, 6L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(7L, 1L, 7L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(8L, 2L, 7L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(9L, 1L, 8L));
                multimediaInVersionDAO.insert(new MultimediaInVersion(10L, 2L, 8L));

                //beginning of heatstroke tutorial
                tutorialDAO.insert(new Tutorial(2L, "Udar słoneczny", 1L, "sun.jpg"));

                //beginning of choking dog tutorial
                tutorialDAO.insert(new Tutorial(3L, "Dławiący się pies", 1L, "choking_dog.jpeg"));

                //beginning of aggressive dog tutorial
                tutorialDAO.insert(new Tutorial(4L, "Agresywny pies", 1L, "barking_dog.jpg"));

                //beginning of choking cat tutorial
                tutorialDAO.insert(new Tutorial(5L, "Dławiący się kot", 1L, "choking_cat.jpeg"));

                //beginning of circulatory shock tutorial
                tutorialDAO.insert(new Tutorial(6L, "Wstrząs", 1L, "circulatory_shock.jpeg"));

                //beginning of drowning tutorial
                tutorialDAO.insert(new Tutorial(7L, "Podtopienie", 1L, "drowning.jpeg"));

                //beginning of categories
                categoryDAO.insert(new Category(0L, "Pierwsza pomoc", true, "first_aid.jpg", 0));
                categoryDAO.insert(new Category(1L, "Pożar", true, "fire.jpeg", 0));
                categoryDAO.insert(new Category(2L, "Żywioł", true, "natural_disaster.jpeg", 0));
                categoryDAO.insert(new Category(3L, "Atak terrorystyczny", true, "terrorist.jpeg", 0));
                categoryDAO.insert(new Category(4L, "Zwierzęta", true, "animals.jpeg", 0));
                categoryDAO.insert(new Category(5L, "Przetrwanie w dziczy", true, "survival.jpeg", 0));
                categoryDAO.insert(new Category(6L, "Problemy z oddychaniem", false, "breathing.jpg", 1));
                categoryDAO.insert(new Category(7L, "Urazy", true, "injury.jpeg", 1));
                categoryDAO.insert(new Category(8L, "Złamania", false, "broken_bone.jpeg", 2));
                categoryDAO.insert(new Category(9L, "Zwichnięcia", false, "sprain.jpeg", 2));
                categoryDAO.insert(new Category(10L, "Głowa", false, "head_accident.jpeg", 1));
                categoryDAO.insert(new Category(11L, "Zatrucia", true, "poisoning.jpeg", 1));
                categoryDAO.insert(new Category(12L, "Węże", false, "snakes.jpeg", 1));
                categoryDAO.insert(new Category(13L, "Psy", false, "dogs.jpeg", 1));
                categoryDAO.insert(new Category(14L, "Konie", false, "horses.jpeg", 1));
                categoryDAO.insert(new Category(15L, "Koty", false, "cats.jpeg", 1));
                categoryDAO.insert(new Category(16L, "Gryzonie", true, "rodents.jpeg", 1));
                categoryDAO.insert(new Category(17L, "Ptaki", true, "birds.jpeg", 1));
                categoryDAO.insert(new Category(18L, "Papugi", false, "parrots.jpeg", 2));
                categoryDAO.insert(new Category(19L, "Szczury", false, "rats.jpg", 2));
                categoryDAO.insert(new Category(20L, "Szynszyle", false, "chinchilla.jpg", 2));
                categoryDAO.insert(new Category(21L, "Sowy", false, "owls.jpeg", 2));
                categoryDAO.insert(new Category(22L, "Dzikie zwierzęta", true, "wild_animals.jpeg", 1));
                categoryDAO.insert(new Category(23L, "Zającowate", true, "leporidae.jpeg", 1));
                categoryDAO.insert(new Category(24L, "Rany", true, "wounds.jpeg", 1));
                categoryDAO.insert(new Category(25L, "Poparzenia", true, "burns.jpeg", 1));
                categoryDAO.insert(new Category(26L, "Jama ustna", false, "mouth.jpeg", 1));
                categoryDAO.insert(new Category(28L, "Wypadki nad wodą", false, "water_accidents.jpeg", 1));
                categoryDAO.insert(new Category(27L, "Szok", false, "shock.jpeg", 1));
                categoryDAO.insert(new Category(29L, "Przypadkowe amputacje", false, "accidental_amputation.jpeg", 1));
                categoryDAO.insert(new Category(30L, "Niedźwiedzie", false, "wild_animals.jpeg", 2));
                categoryDAO.insert(new Category(31L, "Wilki", false, "wolves.jpeg", 2));
                categoryDAO.insert(new Category(32L, "Burza", false, "storm.jpeg", 1));
                categoryDAO.insert(new Category(33L, "Pożar lasu", false, "forest_fire.jpeg", 1));
                categoryDAO.insert(new Category(34L, "Trzęsienie ziemi", false, "earthquake.jpeg", 1));
                categoryDAO.insert(new Category(35L, "Lawina", false, "avalanche.jpeg", 1));
                categoryDAO.insert(new Category(36L, "Huragan", false, "hurricane.jpeg", 1));
                categoryDAO.insert(new Category(37L, "Zamieć śnieżna", false, "heavy_snowfall.jpeg", 1));
                categoryDAO.insert(new Category(38L, "Powódź", false, "flood.jpeg", 1));
                categoryDAO.insert(new Category(39L, "Bardzo niskie temperatury", false, "extreme_cold.jpeg", 1));
                categoryDAO.insert(new Category(40L, "Bardzo wysokie temperatury", false, "extreme_heat.jpeg", 1));
                categoryDAO.insert(new Category(41L, "Środowiskowe", false, "environmental.jpeg", 2));
                categoryDAO.insert(new Category(42L, "Utrata przytomności", false, "unconscious.jpeg", 1));
                categoryDAO.insert(new Category(43L, "Substancjami", false, "substances.jpeg", 2));
                categoryDAO.insert(new Category(44L, "Trujące jedzenie", false, "poison_food.jpeg", 2));
                categoryDAO.insert(new Category(45L, "Trujące rośliny", false, "poison_plants.jpeg", 2));
                categoryDAO.insert(new Category(46L, "Uzbrojeni w broń palną", false, "gunner.jpeg", 1));
                categoryDAO.insert(new Category(47L, "Uzbrojeni w broń białą", false, "knife.jpeg", 1));
                categoryDAO.insert(new Category(48L, "Owady", true, "insects.jpeg", 1));
                categoryDAO.insert(new Category(49L, "Pająki", true, "spiders.jpeg", 1));
                categoryDAO.insert(new Category(50L, "Przygniecenie", false, "crushing.jpg", 1));

                //beginning of tags
                tagDAO.insert(new Tag(15L, "root", 0));
                tagDAO.insert(new Tag(0L, "firstaid", 1));
                tagDAO.insert(new Tag(1L, "fire", 1));
                tagDAO.insert(new Tag(2L, "natural", 1));
                tagDAO.insert(new Tag(3L, "terrorism", 1));
                tagDAO.insert(new Tag(4L, "animals", 1));
                tagDAO.insert(new Tag(5L, "survival", 1));
                tagDAO.insert(new Tag(6L, "breathing", 2));
                tagDAO.insert(new Tag(7L, "injury", 2));
                tagDAO.insert(new Tag(8L, "broken bones", 3));
                tagDAO.insert(new Tag(9L, "sprain", 3));
                tagDAO.insert(new Tag(10L, "head", 2));
                tagDAO.insert(new Tag(11L, "creator", null));
                tagDAO.insert(new Tag(12L, "cpr", null));
                tagDAO.insert(new Tag(13L, "limbs", null));
                tagDAO.insert(new Tag(14L, "heatstroke", null));
                tagDAO.insert(new Tag(16L, "poisoning", 2));
                tagDAO.insert(new Tag(17L, "snakes", 2));
                tagDAO.insert(new Tag(18L, "dogs", 2));
                tagDAO.insert(new Tag(19L, "choking", null));
                tagDAO.insert(new Tag(20L, "horses", 2));
                tagDAO.insert(new Tag(21L, "danger", null));
                tagDAO.insert(new Tag(22L, "cats", 2));
                tagDAO.insert(new Tag(23L, "rodents", 2));
                tagDAO.insert(new Tag(24L, "rats", 3));
                tagDAO.insert(new Tag(25L, "birds", 2));
                tagDAO.insert(new Tag(26L, "parrots", 3));
                tagDAO.insert(new Tag(27L, "chinchilla", 3));
                tagDAO.insert(new Tag(28L, "owls", 3));
                tagDAO.insert(new Tag(29L, "wild", 2));
                tagDAO.insert(new Tag(30L, "leporidae", 2));
                tagDAO.insert(new Tag(31L, "wounds", 2));
                tagDAO.insert(new Tag(32L, "burns", 2));
                tagDAO.insert(new Tag(33L, "water accidents", 2));
                tagDAO.insert(new Tag(34L, "shock", 2));
                tagDAO.insert(new Tag(35L, "circulatory", null));
                tagDAO.insert(new Tag(36L, "mouth", 2));
                tagDAO.insert(new Tag(37L, "amputation", 2));
                tagDAO.insert(new Tag(38L, "drowning", null));
                tagDAO.insert(new Tag(39L, "bears", 3));
                tagDAO.insert(new Tag(40L, "wolves", 3));
                tagDAO.insert(new Tag(41L, "storm", 2));
                tagDAO.insert(new Tag(42L, "forest fire", 2));
                tagDAO.insert(new Tag(43L, "earthquake", 2));
                tagDAO.insert(new Tag(44L, "avalanche", 2));
                tagDAO.insert(new Tag(45L, "hurricane", 2));
                tagDAO.insert(new Tag(46L, "heavy snowfall", 2));
                tagDAO.insert(new Tag(47L, "flood", 2));
                tagDAO.insert(new Tag(48L, "extreme cold", 2));
                tagDAO.insert(new Tag(49L, "extreme heat", 2));
                tagDAO.insert(new Tag(50L, "environmental", 3));
                tagDAO.insert(new Tag(51L, "unconscious", 2));
                tagDAO.insert(new Tag(52L, "substances", 3));
                tagDAO.insert(new Tag(53L, "poisonous food", 3));
                tagDAO.insert(new Tag(54L, "poisonous plants", 3));
                tagDAO.insert(new Tag(55L, "firearms", 2));
                tagDAO.insert(new Tag(56L, "melee", 2));
                tagDAO.insert(new Tag(57L, "insects", 2));
                tagDAO.insert(new Tag(58L, "spiders", 2));
                tagDAO.insert(new Tag(59L, "crushing", 2));

                //beginning of category-tag relations
                categoryTagDAO.insert(new CategoryTag(0L, 0L, 0L));
                categoryTagDAO.insert(new CategoryTag(1L, 1L, 1L));
                categoryTagDAO.insert(new CategoryTag(2L, 2L, 2L));
                categoryTagDAO.insert(new CategoryTag(3L, 3L, 3L));
                categoryTagDAO.insert(new CategoryTag(4L, 4L, 4L));
                categoryTagDAO.insert(new CategoryTag(5L, 5L, 5L));
                categoryTagDAO.insert(new CategoryTag(6L, 6L, 0L));
                categoryTagDAO.insert(new CategoryTag(7L, 6L, 6L));
                categoryTagDAO.insert(new CategoryTag(8L, 7L, 0L));
                categoryTagDAO.insert(new CategoryTag(9L, 7L, 7L));
                categoryTagDAO.insert(new CategoryTag(10L, 8L, 0L));
                categoryTagDAO.insert(new CategoryTag(11L, 8L, 7L));
                categoryTagDAO.insert(new CategoryTag(12L, 8L, 8L));
                categoryTagDAO.insert(new CategoryTag(13L, 9L, 0L));
                categoryTagDAO.insert(new CategoryTag(14L, 9L, 7L));
                categoryTagDAO.insert(new CategoryTag(15L, 9L, 9L));
                categoryTagDAO.insert(new CategoryTag(16L, 10L, 0L));
                categoryTagDAO.insert(new CategoryTag(17L, 10L, 10L));
                categoryTagDAO.insert(new CategoryTag(18L, 11L, 0L));
                categoryTagDAO.insert(new CategoryTag(19L, 11L, 16L));
                categoryTagDAO.insert(new CategoryTag(20L, 12L, 17L));
                categoryTagDAO.insert(new CategoryTag(21L, 12L, 4L));
                categoryTagDAO.insert(new CategoryTag(22L, 13, 18L));
                categoryTagDAO.insert(new CategoryTag(23L, 13, 4L));
                categoryTagDAO.insert(new CategoryTag(24L, 14L, 4L));
                categoryTagDAO.insert(new CategoryTag(25L, 14L, 20L));
                categoryTagDAO.insert(new CategoryTag(26L, 15L, 4L));
                categoryTagDAO.insert(new CategoryTag(27L, 15L, 22L));
                categoryTagDAO.insert(new CategoryTag(28L, 16L, 4L));
                categoryTagDAO.insert(new CategoryTag(29L, 16L, 23L));
                categoryTagDAO.insert(new CategoryTag(31L, 17L, 4L));
                categoryTagDAO.insert(new CategoryTag(32L, 17L, 25L));
                categoryTagDAO.insert(new CategoryTag(33L, 18L, 4L));
                categoryTagDAO.insert(new CategoryTag(34L, 18L, 25L));
                categoryTagDAO.insert(new CategoryTag(35L, 18L, 26L));
                categoryTagDAO.insert(new CategoryTag(36L, 19L, 4L));
                categoryTagDAO.insert(new CategoryTag(37L, 19L, 23L));
                categoryTagDAO.insert(new CategoryTag(38L, 19L, 24L));
                categoryTagDAO.insert(new CategoryTag(39L, 20L, 4L));
                categoryTagDAO.insert(new CategoryTag(40L, 20L, 23L));
                categoryTagDAO.insert(new CategoryTag(41L, 20L, 27L));
                categoryTagDAO.insert(new CategoryTag(42L, 21L, 4L));
                categoryTagDAO.insert(new CategoryTag(43L, 21L, 25L));
                categoryTagDAO.insert(new CategoryTag(44L, 21L, 28L));
                categoryTagDAO.insert(new CategoryTag(45L, 22L, 4L));
                categoryTagDAO.insert(new CategoryTag(46L, 22L, 29L));
                categoryTagDAO.insert(new CategoryTag(47L, 23L, 30L));
                categoryTagDAO.insert(new CategoryTag(48L, 23L, 4L));
                categoryTagDAO.insert(new CategoryTag(49L, 24L, 0L));
                categoryTagDAO.insert(new CategoryTag(50L, 24L, 31L));
                categoryTagDAO.insert(new CategoryTag(51L, 25L, 32L));
                categoryTagDAO.insert(new CategoryTag(52L, 25L, 0L));
                categoryTagDAO.insert(new CategoryTag(53L, 28L, 0L));
                categoryTagDAO.insert(new CategoryTag(54L, 28L, 33L));
                categoryTagDAO.insert(new CategoryTag(55L, 27L, 34L));
                categoryTagDAO.insert(new CategoryTag(56L, 27L, 0L));
                categoryTagDAO.insert(new CategoryTag(57L, 26L, 0L));
                categoryTagDAO.insert(new CategoryTag(58L, 26L, 36L));
                categoryTagDAO.insert(new CategoryTag(59L, 29L, 0L));
                categoryTagDAO.insert(new CategoryTag(60L, 29L, 37L));
                categoryTagDAO.insert(new CategoryTag(61L, 30L, 39L));
                categoryTagDAO.insert(new CategoryTag(62L, 30L, 4L));
                categoryTagDAO.insert(new CategoryTag(63L, 30L, 29L));
                categoryTagDAO.insert(new CategoryTag(64L, 31L, 40L));
                categoryTagDAO.insert(new CategoryTag(65L, 31L, 29L));
                categoryTagDAO.insert(new CategoryTag(66L, 31L, 4L));
                categoryTagDAO.insert(new CategoryTag(67L, 32L, 2L));
                categoryTagDAO.insert(new CategoryTag(68L, 32L, 41L));
                categoryTagDAO.insert(new CategoryTag(69L, 33L, 1L));
                categoryTagDAO.insert(new CategoryTag(70L, 33L, 42L));
                categoryTagDAO.insert(new CategoryTag(71L, 34L, 43L));
                categoryTagDAO.insert(new CategoryTag(72L, 34L, 2L));
                categoryTagDAO.insert(new CategoryTag(73L, 35L, 44L));
                categoryTagDAO.insert(new CategoryTag(74L, 35L, 2L));
                categoryTagDAO.insert(new CategoryTag(75L, 36L, 2L));
                categoryTagDAO.insert(new CategoryTag(76L, 36L, 45L));
                categoryTagDAO.insert(new CategoryTag(77L, 37L, 2L));
                categoryTagDAO.insert(new CategoryTag(78L, 37L, 46L));
                categoryTagDAO.insert(new CategoryTag(79L, 38L, 47L));
                categoryTagDAO.insert(new CategoryTag(80L, 38L, 2L));
                categoryTagDAO.insert(new CategoryTag(81L, 39L, 2L));
                categoryTagDAO.insert(new CategoryTag(82L, 39L, 48L));
                categoryTagDAO.insert(new CategoryTag(83L, 40L, 49L));
                categoryTagDAO.insert(new CategoryTag(84L, 40L, 2L));
                categoryTagDAO.insert(new CategoryTag(85L, 41L, 0L));
                categoryTagDAO.insert(new CategoryTag(86L, 41L, 16L));
                categoryTagDAO.insert(new CategoryTag(87L, 41L, 50L));
                categoryTagDAO.insert(new CategoryTag(88L, 42L, 0L));
                categoryTagDAO.insert(new CategoryTag(89L, 42L, 51L));
                categoryTagDAO.insert(new CategoryTag(90L, 43L, 0L));
                categoryTagDAO.insert(new CategoryTag(91L, 43L, 52L));
                categoryTagDAO.insert(new CategoryTag(92L, 43L, 16L));
                categoryTagDAO.insert(new CategoryTag(93L, 44L, 0L));
                categoryTagDAO.insert(new CategoryTag(94L, 44L, 16L));
                categoryTagDAO.insert(new CategoryTag(95L, 44L, 53L));
                categoryTagDAO.insert(new CategoryTag(96L, 45L, 54L));
                categoryTagDAO.insert(new CategoryTag(97L, 45L, 0L));
                categoryTagDAO.insert(new CategoryTag(98L, 45L, 16L));
                categoryTagDAO.insert(new CategoryTag(99L, 46L, 55L));
                categoryTagDAO.insert(new CategoryTag(100L, 46L, 3L));
                categoryTagDAO.insert(new CategoryTag(101L, 47L, 3L));
                categoryTagDAO.insert(new CategoryTag(102L, 47L, 56L));
                categoryTagDAO.insert(new CategoryTag(103L, 48L, 4L));
                categoryTagDAO.insert(new CategoryTag(104L, 48L, 57L));
                categoryTagDAO.insert(new CategoryTag(105L, 49L, 58L));
                categoryTagDAO.insert(new CategoryTag(106L, 49L, 4L));
                categoryTagDAO.insert(new CategoryTag(107L, 50L, 0L));
                categoryTagDAO.insert(new CategoryTag(108L, 50L, 59L));

                //beginning of helper-tag relations
                helperTagDAO.insert(new HelperTag(0L, 1L, 11L));

                //beginning of tutorial-tag relations
                tutorialTagDAO.insert(new TutorialTag(0L, 0L, 0L));
                tutorialTagDAO.insert(new TutorialTag(1L, 0L, 6L));
                tutorialTagDAO.insert(new TutorialTag(2L, 0L, 12L));
                tutorialTagDAO.insert(new TutorialTag(3L, 1L, 0L));
                tutorialTagDAO.insert(new TutorialTag(4L, 1L, 7L));
                tutorialTagDAO.insert(new TutorialTag(5L, 1L, 8L));
                tutorialTagDAO.insert(new TutorialTag(6L, 1L, 13L));
                tutorialTagDAO.insert(new TutorialTag(7L, 2L, 0L));
                tutorialTagDAO.insert(new TutorialTag(8L, 2L, 10L));
                tutorialTagDAO.insert(new TutorialTag(9L, 2L, 14L));
                tutorialTagDAO.insert(new TutorialTag(10L, 3L, 4L));
                tutorialTagDAO.insert(new TutorialTag(11L, 3L, 18L));
                tutorialTagDAO.insert(new TutorialTag(12L, 3L, 19L));
                tutorialTagDAO.insert(new TutorialTag(13L, 4L, 4L));
                tutorialTagDAO.insert(new TutorialTag(14L, 4L, 18L));
                tutorialTagDAO.insert(new TutorialTag(15L, 4L, 21L));
                tutorialTagDAO.insert(new TutorialTag(16L, 5L, 4L));
                tutorialTagDAO.insert(new TutorialTag(17L, 5L, 22L));
                tutorialTagDAO.insert(new TutorialTag(18L, 5L, 19L));
                tutorialTagDAO.insert(new TutorialTag(19L, 6L, 0L));
                tutorialTagDAO.insert(new TutorialTag(20L, 6L, 34L));
                tutorialTagDAO.insert(new TutorialTag(21L, 6L, 35L));
                tutorialTagDAO.insert(new TutorialTag(22L, 7L, 0L));
                tutorialTagDAO.insert(new TutorialTag(23L, 7L, 33L));
                tutorialTagDAO.insert(new TutorialTag(24L, 7L, 38L));

                //beginning of keywords
                //when helpers add keywords additional replicas with -em, -u, -a replacing e, etc.
                //will be generated also
                keywordDAO.insert(new Keyword(0L, "sztuczne"));
                keywordDAO.insert(new Keyword(1L, "oddychanie"));
                keywordDAO.insert(new Keyword(7L, "oddychania"));
                keywordDAO.insert(new Keyword(8L, "oddechu"));
                keywordDAO.insert(new Keyword(9L, "oddycha"));
                keywordDAO.insert(new Keyword(10L, "oddechem"));
                keywordDAO.insert(new Keyword(2L, "oddech"));
                keywordDAO.insert(new Keyword(3L, "zawał"));
                keywordDAO.insert(new Keyword(4L, "masaż"));
                keywordDAO.insert(new Keyword(5L, "serce"));
                keywordDAO.insert(new Keyword(6L, "serca"));
                keywordDAO.insert(new Keyword(11L, "sercu"));
                keywordDAO.insert(new Keyword(12L, "zawału"));
                keywordDAO.insert(new Keyword(13L, "zawałem"));
                keywordDAO.insert(new Keyword(14L, "oddechy"));
                keywordDAO.insert(new Keyword(15L, "oddech"));
                keywordDAO.insert(new Keyword(16L, "tchu"));
                keywordDAO.insert(new Keyword(17L, "brak"));
                keywordDAO.insert(new Keyword(18L, "cpr"));
                keywordDAO.insert(new Keyword(19L, "renanimacja"));
                keywordDAO.insert(new Keyword(20L, "reanimuj"));
                keywordDAO.insert(new Keyword(21L, "reanimowanie"));
                keywordDAO.insert(new Keyword(22L, "nieregularny"));
                keywordDAO.insert(new Keyword(23L, "zawal"));
                keywordDAO.insert(new Keyword(24L, "masaz"));
                keywordDAO.insert(new Keyword(25L, "zawalu"));
                keywordDAO.insert(new Keyword(26L, "zawalem"));

                keywordDAO.insert(new Keyword(27L, "kości"));
                keywordDAO.insert(new Keyword(28L, "kończyna"));
                keywordDAO.insert(new Keyword(29L, "kończyny"));
                keywordDAO.insert(new Keyword(30L, "ręka"));
                keywordDAO.insert(new Keyword(31L, "ręki"));
                keywordDAO.insert(new Keyword(32L, "noga"));
                keywordDAO.insert(new Keyword(33L, "nogi"));
                keywordDAO.insert(new Keyword(34L, "otwarte"));
                keywordDAO.insert(new Keyword(35L, "zamknięte"));
                keywordDAO.insert(new Keyword(36L, "nadgarstek"));
                keywordDAO.insert(new Keyword(37L, "złamania"));
                keywordDAO.insert(new Keyword(38L, "przedramię"));
                keywordDAO.insert(new Keyword(39L, "ramię"));
                keywordDAO.insert(new Keyword(40L, "kolano"));
                keywordDAO.insert(new Keyword(41L, "ręce"));
                keywordDAO.insert(new Keyword(42L, "nogi"));
                keywordDAO.insert(new Keyword(43L, "złamanie"));
                keywordDAO.insert(new Keyword(44L, "złamana"));
                keywordDAO.insert(new Keyword(45L, "złamane"));
                keywordDAO.insert(new Keyword(46L, "kość"));
                keywordDAO.insert(new Keyword(47L, "kosc"));
                keywordDAO.insert(new Keyword(48L, "kosci"));
                keywordDAO.insert(new Keyword(49L, "reki"));
                keywordDAO.insert(new Keyword(50L, "reka"));
                keywordDAO.insert(new Keyword(51L, "ramie"));
                keywordDAO.insert(new Keyword(52L, "rece"));
                keywordDAO.insert(new Keyword(53L, "zlamanie"));
                keywordDAO.insert(new Keyword(54L, "zlamana"));
                keywordDAO.insert(new Keyword(55L, "zlamane"));
                keywordDAO.insert(new Keyword(56L, "przedramie"));
                keywordDAO.insert(new Keyword(57L, "zamkniete"));

                keywordDAO.insert(new Keyword(58L, "nudności"));
                keywordDAO.insert(new Keyword(59L, "nadciśnienie"));
                keywordDAO.insert(new Keyword(60L, "słabość"));
                keywordDAO.insert(new Keyword(61L, "ból"));
                keywordDAO.insert(new Keyword(62L, "bólu"));
                keywordDAO.insert(new Keyword(63L, "bóle"));
                keywordDAO.insert(new Keyword(64L, "majaczenie"));
                keywordDAO.insert(new Keyword(65L, "rozkojarzenie"));
                keywordDAO.insert(new Keyword(66L, "udar"));
                keywordDAO.insert(new Keyword(67L, "słońce"));
                keywordDAO.insert(new Keyword(68L, "udaru"));
                keywordDAO.insert(new Keyword(69L, "słoneczny"));
                keywordDAO.insert(new Keyword(70L, "gorąco"));
                keywordDAO.insert(new Keyword(71L, "ciepło"));
                keywordDAO.insert(new Keyword(72L, "mowa"));
                keywordDAO.insert(new Keyword(73L, "przytomność"));
                keywordDAO.insert(new Keyword(74L, "omdlenie"));
                keywordDAO.insert(new Keyword(75L, "omdlenia"));
                keywordDAO.insert(new Keyword(76L, "wymowa"));
                keywordDAO.insert(new Keyword(77L, "pot"));
                keywordDAO.insert(new Keyword(78L, "pocenie"));
                keywordDAO.insert(new Keyword(79L, "wymioty"));
                keywordDAO.insert(new Keyword(80L, "wymiot"));
                keywordDAO.insert(new Keyword(81L, "bol"));
                keywordDAO.insert(new Keyword(82L, "bolu"));
                keywordDAO.insert(new Keyword(83L, "bole"));
                keywordDAO.insert(new Keyword(84L, "slonce"));
                keywordDAO.insert(new Keyword(85L, "sloneczny"));
                keywordDAO.insert(new Keyword(86L, "przytomnosc"));
                keywordDAO.insert(new Keyword(87L, "cieplo"));
                keywordDAO.insert(new Keyword(88L, "goraco"));
                keywordDAO.insert(new Keyword(89L, "slabosc"));
                keywordDAO.insert(new Keyword(90L, "nadcisnienie"));

                keywordDAO.insert(new Keyword(91L, "zadławienie"));
                keywordDAO.insert(new Keyword(92L, "dławienie"));
                keywordDAO.insert(new Keyword(93L, "dławi"));
                keywordDAO.insert(new Keyword(94L, "dławią"));
                keywordDAO.insert(new Keyword(95L, "przełykanie"));
                keywordDAO.insert(new Keyword(96L, "oddycha"));
                keywordDAO.insert(new Keyword(97L, "oddychanie"));
                keywordDAO.insert(new Keyword(98L, "tchu"));
                keywordDAO.insert(new Keyword(99L, "jedzenie"));
                keywordDAO.insert(new Keyword(100L, "jedzeniem"));
                keywordDAO.insert(new Keyword(101L, "zatkane"));
                keywordDAO.insert(new Keyword(102L, "zablokowane"));
                keywordDAO.insert(new Keyword(103L, "zatkana"));
                keywordDAO.insert(new Keyword(104L, "zablokowana"));
                keywordDAO.insert(new Keyword(105L, "niemożność"));
                keywordDAO.insert(new Keyword(106L, "wyziewy"));
                keywordDAO.insert(new Keyword(107L, "szarpany"));
                keywordDAO.insert(new Keyword(108L, "oddech"));
                keywordDAO.insert(new Keyword(109L, "gardło"));
                keywordDAO.insert(new Keyword(110L, "gardle"));
                keywordDAO.insert(new Keyword(111L, "przełyk"));
                keywordDAO.insert(new Keyword(112L, "przelyk"));
                keywordDAO.insert(new Keyword(113L, "gardlo"));
                keywordDAO.insert(new Keyword(114L, "niemoznosc"));
                keywordDAO.insert(new Keyword(115L, "przelykanie"));
                keywordDAO.insert(new Keyword(116L, "dlawi"));
                keywordDAO.insert(new Keyword(117L, "dlawia"));
                keywordDAO.insert(new Keyword(118L, "zadlawienie"));

                //beginning of tag-keyword relations
                for(long i = 0L; i < 27L; i++) {
                    tagKeywordDAO.insert(new TagKeyword(i, i, 12L));
                }

                for(long i = 27L; i < 58L; i++) {
                    tagKeywordDAO.insert(new TagKeyword(i, i, 13L));
                }

                for(long i = 58L; i < 91L; i++) {
                    tagKeywordDAO.insert(new TagKeyword(i, i, 14L));
                }

                for(long i = 91L; i < 119L; i++) {
                    tagKeywordDAO.insert(new TagKeyword(i, i, 19L));
                }
            });
        }
    };
}
