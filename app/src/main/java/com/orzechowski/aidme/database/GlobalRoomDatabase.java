package com.orzechowski.aidme.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.orzechowski.aidme.browser.database.Category;
import com.orzechowski.aidme.browser.database.CategoryDAO;
import com.orzechowski.aidme.settings.helper.database.Helper;
import com.orzechowski.aidme.settings.helper.database.HelperDAO;
import com.orzechowski.aidme.tutorial.database.InstructionSet;
import com.orzechowski.aidme.tutorial.database.InstructionSetDAO;
import com.orzechowski.aidme.tutorial.database.Multimedia;
import com.orzechowski.aidme.tutorial.database.MultimediaDAO;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialDAO;
import com.orzechowski.aidme.tutorial.sound.TutorialSound;
import com.orzechowski.aidme.tutorial.sound.TutorialSoundDAO;
import com.orzechowski.aidme.tutorial.version.database.Version;
import com.orzechowski.aidme.tutorial.version.database.VersionDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Version.class, InstructionSet.class, Tutorial.class,
        TutorialSound.class, Helper.class, Multimedia.class, Category.class},
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

    private static volatile GlobalRoomDatabase INSTANCE;

    public static GlobalRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null){
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
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            databaseWriteExecutor.execute(()->{
                TutorialDAO tutorialDAO = INSTANCE.tutorialDao();
                InstructionSetDAO instructionDAO = INSTANCE.instructionDao();
                VersionDAO versionDAO = INSTANCE.versionDao();
                TutorialSoundDAO tutorialSoundDAO = INSTANCE.tutorialSoundDAO();
                HelperDAO helperDAO = INSTANCE.helperDao();
                MultimediaDAO multimediaDAO = INSTANCE.multimediaDAO();
                CategoryDAO categoryDAO = INSTANCE.categoryDAO();

                helperDAO.insert(new Helper(0L, "Ania", "Kozłowska", "", "Studentka", "voiceactor"));

                helperDAO.insert(new Helper(1L, "Łukasz", "Orzechowski", "", "Twórca", "creator firstaid breathing immediate"));
                //beginning of CPR tutorial
                tutorialDAO.insert(new Tutorial(0L, "Masaż serca", 1L, "firstaid breathing immediate", "heart_massage.jpg"));

                instructionDAO.insert(new InstructionSet(0L, "Wstęp", "Jeżeli ofiara nie jest w stanie samodzielnie oddychać…", 5000, 0L, 0));

                instructionDAO.insert(new InstructionSet(1L, "Ułożenie ofiary", "Upewnij się, że ofiara leży na plecach, jest ułożona prosto, a jej drogi oddechowe są udrożnione.", 8000, 0L, 1));

                instructionDAO.insert(new InstructionSet(2L, "Pozycja do udzielania pomocy", "Uklęknij wygodnie nad ofiarą tak, by twoje dłonie mogły być wyprostowane prostopadle do jej klatki piersiowej.", 8000, 0L, 2));

                instructionDAO.insert(new InstructionSet(3L, "Ułożenie dłoni", "Umieść dłoń na środku klatki piersiowej, drugą dłoń umieść nad pierwszą tak, aby palce się przeplatały.", 9000, 0L, 3));

                instructionDAO.insert(new InstructionSet(4L, "Palce", "Nie wywieraj nacisku na klatkę piersiową palcami, utrzymaj je lekko uniesione i splecione.", 8000, 0L, 4));

                instructionDAO.insert(new InstructionSet(5L, "Głębokość uciśnięć", "Staraj się wywierać nacisk o 5 centymetrów prosto w dół ciężarem swojego ciała.", 7000, 0L, 5));

                instructionDAO.insert(new InstructionSet(6L, "Uciśnięcia", "Kontynuuj uciśnięcia do momentu przybycia pomocy zgodnie z tempem dźwięku który słyszysz w tle.", 14000, 0L, 6));

                instructionDAO.insert(new InstructionSet(7L, "W razie zwymiotowania ofiary", "Jeśli ofiara zwymiotuje w trakcie, przekręć ją na bok tak by głowa była skierowana w dół i poczekaj aż jej usta się opróżnią, przetrzyj je, po czym wróć do procedury.", 8000, 0L, 7));

                versionDAO.insert(new Version(0L, "Przeprowadź mnie przez wszystkie podstawowe kroki!", "01234567", 0L, true, "0", "0"));

                versionDAO.insert(new Version(1L, "Wiem, co robię, potrzebne mi jest tylko tempo!", "6", 0L, false, "0", "0"));

                tutorialSoundDAO.insert(new TutorialSound(0L, 45000L, true, 545L, 0L));

                multimediaDAO.insert(new Multimedia(0L, 0L, -1, false, true, 0));
                //beginning of broken bone general tutorial
                tutorialDAO.insert(new Tutorial(1L, "Złamana kończyna", 1L, "firstaid injury brokenbones limbs", "broken_bone.jpeg"));

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

                versionDAO.insert(new Version(3L, "Udało mi się wezwać pomoc, jestem z ofiarą do czasu jej przybycia", "023456", 1L, false, "", ""));

                categoryDAO.insert(new Category(0L, "Pierwsza pomoc", "firstaid", true, "first_aid.jpg", 0));

                categoryDAO.insert(new Category(1L, "Pożar", "fire", true, "fire.jpeg", 0));

                categoryDAO.insert(new Category(2L, "Żywioł", "natural", true, "natural_disaster.jpeg", 0));

                categoryDAO.insert(new Category(3L, "Atak terrorystyczny", "terrorism", true, "terrorist.jpeg", 0));

                categoryDAO.insert(new Category(4L, "Zwierzęta", "animals", true, "animal_danger.jpg", 0));

                categoryDAO.insert(new Category(5L, "Przetrwanie w dziczy", "survival", true, "survival.jpeg", 0));

                categoryDAO.insert(new Category(6L, "Problemy z oddychaniem", "firstaid breathing", false, "breathing.jpg", 1));

                categoryDAO.insert(new Category(7L, "Urazy", "firstaid injury", true, "injury.jpeg", 1));

                categoryDAO.insert(new Category(8L, "Złamania", "firstaid injury brokenbones", false, "broken_bone.jpeg", 2));

                categoryDAO.insert(new Category(9L, "Zwichnięcia", "firstaid injury sprain", false, "sprain.jpeg", 2));
            });
        }
    };
}
