package com.orzechowski.saveme.creator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;
import com.orzechowski.saveme.tutorial.version.database.Version;

import java.util.ArrayList;
import java.util.List;

public class GsonConverter
{
    private final Gson mGson = new Gson();

    public List<Version> obtainVersions(String json)
    {
        return mGson.fromJson(json, new TypeToken<ArrayList<Version>>(){}.getType());
    }

    public List<Multimedia> obtainMultimedia(String json)
    {
        return mGson.fromJson(json, new TypeToken<ArrayList<Multimedia>>(){}.getType());
    }

    public List<TutorialSound> obtainSounds(String json)
    {
        return mGson.fromJson(json, new TypeToken<ArrayList<TutorialSound>>(){}.getType());
    }
}
