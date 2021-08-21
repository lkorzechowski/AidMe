package com.orzechowski.aidme.database;

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
import com.orzechowski.aidme.tutorial.database.TutorialLink;
import com.orzechowski.aidme.tutorial.database.TutorialLinkDAO;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.MultimediaDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimediaDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.SoundInVersion;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.SoundInVersionDAO;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSoundDAO;
import com.orzechowski.aidme.tutorial.version.database.Version;
import com.orzechowski.aidme.tutorial.version.database.VersionDAO;
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction;
import com.orzechowski.aidme.tutorial.version.database.VersionInstructionDAO;

public class Populating
{
    public void populateCategories(CategoryDAO categoryDAO)
    {
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
    }

    public void populateTags(TagDAO tagDAO)
    {
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
        tagDAO.insert(new Tag(13L, "broken limbs", null));
        tagDAO.insert(new Tag(14L, "heatstroke", null));
        tagDAO.insert(new Tag(16L, "poisoning", 2));
        tagDAO.insert(new Tag(17L, "snakes", 2));
        tagDAO.insert(new Tag(18L, "dogs", 2));
        tagDAO.insert(new Tag(19L, "choking", null));
        tagDAO.insert(new Tag(20L, "horses", 2));
        tagDAO.insert(new Tag(21L, "aggressive dog", null));
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
    }

    public void populateCategoryTags(CategoryTagDAO categoryTagDAO)
    {
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
    }

    public void populateTutorialTags(TutorialTagDAO tutorialTagDAO)
    {
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
    }

