package com.orzechowski.aidme.database;

import com.orzechowski.aidme.browser.categories.database.Category;
import com.orzechowski.aidme.browser.categories.database.CategoryDAO;
import com.orzechowski.aidme.browser.search.database.Keyword;
import com.orzechowski.aidme.browser.search.database.KeywordDAO;
import com.orzechowski.aidme.browser.search.database.TagKeyword;
import com.orzechowski.aidme.browser.search.database.TagKeywordDAO;
import com.orzechowski.aidme.database.tag.CategoryTag;
import com.orzechowski.aidme.database.tag.CategoryTagDAO;
import com.orzechowski.aidme.database.tag.Tag;
import com.orzechowski.aidme.database.tag.TagDAO;
import com.orzechowski.aidme.database.tag.TutorialTag;
import com.orzechowski.aidme.database.tag.TutorialTagDAO;

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
    }
}
