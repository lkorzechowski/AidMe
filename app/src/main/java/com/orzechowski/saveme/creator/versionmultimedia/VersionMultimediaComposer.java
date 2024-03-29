package com.orzechowski.saveme.creator.versionmultimedia;

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
import com.orzechowski.saveme.creator.versiontext.VersionTextAdapter;
import com.orzechowski.saveme.tutorial.multimedia.database.Multimedia;
import com.orzechowski.saveme.tutorial.multimedia.database.VersionMultimedia;
import com.orzechowski.saveme.tutorial.version.database.Version;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class VersionMultimediaComposer extends Fragment
    implements VersionMultimediaOuterAdapter.FragmentCallback
{
    private VersionMultimediaOuterAdapter mOuterAdapter;
    private final List<Version> mVersions;
    private final List<Multimedia> mMultimedia;
    private VersionTextAdapter mVersionTextAdapter;
    private final List<VersionMultimedia> mVersionMultimedia = new LinkedList<>();
    private final ActivityCallback mCallback;

    public VersionMultimediaComposer(List<Version> versions, List<Multimedia> multimedia,
                                     ActivityCallback callback)
    {
        mVersions = versions;
        mMultimedia = multimedia;
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_version_multimedia, container,
                false);
        view.findViewById(R.id.version_multimedia_finalize_button).setOnClickListener(v -> {
            if(mVersionMultimedia.isEmpty()) {
                Toast.makeText(activity, R.string.version_multimedia_not_assigned,
                        Toast.LENGTH_SHORT).show();
            } else {
                mCallback.finalizeVersionMultimedia(mVersionMultimedia);
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
        mVersionMultimedia.add(new VersionMultimedia(0,
                multimedia.getMultimediaId(), version.getVersionId()));
    }

    @Override
    public void unselect(Multimedia multimedia, Version version)
    {
        mVersionMultimedia.remove(new VersionMultimedia(0,
                multimedia.getMultimediaId(), version.getVersionId()));
    }

    public interface ActivityCallback
    {
        void finalizeVersionMultimedia(List<VersionMultimedia> versionMultimedia);
    }
}
