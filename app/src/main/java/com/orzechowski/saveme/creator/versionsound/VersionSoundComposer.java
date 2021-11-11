package com.orzechowski.saveme.creator.versionsound;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.creator.initial.VersionTextAdapter;
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.TutorialSound;
import com.orzechowski.saveme.tutorial.mediaplayer.sound.database.VersionSound;
import com.orzechowski.saveme.tutorial.version.database.Version;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class VersionSoundComposer extends Fragment
        implements VersionSoundOuterAdapter.FragmentCallback
{
    private VersionSoundOuterAdapter mOuterAdapter;
    private final List<Version> mVersions;
    private final List<TutorialSound> mSounds;
    private VersionTextAdapter mVersionTextAdapter;
    private final Collection<VersionSound> mVersionSounds = new LinkedList<>();
    private final ActivityCallback mCallback;

    public VersionSoundComposer(List<Version> versions, List<TutorialSound> sounds,
                                ActivityCallback callback)
    {
        mVersions = versions;
        mSounds = sounds;
        mCallback = callback;
        if(mSounds.isEmpty()) {
            mCallback.finalizeVersionSounds(mVersionSounds);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_version_sound, container, false);
        view.findViewById(R.id.version_sound_finalize_button).setOnClickListener(v -> {
            if(mVersionSounds.isEmpty()) {
                Toast.makeText(activity, R.string.version_sounds_not_assigned, Toast.LENGTH_SHORT)
                        .show();
            } else {
                mCallback.finalizeVersionSounds(mVersionSounds);
            }
        });
        mOuterAdapter = new VersionSoundOuterAdapter(activity, this);
        RecyclerView outerRecycler = view.findViewById(R.id.version_sound_outer_rv);
        outerRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        outerRecycler.setAdapter(mOuterAdapter);
        mVersionTextAdapter = new VersionTextAdapter(activity);
        RecyclerView versionTextRecycler = view.findViewById(R.id.version_text_rv);
        versionTextRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        versionTextRecycler.setAdapter(mVersionTextAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        mOuterAdapter.setElementList(mSounds, mVersions);
        mVersionTextAdapter.setElementList(mVersions);
    }

    @Override
    public void select(TutorialSound sound, Version version)
    {
        mVersionSounds.add(new VersionSound(0, sound.getSoundId(),
                version.getVersionId()));
    }

    @Override
    public void unselect(TutorialSound sound, Version version)
    {
        mVersionSounds.remove(new VersionSound(0, sound.getSoundId(),
                version.getVersionId()));
    }

    public interface ActivityCallback
    {
        void finalizeVersionSounds(Collection<VersionSound> versionSounds);
    }
}
