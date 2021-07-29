package com.orzechowski.aidme.creator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.version.database.Version;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class VersionTreeComposer
        extends Fragment implements VersionTreeComposerAdapter.FragmentCallback
{
    private VersionTreeComposerAdapter mUnassignedAdapter, mLevelOneAdapter, mLevelTwoAdapter,
            mLevelThreeAdapter, mLevelFourAdapter;
    private final List<Version> mUnassigned;
    private final List<Version> mLevelOneVersions = new LinkedList<>();
    private final List<Version> mLevelTwoVersions = new LinkedList<>();
    private final List<Version> mLevelThreeVersions = new LinkedList<>();
    private final List<Version> mLevelFourVersions = new LinkedList<>();

    public VersionTreeComposer(List<Version> versions)
    {
        mUnassigned = versions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater
                .inflate(R.layout.fragment_version_tree_composer, container, false);
        RecyclerView unassignedRecycler = view.findViewById(R.id.unassigned_version_rv);
        RecyclerView levelOneRecycler = view.findViewById(R.id.version_tree_first_level_rv);
        RecyclerView levelTwoRecycler = view.findViewById(R.id.version_tree_second_level_rv);
        RecyclerView levelThreeRecycler = view.findViewById(R.id.version_tree_third_level_rv);
        RecyclerView levelFourRecycler = view.findViewById(R.id.version_tree_fourth_level_rv);
        unassignedRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelOneRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelTwoRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelThreeRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelFourRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mUnassignedAdapter = new VersionTreeComposerAdapter(activity, this, 0);
        mLevelOneAdapter = new VersionTreeComposerAdapter(activity, this, 1);
        mLevelTwoAdapter = new VersionTreeComposerAdapter(activity, this, 2);
        mLevelThreeAdapter = new VersionTreeComposerAdapter(activity, this, 3);
        mLevelFourAdapter = new VersionTreeComposerAdapter(activity, this, 4);
        unassignedRecycler.setAdapter(mUnassignedAdapter);
        levelOneRecycler.setAdapter(mLevelOneAdapter);
        levelTwoRecycler.setAdapter(mLevelTwoAdapter);
        levelThreeRecycler.setAdapter(mLevelThreeAdapter);
        levelFourRecycler.setAdapter(mLevelFourAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        mUnassignedAdapter.setElementList(mUnassigned);
    }

    @Override
    public void reassign(Version version, int level)
    {

    }
}
