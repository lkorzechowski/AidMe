package com.orzechowski.saveme.creator.initial.soundcomposer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class SoundComposer extends Fragment implements SoundComposerAdapter.FragmentCallback
{
    private SoundComposerAdapter mAdapter;
    private final List<TutorialSound> mSounds = new LinkedList<>();
    private final ActivityCallback mCallback;
    private int mCurrentPosition;

    public SoundComposer(ActivityCallback callback)
    {
        mCallback = callback;
    }

    public int getCurrentPosition()
    {
        return mCurrentPosition;
    }

    public List<TutorialSound> getSounds()
    {
        return mSounds;
    }

    public void resetAdapterElements()
    {
        mAdapter.setElementList(mSounds);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new SoundComposerAdapter(activity, this);
        View view = inflater
                .inflate(R.layout.fragment_sound_composer, container, false);
        RecyclerView recycler = view.findViewById(R.id.new_sound_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        if(!mSounds.isEmpty()) mAdapter.setElementList(mSounds);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addSoundButton = view.findViewById(R.id.new_sound_button);
        addSoundButton.setOnClickListener(v -> {
            mSounds.add(new TutorialSound(mSounds.size(), 0,
                    false, 0, 0, ""));
            resetAdapterElements();
        });
    }

    @Override
    public void delete(TutorialSound tutorialSound)
    {
        mSounds.remove(tutorialSound);
        for(int i = (int) tutorialSound.getSoundId(); i < mSounds.size(); i++)
        {
            TutorialSound sound = mSounds.get(i);
            mSounds.remove(sound);
            mSounds.add(new TutorialSound(i, sound.getSoundStart(),
                    sound.getSoundLoop(), sound.getInterval(), 0, sound.getFileName()));
        }
        resetAdapterElements();
    }

    @Override
    public void modifyLoop(boolean loop, Long soundId)
    {
        mSounds.get(soundId.intValue()).setSoundLoop(loop);
    }

    @Override
    public void modifyStart(int start, Long soundId)
    {
        mSounds.get(soundId.intValue()).setSoundStart(start);
    }

    @Override
    public void modifyInterval(int interval, Long soundId)
    {
        mSounds.get(soundId.intValue()).setInterval(interval);
    }

    @Override
    public void addSound(int position)
    {
        mCurrentPosition = position;
        mCallback.callSoundRecycler();
    }

    public interface ActivityCallback
    {
        void callSoundRecycler();
    }
}