    public void populateKeywords(KeywordDAO keywordDAO)
    {
        keywordDAO.insert(new Keyword(0L, "sztuczne"));
        keywordDAO.insert(new Keyword(1L, "oddychanie"));
        keywordDAO.insert(new Keyword(2L, "oddychania"));
        keywordDAO.insert(new Keyword(3L, "oddechu"));
        keywordDAO.insert(new Keyword(4L, "oddycha"));
        keywordDAO.insert(new Keyword(5L, "oddechem"));
        keywordDAO.insert(new Keyword(6L, "oddech"));
        keywordDAO.insert(new Keyword(7L, "serce"));
        keywordDAO.insert(new Keyword(8L, "serca"));
        keywordDAO.insert(new Keyword(9L, "sercu"));
        keywordDAO.insert(new Keyword(10L, "oddechy"));
        keywordDAO.insert(new Keyword(11L, "oddech"));
        keywordDAO.insert(new Keyword(12L, "tchu"));
        keywordDAO.insert(new Keyword(13L, "brak"));
        keywordDAO.insert(new Keyword(14L, "cpr"));
        keywordDAO.insert(new Keyword(15L, "renanimacja"));
        keywordDAO.insert(new Keyword(16L, "reanimuj"));
        keywordDAO.insert(new Keyword(17L, "reanimowanie"));
        keywordDAO.insert(new Keyword(18L, "nieregularny"));
        keywordDAO.insert(new Keyword(19L, "zawal"));
        keywordDAO.insert(new Keyword(20L, "masaz"));
        keywordDAO.insert(new Keyword(21L, "zawalu"));
        keywordDAO.insert(new Keyword(22L, "zawalem"));
        keywordDAO.insert(new Keyword(23L, "kompresje"));
        keywordDAO.insert(new Keyword(24L, "nieprzytomny"));
        keywordDAO.insert(new Keyword(25L, "nieprzytomna"));
        keywordDAO.insert(new Keyword(26L, "przytomnosc"));

        keywordDAO.insert(new Keyword(27L, "prawica"));
        keywordDAO.insert(new Keyword(28L, "konczyna"));
        keywordDAO.insert(new Keyword(29L, "konczyny"));
        keywordDAO.insert(new Keyword(30L, "lewica"));
        keywordDAO.insert(new Keyword(31L, "bark"));
        keywordDAO.insert(new Keyword(32L, "noga"));
        keywordDAO.insert(new Keyword(33L, "nogi"));
        keywordDAO.insert(new Keyword(34L, "dlon"));
        keywordDAO.insert(new Keyword(35L, "nozne"));
        keywordDAO.insert(new Keyword(36L, "nadgarstek"));
        keywordDAO.insert(new Keyword(37L, "ramie"));
        keywordDAO.insert(new Keyword(38L, "udo"));
        keywordDAO.insert(new Keyword(39L, "temblak"));
        keywordDAO.insert(new Keyword(40L, "kolano"));
        keywordDAO.insert(new Keyword(41L, "zamkniete"));
        keywordDAO.insert(new Keyword(42L, "nogi"));
        keywordDAO.insert(new Keyword(43L, "odnoze"));
        keywordDAO.insert(new Keyword(44L, "gica"));
        keywordDAO.insert(new Keyword(45L, "przedramie"));
        keywordDAO.insert(new Keyword(46L, "lapa"));
        keywordDAO.insert(new Keyword(47L, "lewa"));
        keywordDAO.insert(new Keyword(48L, "prawa"));
        keywordDAO.insert(new Keyword(49L, "reki"));
        keywordDAO.insert(new Keyword(50L, "reka"));
        keywordDAO.insert(new Keyword(51L, "ramie"));
        keywordDAO.insert(new Keyword(52L, "rece"));

        keywordDAO.insert(new Keyword(53L, "nudnosci"));
        keywordDAO.insert(new Keyword(54L, "gorac"));
        keywordDAO.insert(new Keyword(55L, "skrajne"));
        keywordDAO.insert(new Keyword(56L, "majaczenie"));
        keywordDAO.insert(new Keyword(57L, "rozkojarzenie"));
        keywordDAO.insert(new Keyword(58L, "udar"));
        keywordDAO.insert(new Keyword(59L, "udaru"));
        keywordDAO.insert(new Keyword(60L, "mowa"));
        keywordDAO.insert(new Keyword(61L, "omdlenie"));
        keywordDAO.insert(new Keyword(62L, "omdlenia"));
        keywordDAO.insert(new Keyword(63L, "wymowa"));
        keywordDAO.insert(new Keyword(64L, "pot"));
        keywordDAO.insert(new Keyword(65L, "pocenie"));
        keywordDAO.insert(new Keyword(66L, "wymioty"));
        keywordDAO.insert(new Keyword(67L, "wymiot"));
        keywordDAO.insert(new Keyword(68L, "bol"));
        keywordDAO.insert(new Keyword(69L, "bolu"));
        keywordDAO.insert(new Keyword(70L, "bole"));
        keywordDAO.insert(new Keyword(71L, "slonce"));
        keywordDAO.insert(new Keyword(72L, "sloneczny"));
        keywordDAO.insert(new Keyword(73L, "przytomnosc"));
        keywordDAO.insert(new Keyword(74L, "cieplo"));
        keywordDAO.insert(new Keyword(75L, "goraco"));
        keywordDAO.insert(new Keyword(76L, "slabosc"));
        keywordDAO.insert(new Keyword(77L, "nadcisnienie"));
        keywordDAO.insert(new Keyword(78L, "kontuzja"));

        keywordDAO.insert(new Keyword(79L, "posilkiem"));
        keywordDAO.insert(new Keyword(80L, "dlawienie"));
        keywordDAO.insert(new Keyword(81L, "nudnosci"));
        keywordDAO.insert(new Keyword(82L, "zatkany"));
        keywordDAO.insert(new Keyword(83L, "oddycha"));
        keywordDAO.insert(new Keyword(84L, "oddychanie"));
        keywordDAO.insert(new Keyword(85L, "tchu"));
        keywordDAO.insert(new Keyword(86L, "jedzenie"));
        keywordDAO.insert(new Keyword(87L, "jedzeniem"));
        keywordDAO.insert(new Keyword(88L, "zatkane"));
        keywordDAO.insert(new Keyword(89L, "zablokowane"));
        keywordDAO.insert(new Keyword(90L, "zatkana"));
        keywordDAO.insert(new Keyword(91L, "zablokowana"));
        keywordDAO.insert(new Keyword(92L, "wyziewy"));
        keywordDAO.insert(new Keyword(93L, "szarpany"));
        keywordDAO.insert(new Keyword(94L, "oddech"));
        keywordDAO.insert(new Keyword(95L, "gardle"));
        keywordDAO.insert(new Keyword(96L, "przełyk"));
        keywordDAO.insert(new Keyword(97L, "przelyk"));
        keywordDAO.insert(new Keyword(98L, "gardlo"));
        keywordDAO.insert(new Keyword(99L, "niemoznosc"));
        keywordDAO.insert(new Keyword(100L, "przelykanie"));
        keywordDAO.insert(new Keyword(101L, "dlawi"));
        keywordDAO.insert(new Keyword(102L, "dlawia"));
        keywordDAO.insert(new Keyword(103L, "zadlawienie"));
        keywordDAO.insert(new Keyword(104L, "wypluc"));

        keywordDAO.insert(new Keyword(105L, "wstrzasy"));
        keywordDAO.insert(new Keyword(106L, "krwotok"));
        keywordDAO.insert(new Keyword(107L, "krew"));
        keywordDAO.insert(new Keyword(108L, "krwawienie"));
        keywordDAO.insert(new Keyword(109L, "krwawi"));
        keywordDAO.insert(new Keyword(110L, "wykrwawia"));
        keywordDAO.insert(new Keyword(111L, "wykrwawi"));
        keywordDAO.insert(new Keyword(112L, "traci"));
        keywordDAO.insert(new Keyword(113L, "krwi"));
        keywordDAO.insert(new Keyword(114L, "utrata"));
        keywordDAO.insert(new Keyword(115L, "przytomnosc"));
        keywordDAO.insert(new Keyword(116L, "slabosc"));
        keywordDAO.insert(new Keyword(117L, "niskie"));
        keywordDAO.insert(new Keyword(118L, "cisnienie"));
        keywordDAO.insert(new Keyword(119L, "wychlodzenie"));
        keywordDAO.insert(new Keyword(120L, "bladosc"));
        keywordDAO.insert(new Keyword(121L, "blady"));
        keywordDAO.insert(new Keyword(122L, "blada"));
        keywordDAO.insert(new Keyword(123L, "pot"));
        keywordDAO.insert(new Keyword(124L, "pocenie"));
        keywordDAO.insert(new Keyword(125L, "niska"));
        keywordDAO.insert(new Keyword(126L, "temperatura"));
        keywordDAO.insert(new Keyword(127L, "zaczerwienienie"));
        keywordDAO.insert(new Keyword(128L, "czerwona"));
        keywordDAO.insert(new Keyword(129L, "skora"));
        keywordDAO.insert(new Keyword(130L, "skapomocz"));

        keywordDAO.insert(new Keyword(131L, "woda"));
        keywordDAO.insert(new Keyword(132L, "tonie"));
        keywordDAO.insert(new Keyword(133L, "topi"));
        keywordDAO.insert(new Keyword(134L, "wodzie"));
        keywordDAO.insert(new Keyword(135L, "plywanie"));
        keywordDAO.insert(new Keyword(136L, "plywania"));
        keywordDAO.insert(new Keyword(137L, "hipotermia"));

        keywordDAO.insert(new Keyword(138L, "gryzie"));
        keywordDAO.insert(new Keyword(139L, "ugryzl"));
        keywordDAO.insert(new Keyword(140L, "szczeka"));
        keywordDAO.insert(new Keyword(141L, "goni"));
        keywordDAO.insert(new Keyword(142L, "atakuje"));
        keywordDAO.insert(new Keyword(143L, "skacze"));
        keywordDAO.insert(new Keyword(144L, "warczy"));
        keywordDAO.insert(new Keyword(145L, "obszczekuje"));
        keywordDAO.insert(new Keyword(146L, "agresywny"));

        keywordDAO.insert(new Keyword(147L, "kontuzja"));
        keywordDAO.insert(new Keyword(148L, "potluczenie"));
        keywordDAO.insert(new Keyword(149L, "wywichniecie"));
        keywordDAO.insert(new Keyword(150L, "zlamanie"));
        keywordDAO.insert(new Keyword(151L, "skrecenie"));
        keywordDAO.insert(new Keyword(152L, "stluczenie"));
        keywordDAO.insert(new Keyword(153L, "wypaczenie"));
        keywordDAO.insert(new Keyword(154L, "fraktura"));
        keywordDAO.insert(new Keyword(155L, "zranienie"));
        keywordDAO.insert(new Keyword(156L, "obrazenie"));
        keywordDAO.insert(new Keyword(157L, "uszkodzenie"));
        keywordDAO.insert(new Keyword(158L, "uraz"));
        keywordDAO.insert(new Keyword(159L, "ranienie"));
        keywordDAO.insert(new Keyword(160L, "obtluczenie"));
        keywordDAO.insert(new Keyword(161L, "kontuzjowanie"));
        keywordDAO.insert(new Keyword(162L, "nadwyrezenie"));
        keywordDAO.insert(new Keyword(163L, "nadwyrezony"));

        keywordDAO.insert(new Keyword(164L, "tchnienie"));
        keywordDAO.insert(new Keyword(165L, "oddech"));
        keywordDAO.insert(new Keyword(166L, "respiracja"));
        keywordDAO.insert(new Keyword(167L, "dech"));

        keywordDAO.insert(new Keyword(168L, "kosc"));
        keywordDAO.insert(new Keyword(169L, "kosci"));
        keywordDAO.insert(new Keyword(170L, "zlamana"));
        keywordDAO.insert(new Keyword(171L, "zlamane"));
        keywordDAO.insert(new Keyword(172L, "zlamanie"));
        keywordDAO.insert(new Keyword(173L, "zlamania"));
        keywordDAO.insert(new Keyword(174L, "wystaje"));
        keywordDAO.insert(new Keyword(175L, "wylamana"));
        keywordDAO.insert(new Keyword(176L, "wylamany"));
        keywordDAO.insert(new Keyword(177L, "wylamane"));
        keywordDAO.insert(new Keyword(178L, "otwarte"));
        keywordDAO.insert(new Keyword(179L, "wystajaca"));
        keywordDAO.insert(new Keyword(180L, "roztrzaskanie"));
        keywordDAO.insert(new Keyword(181L, "strzaskanie"));

        keywordDAO.insert(new Keyword(182L, "nadwyrezenie"));
        keywordDAO.insert(new Keyword(183L, "przeforsowanie"));
        keywordDAO.insert(new Keyword(184L, "nadwyrezony"));
        keywordDAO.insert(new Keyword(185L, "przeforsowany"));
        keywordDAO.insert(new Keyword(186L, "nadwatlenie"));
        keywordDAO.insert(new Keyword(187L, "wyzylowanie"));
        keywordDAO.insert(new Keyword(188L, "przeciazenie"));
        keywordDAO.insert(new Keyword(189L, "przeciazony"));
        keywordDAO.insert(new Keyword(190L, "naruszenie"));
        keywordDAO.insert(new Keyword(191L, "objuczenie"));
        keywordDAO.insert(new Keyword(192L, "naruszony"));
        keywordDAO.insert(new Keyword(193L, "naruszona"));
        keywordDAO.insert(new Keyword(194L, "przeciazona"));
        keywordDAO.insert(new Keyword(195L, "obwieszenie"));

        keywordDAO.insert(new Keyword(196L, "glowy"));
        keywordDAO.insert(new Keyword(197L, "umysl"));
        keywordDAO.insert(new Keyword(198L, "mozg"));
        keywordDAO.insert(new Keyword(199L, "rozum"));
        keywordDAO.insert(new Keyword(200L, "uposledzenie"));
        keywordDAO.insert(new Keyword(201L, "uposledzony"));
        keywordDAO.insert(new Keyword(202L, "uposledzona"));
        keywordDAO.insert(new Keyword(203L, "uposledzone"));
        keywordDAO.insert(new Keyword(204L, "kognitywny"));
        keywordDAO.insert(new Keyword(205L, "kognitywna"));
        keywordDAO.insert(new Keyword(206L, "kognitywne"));
        keywordDAO.insert(new Keyword(207L, "intelekt"));

        keywordDAO.insert(new Keyword(208L, "trutka"));
        keywordDAO.insert(new Keyword(209L, "trutki"));
        keywordDAO.insert(new Keyword(210L, "jad"));
        keywordDAO.insert(new Keyword(211L, "toksyna"));
        keywordDAO.insert(new Keyword(212L, "toksyczne"));
        keywordDAO.insert(new Keyword(213L, "toksyczna"));
        keywordDAO.insert(new Keyword(214L, "toksyczny"));
        keywordDAO.insert(new Keyword(215L, "szkodliwe"));
        keywordDAO.insert(new Keyword(216L, "toksykant"));
        keywordDAO.insert(new Keyword(217L, "substancja"));
        keywordDAO.insert(new Keyword(218L, "substancje"));

        keywordDAO.insert(new Keyword(219L, "obtarcie"));
        keywordDAO.insert(new Keyword(220L, "ranka"));
        keywordDAO.insert(new Keyword(221L, "przestrzelina"));
        keywordDAO.insert(new Keyword(222L, "zadrasniecie"));
        keywordDAO.insert(new Keyword(223L, "starcie"));
        keywordDAO.insert(new Keyword(224L, "ciecie"));
        keywordDAO.insert(new Keyword(225L, "zaciecie"));
        keywordDAO.insert(new Keyword(226L, "ciecia"));
        keywordDAO.insert(new Keyword(227L, "okaleczenie"));
        keywordDAO.insert(new Keyword(228L, "skaleczenie"));
        keywordDAO.insert(new Keyword(229L, "drasniecie"));
        keywordDAO.insert(new Keyword(230L, "rozciecie"));

        keywordDAO.insert(new Keyword(231L, "oparzenie"));
        keywordDAO.insert(new Keyword(232L, "spieczenie"));
        keywordDAO.insert(new Keyword(233L, "oparzony"));
        keywordDAO.insert(new Keyword(234L, "poparzony"));
        keywordDAO.insert(new Keyword(235L, "spieczony"));
        keywordDAO.insert(new Keyword(236L, "spieczona"));
        keywordDAO.insert(new Keyword(237L, "oparzony"));
        keywordDAO.insert(new Keyword(238L, "bable"));

        keywordDAO.insert(new Keyword(239L, "morski"));
        keywordDAO.insert(new Keyword(240L, "nadwodne"));
        keywordDAO.insert(new Keyword(241L, "nadwodny"));
        keywordDAO.insert(new Keyword(242L, "nadwodna"));
        keywordDAO.insert(new Keyword(243L, "morskie"));
        keywordDAO.insert(new Keyword(244L, "morska"));
        keywordDAO.insert(new Keyword(245L, "brzeg"));
        keywordDAO.insert(new Keyword(246L, "nadbrzezny"));
        keywordDAO.insert(new Keyword(247L, "nadbrzezna"));
        keywordDAO.insert(new Keyword(248L, "nadbrzezne"));
        keywordDAO.insert(new Keyword(249L, "przybrzezny"));
        keywordDAO.insert(new Keyword(250L, "przybrzezna"));
        keywordDAO.insert(new Keyword(251L, "przybrzezne"));
        keywordDAO.insert(new Keyword(252L, "woda"));
        keywordDAO.insert(new Keyword(253L, "wody"));
        keywordDAO.insert(new Keyword(254L, "wodzie"));

        keywordDAO.insert(new Keyword(255L, "szok"));
        keywordDAO.insert(new Keyword(256L, "szoku"));
        keywordDAO.insert(new Keyword(257L, "oszolomienie"));
        keywordDAO.insert(new Keyword(258L, "oszolomiony"));
        keywordDAO.insert(new Keyword(259L, "oszolomiona"));
        keywordDAO.insert(new Keyword(260L, "zdretwienie"));
        keywordDAO.insert(new Keyword(261L, "zdretwiona"));
        keywordDAO.insert(new Keyword(262L, "zdretwiony"));
        keywordDAO.insert(new Keyword(263L, "zdretwione"));
        keywordDAO.insert(new Keyword(264L, "wzburzenie"));
        keywordDAO.insert(new Keyword(265L, "wzburzony"));
        keywordDAO.insert(new Keyword(266L, "wzburzone"));
        keywordDAO.insert(new Keyword(267L, "wzburzona"));

        keywordDAO.insert(new Keyword(268L, "jama"));
        keywordDAO.insert(new Keyword(269L, "jamie"));
        keywordDAO.insert(new Keyword(270L, "jamy"));
        keywordDAO.insert(new Keyword(271L, "szczeka"));
        keywordDAO.insert(new Keyword(272L, "szczeki"));
        keywordDAO.insert(new Keyword(273L, "szczece"));
        keywordDAO.insert(new Keyword(274L, "zab"));
        keywordDAO.insert(new Keyword(275L, "zeby"));
        keywordDAO.insert(new Keyword(276L, "zebach"));
        keywordDAO.insert(new Keyword(277L, "zebie"));
        keywordDAO.insert(new Keyword(278L, "zeba"));
        keywordDAO.insert(new Keyword(279L, "buzia"));
        keywordDAO.insert(new Keyword(280L, "buzi"));
        keywordDAO.insert(new Keyword(281L, "zebow"));
        keywordDAO.insert(new Keyword(282L, "jezyk"));
        keywordDAO.insert(new Keyword(283L, "podniebienie"));
        keywordDAO.insert(new Keyword(284L, "wargi"));

        keywordDAO.insert(new Keyword(285L, "obciecie"));
        keywordDAO.insert(new Keyword(286L, "obciety"));
        keywordDAO.insert(new Keyword(287L, "obcieta"));
        keywordDAO.insert(new Keyword(288L, "obciete"));
        keywordDAO.insert(new Keyword(289L, "odcieta"));
        keywordDAO.insert(new Keyword(290L, "odciety"));
        keywordDAO.insert(new Keyword(291L, "odciete"));
        keywordDAO.insert(new Keyword(292L, "wyrwany"));
        keywordDAO.insert(new Keyword(293L, "wyrwana"));
        keywordDAO.insert(new Keyword(294L, "wyrwane"));
        keywordDAO.insert(new Keyword(295L, "odjeta"));
        keywordDAO.insert(new Keyword(296L, "odjety"));
        keywordDAO.insert(new Keyword(297L, "odjecie"));
        keywordDAO.insert(new Keyword(298L, "sciety"));
        keywordDAO.insert(new Keyword(299L, "scieta"));
        keywordDAO.insert(new Keyword(300L, "sciete"));
        keywordDAO.insert(new Keyword(301L, "pozbawiony"));
        keywordDAO.insert(new Keyword(302L, "pozbawiona"));
        keywordDAO.insert(new Keyword(303L, "pozbawione"));
        keywordDAO.insert(new Keyword(304L, "amputowanie"));
        keywordDAO.insert(new Keyword(305L, "amputowany"));
        keywordDAO.insert(new Keyword(306L, "amputowane"));
        keywordDAO.insert(new Keyword(307L, "amputowana"));

        keywordDAO.insert(new Keyword(308L, "poryw"));
        keywordDAO.insert(new Keyword(309L, "przeprawa"));
        keywordDAO.insert(new Keyword(310L, "sztorm"));
        keywordDAO.insert(new Keyword(311L, "opady"));
        keywordDAO.insert(new Keyword(312L, "blyskawice"));
        keywordDAO.insert(new Keyword(313L, "urwanie"));
        keywordDAO.insert(new Keyword(314L, "wicher"));
        keywordDAO.insert(new Keyword(315L, "zawierucha"));
        keywordDAO.insert(new Keyword(316L, "nawalnica"));
        keywordDAO.insert(new Keyword(317L, "nawalnicy"));
        keywordDAO.insert(new Keyword(318L, "pozoga"));

        keywordDAO.insert(new Keyword(319L, "ognisko"));
        keywordDAO.insert(new Keyword(320L, "niedopal"));
        keywordDAO.insert(new Keyword(321L, "zaplon"));
        keywordDAO.insert(new Keyword(322L, "zar"));
        keywordDAO.insert(new Keyword(323L, "drzewo"));
        keywordDAO.insert(new Keyword(324L, "drzewa"));
        keywordDAO.insert(new Keyword(325L, "krzew"));
        keywordDAO.insert(new Keyword(326L, "krzewy"));
        keywordDAO.insert(new Keyword(327L, "dicz"));
        keywordDAO.insert(new Keyword(328L, "plener"));
        keywordDAO.insert(new Keyword(329L, "srodowisko"));

        keywordDAO.insert(new Keyword(330L, "drganie"));
        keywordDAO.insert(new Keyword(331L, "drgania"));
        keywordDAO.insert(new Keyword(332L, "wstrzas"));
        keywordDAO.insert(new Keyword(333L, "wstrzasy"));
        keywordDAO.insert(new Keyword(334L, "sypkie"));

        keywordDAO.insert(new Keyword(335L, "snieg"));
        keywordDAO.insert(new Keyword(336L, "zawieja"));
        keywordDAO.insert(new Keyword(337L, "calun"));
        keywordDAO.insert(new Keyword(338L, "zamiec"));

        keywordDAO.insert(new Keyword(339L, "huk"));
        keywordDAO.insert(new Keyword(340L, "nawal"));
        keywordDAO.insert(new Keyword(341L, "natlok"));
        keywordDAO.insert(new Keyword(342L, "zaspa"));
        keywordDAO.insert(new Keyword(343L, "zaspy"));
        keywordDAO.insert(new Keyword(344L, "masa"));

        keywordDAO.insert(new Keyword(345L, "traba"));
        keywordDAO.insert(new Keyword(346L, "raban"));
        keywordDAO.insert(new Keyword(347L, "zefir"));
        keywordDAO.insert(new Keyword(348L, "cyklon"));
        keywordDAO.insert(new Keyword(349L, "tajfun"));
        keywordDAO.insert(new Keyword(350L, "wietrzysko"));
        keywordDAO.insert(new Keyword(351L, "zawieja"));
        keywordDAO.insert(new Keyword(352L, "halny"));

        keywordDAO.insert(new Keyword(353L, "potop"));
        keywordDAO.insert(new Keyword(354L, "zalanie"));
        keywordDAO.insert(new Keyword(355L, "zalana"));
        keywordDAO.insert(new Keyword(356L, "rzeka"));
        keywordDAO.insert(new Keyword(357L, "wylala"));
        keywordDAO.insert(new Keyword(358L, "wylalo"));
        keywordDAO.insert(new Keyword(359L, "rozlew"));
        keywordDAO.insert(new Keyword(360L, "rozlalo"));
        keywordDAO.insert(new Keyword(361L, "zalalo"));
        keywordDAO.insert(new Keyword(362L, "zalala"));
        keywordDAO.insert(new Keyword(363L, "potok"));

        keywordDAO.insert(new Keyword(364L, "hipotermia"));
        keywordDAO.insert(new Keyword(365L, "wyziebienie"));
        keywordDAO.insert(new Keyword(366L, "wyziebiony"));
        keywordDAO.insert(new Keyword(367L, "wyziebiona"));
        keywordDAO.insert(new Keyword(368L, "przemarzniecie"));
        keywordDAO.insert(new Keyword(369L, "przemarzniety"));
        keywordDAO.insert(new Keyword(370L, "przemarznieta"));
        keywordDAO.insert(new Keyword(371L, "przemarzniete"));
        keywordDAO.insert(new Keyword(372L, "oziebienie"));
        keywordDAO.insert(new Keyword(373L, "schlodzenie"));
        keywordDAO.insert(new Keyword(374L, "ochlodazenie"));
        keywordDAO.insert(new Keyword(375L, "zmarzniecie"));
        keywordDAO.insert(new Keyword(376L, "zamarzniecie"));
        keywordDAO.insert(new Keyword(377L, "ziebica"));
    }

