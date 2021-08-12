package com.orzechowski.aidme.creator.versionmultimedia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.creator.VersionTextAdapter;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.VersionMultimedia;
import com.orzechowski.aidme.tutorial.version.database.Version;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class VersionMultimediaComposer extends Fragment
    implements VersionMultimediaOuterAdapter.CallbackToFragment
{
    private VersionMultimediaOuterAdapter mOuterAdapter;
    private final List<Version> mVersions;
    private final List<Multimedia> mMultimedia;
    private VersionTextAdapter mVersionTextAdapter;
    private final Collection<VersionMultimedia> mVersionMultimedias = new LinkedList<>();
    private final CallbackToActivity mCallback;

    public VersionMultimediaComposer(List<Version> versions, List<Multimedia> multimedia,
                                     CallbackToActivity callback)
    {
        mVersions = versions;
        mMultimedia = multimedia;
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_version_multimedia,
                container, false);
        Button finalizeMultimedia = view.findViewById(R.id.version_multimedia_finalize_button);
        finalizeMultimedia.setOnClickListener(v -> {
            if(mVersionMultimedias.isEmpty()) {

            } else {
                mCallback.finalizeVersionMultimedia(mVersionMultimedias);
            }
        });
        mOuterAdapter = new VersionMultimediaOuterAdapter(activity, this);
        RecyclerView outerRecycler = view.findViewById(R.id.version_multimedia_outer_rv);
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
        mOuterAdapter.setElementList(mMultimedia, mVersions);
        mVersionTextAdapter.setElementList(mVersions);
    }

    @Override
    public void select(Multimedia multimedia, Version version)
    {
        mVersionMultimedias.add(new VersionMultimedia(0,
                multimedia.getMultimediaId(), version.getVersionId()));
    }

    @Override
    public void unselect(Multimedia multimedia, Version version)
    {
        mVersionMultimedias.remove(new VersionMultimedia(0,
                multimedia.getMultimediaId(), version.getVersionId()));
    }

    public interface CallbackToActivity
    {
        void finalizeVersionMultimedia(Collection<VersionMultimedia> versionMultimedia);
    }
}