    public void populateTagKeywords(TagKeywordDAO tagKeywordDAO)
    {
        for(long i = 0L; i < 27L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 12L));
        }
        for(long i = 27L; i < 53L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 13L));
        }
        for(long i = 53L; i < 79L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 14L));
        }
        for(long i = 79L; i < 105L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 19L));
        }
        for(long i = 105L; i < 131L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 35L));
        }
        for(long i = 131L; i < 138L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 38L));
        }
        for(long i = 138L; i < 147L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 21L));
        }
        for(long i = 147L; i < 164L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 7L));
        }
        for(long i = 164L; i < 168L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 6L));
        }
        for(long i = 168L; i < 182L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 8L));
        }
        for(long i = 182L; i < 196L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 9L));
        }
        for(long i = 196L; i < 208L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 10L));
        }
        for(long i = 208L; i < 219L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 16L));
        }
        for(long i = 220L; i < 231L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 31L));
        }
        for(long i = 231L; i < 239L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 32L));
        }
        for(long i = 239L; i < 255L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 33L));
        }
        for(long i = 255L; i < 268L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 34L));
        }
        for(long i = 268L; i < 285L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 36L));
        }
        for(long i = 285L; i < 308L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 37L));
        }
        for(long i = 308L; i < 319L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 41L));
        }
        for(long i = 319L; i < 330L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 42L));
        }
        for(long i = 330L; i < 335L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 43L));
        }
        for(long i = 335L; i < 339L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 46L));
        }
        for(long i = 339L; i < 345L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 44L));
        }
        for(long i = 345L; i < 353L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 45L));
        }
        for(long i = 353L; i < 364L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 47L));
        }
        for(long i = 364L; i < 378L; i++) {
            tagKeywordDAO.insert(new TagKeyword(i, i, 48L));
        }
    }

    public void populateInstructionSets(InstructionSetDAO instructionDAO)
    {
        //tutorial 0
        instructionDAO.insert(new InstructionSet(0L, "Wstęp", "Jeżeli ofiara nie jest w stanie samodzielnie oddychać…", 5000, 0L, 0, "s0_0.m4a"));
        instructionDAO.insert(new InstructionSet(1L, "Ułożenie ofiary", "Upewnij się, że ofiara leży na plecach, jest ułożona prosto, a jej drogi oddechowe są udrożnione.", 8000, 0L, 1, "s0_1.m4a"));
        instructionDAO.insert(new InstructionSet(2L, "Pozycja do udzielania pomocy", "Uklęknij wygodnie nad ofiarą tak, by twoje dłonie mogły być wyprostowane prostopadle do jej klatki piersiowej.", 8000, 0L, 2, "s0_2.m4a"));
        instructionDAO.insert(new InstructionSet(3L, "Ułożenie dłoni", "Umieść dłoń na środku klatki piersiowej, drugą dłoń umieść nad pierwszą tak, aby palce się przeplatały.", 9000, 0L, 3, "s0_3.m4a"));
        instructionDAO.insert(new InstructionSet(4L, "Palce", "Nie wywieraj nacisku na klatkę piersiową palcami, utrzymaj je lekko uniesione i splecione.", 8000, 0L, 4, "s0_4.m4a"));
        instructionDAO.insert(new InstructionSet(5L, "Głębokość uciśnięć", "Staraj się wywierać nacisk o 5 centymetrów prosto w dół ciężarem swojego ciała.", 7000, 0L, 5, "s0_5.m4a"));
        instructionDAO.insert(new InstructionSet(6L, "Uciśnięcia", "Kontynuuj uciśnięcia do momentu przybycia pomocy zgodnie z tempem dźwięku który słyszysz w tle.", 14000, 0L, 6, "s0_6.m4a"));
        instructionDAO.insert(new InstructionSet(7L, "W razie zwymiotowania ofiary", "Jeśli ofiara zwymiotuje w trakcie, przekręć ją na bok tak by głowa była skierowana w dół i poczekaj aż jej usta się opróżnią, przetrzyj je, po czym wróć do procedury.", 8000, 0L, 7, "s0_7.m4a"));
        //tutorial 1
        instructionDAO.insert(new InstructionSet(8L, "Wstęp", "Jeżeli istnieje podejrzenie złamania", 3500, 1L, 0, "s1_0.aac"));
        instructionDAO.insert(new InstructionSet(9L, "Nie udało się wezwać pomocy", "...i nie możesz dosięgnąć numeru alarmowego", 3000, 1L, 1, "s1_1.aac"));
        instructionDAO.insert(new InstructionSet(10L, "Upomnienie ofiary", "Ofiara powinna pozostać w bezruchu. Złamane fragmenty kostne są ostre, i każdy ruch stwarza ryzyko dalszego okaleczenia.", 8500, 1L, 2, "s1_2.aac"));
        instructionDAO.insert(new InstructionSet(11L, "Upomnienie użytkownika", "Nie próbuj ustawiać ani prostować kończyny, nie przemieszczaj jej w żaden sposób.", 6000, 1L, 3, "s1_3.aac"));
        instructionDAO.insert(new InstructionSet(12L, "Upomnienie użytkownika, złamanie otwarte", "Nie dotykaj wystającego fragmentu kostnego.", 4000, 1L, 4, "s1_4.aac"));
        instructionDAO.insert(new InstructionSet(13L, "Pomoc wezwana, koniec", "Nie podejmuj dalszych działań. Upewnij się, że ofiara jest bezpieczna i ułożona bądź usadzona w wygodnej pozycji.", 8000, 1L, 5, "s1_5.aac"));
        instructionDAO.insert(new InstructionSet(14L, "Jedzenie i picie", "Nie pozwalaj ofierze niczego jeść ani pić, może to mieć negatywny wpływ na działanie znieczulenia.", 7000, 1L, 6, "s1_6.aac"));
        instructionDAO.insert(new InstructionSet(15L, "Unieruchamianie kości", "Unieruchomienie kończyny jest wymagane w celu przetransportowania ofiary do najbliższego szpitala.", 6500, 1L, 7, "s1_7.aac"));
        instructionDAO.insert(new InstructionSet(24L, "Upomnienie o stawach", "Unieruchomione muszą zostać również dwa sąsiadujące z kością stawy.", 5500, 1L, 8, "s1_8.aac"));
        instructionDAO.insert(new InstructionSet(16L, "Unieruchamianie dolnej kończyny", "Częściowe unieruchomienie kończyny dolnej może zostać osiągnięte poprzez przymocowanie jej do drugiej kończyny.", 7000, 1L, 9, "s1_9.aac"));
        instructionDAO.insert(new InstructionSet(23L, "Unieruchomienie zdrowej kończyny", "Należy w takim wypadku w miarę możliwości odpowiednio usztywnić też zdrową kończynę.", 6000, 1L, 10, "s1_10.aac"));
        instructionDAO.insert(new InstructionSet(17L, "Upomnienie o wiązaniu kończyn", "W przypadku gdy rozwiązanie to nie będzie wygodne dla ofiary, nie należy go stosować.", 7000, 1L, 11, "s1_11.aac"));
        instructionDAO.insert(new InstructionSet(18L, "Temblak", "Wykonanie prowizorycznego temblaka do usztywniania górnej kończyny zaprezentowane zostało w galerii zdjęć.", 8500, 1L, 12, "s1_12.aac"));
        instructionDAO.insert(new InstructionSet(19L, "Opatrywanie złamania otwartego", "Opatrunek złamania otwartego należy wykonać poprzez stopniowe owijanie czystego materiału wokół i ponad, lecz nie bezpośrednio na miejscu złamania.", 10000, 1L, 13, "s1_13.aac"));
        instructionDAO.insert(new InstructionSet(20L, "Upomienie o złamaniu otwartym", "Nie należy stosować łatwo rozpadających się przedmiotów takich jak waciki do podwyższenia opatrunku.", 7000, 1L, 14, "s1_14.aac"));
        instructionDAO.insert(new InstructionSet(21L, "Opatrywanie złamanie otwartego ciąg dalszy", "Należy wykonać dostatecznie dużo warstw, aby opatrunek bezpośrednio nad raną nie wywierał nacisku na kość.", 7000, 1L, 15, "s1_15.aac"));
        instructionDAO.insert(new InstructionSet(22L, "Upomnienie o nacisku na kość", "Każdy nacisk na wystający fragment kostny powoduje komplikacje.", 5000, 1L, 16, "s1_16.aac"));
        instructionDAO.insert(new InstructionSet(25L, "Sprawdzanie krwiobiegu - wezwana pomoc", "Do czasu przybycia pomocy sprawdzaj co kilka minut ukrwienie kończyny poniżej opatrunku…", 7500, 1L, 17, "s1_17.aac"));
        instructionDAO.insert(new InstructionSet(26L, "Sprawdzanie krwiobiegu - niewezwana pomoc", "Jeżeli jest z tobą osoba towarzysząca, poproś ją o sprawdzanie co kilka minut ukrwienia kończyny poniżej opatrunku…", 9000, 1L, 18, "s1_18.aac"));
        instructionDAO.insert(new InstructionSet(27L, "Upomnienie o krwiobiegu", "…w celu upewnienia się, że sztywny opatrunek go nie tamuje. W przypadku gdy stopień ukrwienia jest obniżony, należy poluzować opatrunek.", 9000, 1L, 19, "s1_19.aac"));
        instructionDAO.insert(new InstructionSet(28L, "Wstrząs", "Ryzyko wstrząsu, spowodowanego utratą dużej ilości krwi omówione jest w oddzielnym poradniku. W razie potrzeby naciśnij tutaj aby do niego przejść.", 10000, 1L, 20, "s1_20.aac"));
        //tutorial 2
        instructionDAO.insert(new InstructionSet(56L, "Podstawowe kroki", "Zabierz ofiarę do chłodnego miejsca i pomóż jej zdjąć jak najwięcej ubrań. Zadzwoń na pogotowie.", 5000, 2L, 0, null));
        instructionDAO.insert(new InstructionSet(57L, "Usadzenie ofiary", "Pomóż ofierze usiąść w wygodnym miejscu.", 5000, 2L, 1, null));
        instructionDAO.insert(new InstructionSet(58L, "Obniżenie temperatury ciała", "Postaraj się obniżyć temperaturę ciała w dowolny możliwy sposób. Sugerujemy polewanie wodą, okrycie wilgotnym materiałem, lód oraz uruchomienie wentylacji.", 5000, 2L, 2, null));
        instructionDAO.insert(new InstructionSet(59L, "Zaprzestania ochładzania", "Jeżeli uważasz, że temperatura ciała ofiary jest dostatecznie niska, pomóż jej się osuszyć i zaprzestań ochładzania.", 5000, 2L, 3, null));
        instructionDAO.insert(new InstructionSet(60L, "Powrót do ochładzania", "Temperatura może znowu wzrosnąć, jeżeli do tego dojdzie, wróć do ochładzania ofiary.", 5000, 2L, 4, null));
        instructionDAO.insert(new InstructionSet(61L, "Masaż serca", "Jeżeli ofiara straciła przytomność i nie ma regularnego oddechu, skieruj się do poradnika udzielania masażu serca klikając w link na ekranie i podążaj za instrukcjami.", 5000, 2L, 5, null));
        //tutorial 3
        instructionDAO.insert(new InstructionSet(29L, "Ułożenie psa", "Ułóż psa na ziemi bokiem, uspokajaj go, nie podnoś głosu.", 5000, 3L, 0, null));
        instructionDAO.insert(new InstructionSet(30L, "Wymuszanie powietrza z płuc", "Płaską ręką uderz w klatkę piersiową psa, tak, by jak najwięcej powietrza opuściło płuca.", 5000, 3L, 1, null));
        instructionDAO.insert(new InstructionSet(31L, "Powtórne uderzenia", "Spróbuj powtórzyć to trzy lub cztery razy.", 5000, 3L, 2, null));
        instructionDAO.insert(new InstructionSet(32L, "Próba wydobycia przedmiotu", "Sprawdź, czy nie jesteś w stanie wyjąć przedmiotu z pyska psa nie wkładając ręki głęboko.", 5000, 3L, 3, null));
        instructionDAO.insert(new InstructionSet(33L, "Uniesienie za tylne łapy", "Ostatecznie podnieś psa na nogi i unieś go za tylne łapy, tak aby był skierowany pyskiem w dół.", 5000, 3L, 4, null));
        instructionDAO.insert(new InstructionSet(34L, "Potrząsanie psem", "Jeżeli jesteś w stanie, spróbuj energicznie podnosić i opuszczać psa, lub nawet nim potrząsać.", 5000, 3L, 5, null));
        instructionDAO.insert(new InstructionSet(35L, "W razie niepowodzenia", "Jeżeli nie udało ci się wydobyć obiektu, zabierz natychmiast pupila do weterynarza lub zadzwoń na pogotowie weterynaryjne.", 5000, 3L, 6, null));
        instructionDAO.insert(new InstructionSet(36L, "Numer pogotowia", "Numer alarmowy do pogotowia weterynaryjnego to 983.", 5000, 3L, 7, null));
        //tutorial 5
        instructionDAO.insert(new InstructionSet(37L, "Ułożenie kota", "Ułóż kota na ziemi bokiem, nie podnoś głosu.", 5000, 5L, 0, null));
        instructionDAO.insert(new InstructionSet(38L, "Wymuszenie powietrza z płuc", "Płaską ręką klepnij w klatkę piersiową kota, tak, by jak najwięcej powietrza opuściło płuca.", 5000, 5L, 1, null));
        instructionDAO.insert(new InstructionSet(39L, "Powtórne uderzenia", "Spróbuj powtórzyć to trzy lub cztery razy.", 5000, 5L, 2, null));
        instructionDAO.insert(new InstructionSet(40L, "Próba wydobycia przedmiotu", "Sprawdź, czy nie jesteś w stanie wyjąć przedmiotu z pyszczka kota nie wkładając palców głęboko.", 5000, 5L, 3, null));
        instructionDAO.insert(new InstructionSet(41L, "Uniesienie za tylne łapy", "Ostatecznie podnieś kota na nogi i unieś go za tyle łapy, tak aby był skierowany pyszczkiem w dół.", 5000, 5L, 4, null));
        instructionDAO.insert(new InstructionSet(42L, "Potrząsanie kotem", "Potrząsaj nim energicznie.", 5000, 5L, 5, null));
        instructionDAO.insert(new InstructionSet(44L, "W razie niepowodzenia", "Jeżeli nie udało ci się wydobyć obiektu, zabierz natychmiast pupila do weterynarza lub zadzwoń na pogotowie weterynaryjne.", 5000, 5L, 6, null));
        instructionDAO.insert(new InstructionSet(45L, "Numer pogotowia", "Numer alarmowy do pogotowia weterynaryjnego to 983.", 5000, 5L, 7, null));
        //tutorial 6
        instructionDAO.insert(new InstructionSet(46L, "Wstęp", "Jeżeli ofiara utraciła dużą ilość krwi lub innych płynów", 5000, 6L, 0, null));
        instructionDAO.insert(new InstructionSet(47L, "Ułożenie ofiary", "Ułóż ofiarę, najlepiej na kocu lub ubraniach", 5000, 6L, 1, null));
        instructionDAO.insert(new InstructionSet(48L, "Dopływ krwi do organów", "Unieś oraz wesprzyj jej nogi, tak, aby były wyżej od klatki piersiowej, w celu zapewnienia większego zasobu krwi organom witalnym.", 5000, 6L, 2, null));
        instructionDAO.insert(new InstructionSet(49L, "Udrożnienie krwiobiegu", "Rozepnij guziki w okolicach szyi, na spodniach, na rękawach, zdejmij biżuterię, tak aby nic nie tamowało krwiobiegu.", 5000, 6L, 3, null));
        instructionDAO.insert(new InstructionSet(50L, "Uspokojenie ofiary", "Pozostań przy ofierze, niepokój i strach mogą nasilić atak.", 5000, 6L, 4, null));
        instructionDAO.insert(new InstructionSet(51L, "Jedzenie i picie", "Ofiara nie powinna jeść ani pić, ponieważ może to osłabić działanie anastetyka.", 5000, 6L, 5, null));
        instructionDAO.insert(new InstructionSet(52L, "Zachowanie ciepła", "W celu zachowania ciepła otul ofiarę kocem lub ubraniami.", 5000, 6L, 6, null));
        instructionDAO.insert(new InstructionSet(53L, "Jak nie zachowywać ciepła", "Nie próbuj rozgrzewać ofiary źródłami ciepła takimi jak gorąca woda.", 5000, 6L, 7, null));
        instructionDAO.insert(new InstructionSet(54L, "Utrata przytomności", "Jeżeli ofiara straci przytomność, sprawdzaj, czy ma regularny oddech.", 5000, 6L, 8, null));
        instructionDAO.insert(new InstructionSet(55L, "Masaż serca", "Jeśli ofiera nie ma regularnego oddechu, skieruj się do poradnika udzielania masażu serca klikając w link na ekranie, i podążaj za instrukcjami.", 5000, 6L, 9, null));
        //tutorial 7
        instructionDAO.insert(new InstructionSet(62L, "Ułożenie ofiary", "Ofiara powinna leżeć na ziemi. Jej głowa powinna być jak najniżej.", 5000, 7L, 0, null));
        instructionDAO.insert(new InstructionSet(63L, "Masaż serca", "Jeżeli ofiara straciła przytomność i nie ma regularnego oddechu, natychmiast rozpocznij masaż serca. Naciśnij tutaj aby przejść do poradnika udzielania masażu serca.", 5000, 7L, 1, null));
        instructionDAO.insert(new InstructionSet(64L, "Zmiana ubrań", "Jeżeli ofiara oddycha lub udało ci się przywrócić jej oddech, należy pomóc jej pozbyć się wilgotnych ubrań i zapewnić jej suche okrycie.", 5000, 7L, 2, null));
        instructionDAO.insert(new InstructionSet(65L, "Wezwanie pomocy", "Koniecznie zadzwoń na pogotowie. Płuca ofiary mogły napełnić się wodą, musi zostać zbadana.", 5000, 7L, 3, null));
        instructionDAO.insert(new InstructionSet(66L, "Dalsze kroki", "Jeżeli istnieje taka możliwość i ofiara odzyskała pełną przytomność, znajdź więcej suchego okrycia, lub podaj jej ciepły napój. Monitoruj stan ofiary do czasu przybycia pogotowia.", 5000, 7L, 4, null));
    }

    public void populateVersions(VersionDAO versionDAO)
    {
        //tutorial 0
        versionDAO.insert(new Version(0L, "Przeprowadź mnie przez wszystkie podstawowe kroki!", 0L, true, false, false,  null));
        versionDAO.insert(new Version(1L, "Wiem, co robię, potrzebne mi jest tylko tempo!", 0L, false, false, false, null));
        //tutorial 1
        versionDAO.insert(new Version(9L, "Złamana ręka", 1L, false, true, false, null));
        versionDAO.insert(new Version(10L, "Złamana noga", 1L, false, true, false, null));
        versionDAO.insert(new Version(3L, "Udało mi się wezwać pomoc, jestem z ofiarą do czasu jej przybycia", 1L, false, true, true, 9L));
        versionDAO.insert(new Version(4L, "Muszę sam/a zawieźć ofiarę do szpitala", 1L, false, true, true, 9L));
        versionDAO.insert(new Version(11L, "Udało mi się wezwać pomoc, jestem z ofiarą do czasu jej przybycia", 1L, false, true, true, 10L));
        versionDAO.insert(new Version(12L, "Muszę sam/a zawieźć ofiarę do szpitala", 1L, false, true, true, 10L));
        versionDAO.insert(new Version(5L, "Złamanie zamknięte", 1L, false, false, true, 3L));
        versionDAO.insert(new Version(6L, "Złamanie otwarte", 1L, false, false, true, 3L));
        versionDAO.insert(new Version(7L, "Złamanie zamknięte", 1L, false, false, true, 4L));
        versionDAO.insert(new Version(8L, "Złamanie otwarte", 1L, false, false, true, 4L));
        versionDAO.insert(new Version(13L, "Złamanie zamknięte", 1L, false, false, true, 11L));
        versionDAO.insert(new Version(14L, "Złamanie otwarte", 1L, false, false, true, 11L));
        versionDAO.insert(new Version(15L, "Złamanie zamknięte", 1L, false, false, true, 12L));
        versionDAO.insert(new Version(16L, "Złamanie otwarte", 1L, false, false, true, 12L));
        //tutorial 2
        versionDAO.insert(new Version(20L, "default", 2L, false, false, false, null));
        //tutorial 3
        versionDAO.insert(new Version(17L, "default", 3L, false, false, false, null));
        //tutorial 5
        versionDAO.insert(new Version(18L, "default", 5L, false, false, false, null));
        //tutorial 6
        versionDAO.insert(new Version(19L, "default", 6L, false, false, false, null));
        //tutorial 7
        versionDAO.insert(new Version(21L, "default", 7L, false, false, false, null));
    }

    public void populateVersionSounds(SoundInVersionDAO soundInVersionDAO)
    {
        soundInVersionDAO.insert(new SoundInVersion(0L, 0L, 0L));
        soundInVersionDAO.insert(new SoundInVersion(1L, 0L, 1L));
    }

    public void populateTutorials(TutorialDAO tutorialDAO)
    {
        tutorialDAO.insert(new Tutorial(0L, "Masaż serca", 1L, "heart_massage.jpg", 0));
        tutorialDAO.insert(new Tutorial(1L, "Złamana kończyna", 1L, "broken_bone.jpeg", 0));
        tutorialDAO.insert(new Tutorial(2L, "Udar słoneczny", 1L, "sun.jpg", 0));
        tutorialDAO.insert(new Tutorial(3L, "Dławiący się pies", 1L, "choking_dog.jpeg", 0));
        tutorialDAO.insert(new Tutorial(4L, "Agresywny pies", 1L, "barking_dog.jpg", 0));
        tutorialDAO.insert(new Tutorial(5L, "Dławiący się kot", 1L, "choking_cat.jpeg", 0));
        tutorialDAO.insert(new Tutorial(6L, "Wstrząs", 1L, "circulatory_shock.jpeg", 0));
        tutorialDAO.insert(new Tutorial(7L, "Podtopienie", 1L, "drowning.jpeg", 3));
    }

    public void populateVersionInstructions(VersionInstructionDAO versionInstructionDAO)
    {
        //tutorial 0
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

        //tutorial 1
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

        //tutorial 2
        versionInstructionDAO.insert(new VersionInstruction(131L, 20L, 0));
        versionInstructionDAO.insert(new VersionInstruction(132L, 20L, 1));
        versionInstructionDAO.insert(new VersionInstruction(133L, 20L, 2));
        versionInstructionDAO.insert(new VersionInstruction(134L, 20L, 3));
        versionInstructionDAO.insert(new VersionInstruction(135L, 20L, 4));
        versionInstructionDAO.insert(new VersionInstruction(136L, 20L, 5));

        //tutorial 3
        versionInstructionDAO.insert(new VersionInstruction(105L, 17L, 0));
        versionInstructionDAO.insert(new VersionInstruction(106L, 17L, 1));
        versionInstructionDAO.insert(new VersionInstruction(107L, 17L, 2));
        versionInstructionDAO.insert(new VersionInstruction(108L, 17L, 3));
        versionInstructionDAO.insert(new VersionInstruction(109L, 17L, 4));
        versionInstructionDAO.insert(new VersionInstruction(110L, 17L, 5));
        versionInstructionDAO.insert(new VersionInstruction(111L, 17L, 6));
        versionInstructionDAO.insert(new VersionInstruction(112L, 17L, 7));

        //tutorial 5
        versionInstructionDAO.insert(new VersionInstruction(113L, 18L, 0));
        versionInstructionDAO.insert(new VersionInstruction(114L, 18L, 1));
        versionInstructionDAO.insert(new VersionInstruction(115L, 18L, 2));
        versionInstructionDAO.insert(new VersionInstruction(116L, 18L, 3));
        versionInstructionDAO.insert(new VersionInstruction(117L, 18L, 4));
        versionInstructionDAO.insert(new VersionInstruction(118L, 18L, 5));
        versionInstructionDAO.insert(new VersionInstruction(119L, 18L, 6));
        versionInstructionDAO.insert(new VersionInstruction(120L, 18L, 7));

        //tutorial 6
        versionInstructionDAO.insert(new VersionInstruction(121L, 19L, 0));
        versionInstructionDAO.insert(new VersionInstruction(122L, 19L, 1));
        versionInstructionDAO.insert(new VersionInstruction(123L, 19L, 2));
        versionInstructionDAO.insert(new VersionInstruction(124L, 19L, 3));
        versionInstructionDAO.insert(new VersionInstruction(125L, 19L, 4));
        versionInstructionDAO.insert(new VersionInstruction(126L, 19L, 5));
        versionInstructionDAO.insert(new VersionInstruction(127L, 19L, 6));
        versionInstructionDAO.insert(new VersionInstruction(128L, 19L, 7));
        versionInstructionDAO.insert(new VersionInstruction(129L, 19L, 8));
        versionInstructionDAO.insert(new VersionInstruction(130L, 19L, 9));

        //tutorial 7
        versionInstructionDAO.insert(new VersionInstruction(137L, 21L, 0));
        versionInstructionDAO.insert(new VersionInstruction(138L, 21L, 1));
        versionInstructionDAO.insert(new VersionInstruction(139L, 21L, 2));
        versionInstructionDAO.insert(new VersionInstruction(140L, 21L, 3));
        versionInstructionDAO.insert(new VersionInstruction(141L, 21L, 4));
    }

    public void populateMultimediaInVersion(VersionMultimediaDAO versionMultimediaDAO)
    {
        //tutorial 0
        versionMultimediaDAO.insert(new VersionMultimedia(0L, 0L, 0L));
        versionMultimediaDAO.insert(new VersionMultimedia(1L, 0L, 1L));

        //tutorial 1
        versionMultimediaDAO.insert(new VersionMultimedia(7L, 1L, 7L));
        versionMultimediaDAO.insert(new VersionMultimedia(8L, 2L, 7L));
        versionMultimediaDAO.insert(new VersionMultimedia(9L, 1L, 8L));
        versionMultimediaDAO.insert(new VersionMultimedia(10L, 2L, 8L));
    }

    public void populateMultimedia(MultimediaDAO multimediaDAO)
    {
        //tutorial 0
        multimediaDAO.insert(new Multimedia(0L, 0L, -1,false, "m0_0.mp4" ,true, 0));
        //tutorial 1
        multimediaDAO.insert(new Multimedia(1L, 1L, 5000, true, "m1_0.jpg", true, 0));
        multimediaDAO.insert(new Multimedia(2L, 1L, 5000, true, "m1_1.jpg", true, 1));
    }

    public void populateHelpers(HelperDAO helperDAO)
    {
        helperDAO.insert(new Helper(0L, "Ania", "Kozłowska", "", "Studentka"));
        helperDAO.insert(new Helper(1L, "Łukasz", "Orzechowski", "", "Twórca"));
        helperDAO.insert(new Helper(2L, "Kasia", "Kulpa", "", "Studentka"));
    }

    public void populateSounds(TutorialSoundDAO tutorialSoundDAO)
    {
        tutorialSoundDAO.insert(new TutorialSound(0L, 45000L, true, 545L, 0L, "g0_0.mp3"));
    }

    public void populateLinks(TutorialLinkDAO tutorialLinkDAO)
    {
        tutorialLinkDAO.insert(new TutorialLink(0L, 6L, 1L, 20));
        tutorialLinkDAO.insert(new TutorialLink(1L, 0L, 6L, 9));
        tutorialLinkDAO.insert(new TutorialLink(2L, 0L, 7L, 1));
    }

    public void populateHelperTags(HelperTagDAO helperTagDAO)
    {
        helperTagDAO.insert(new HelperTag(0L, 1L, 11L));
    }
}
