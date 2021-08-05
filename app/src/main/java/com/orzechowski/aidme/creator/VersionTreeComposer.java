package com.orzechowski.aidme.creator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.LinkedList;
import java.util.List;

public class VersionTreeComposer
        extends Fragment implements VersionTreeComposerAdapter.FragmentCallback
{
    private VersionTreeComposerAdapter mLevelOneAdapter, mLevelTwoAdapter, mLevelThreeAdapter,
            mLevelFourAdapter;
    private VersionTextAdapter mVersionTextAdapter;
    private final List<Version> mAllVersions;
    private final List<Version> mLevelOneVersions;
    private final List<Version> mLevelTwoVersions = new LinkedList<>();
    private final List<Version> mLevelThreeVersions = new LinkedList<>();
    private final List<Version> mLevelFourVersions = new LinkedList<>();

    public VersionTreeComposer(List<Version> versions)
    {
        mAllVersions = versions;
        mLevelOneVersions = versions;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater
                .inflate(R.layout.fragment_version_tree_composer, container, false);
        RecyclerView versionTextRecycler = view.findViewById(R.id.version_text_rv);
        RecyclerView levelOneRecycler = view.findViewById(R.id.version_tree_first_level_rv);
        RecyclerView levelTwoRecycler = view.findViewById(R.id.version_tree_second_level_rv);
        RecyclerView levelThreeRecycler = view.findViewById(R.id.version_tree_third_level_rv);
        RecyclerView levelFourRecycler = view.findViewById(R.id.version_tree_fourth_level_rv);
        versionTextRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        levelOneRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelTwoRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelThreeRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        levelFourRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mVersionTextAdapter = new VersionTextAdapter(activity);
        mLevelOneAdapter = new VersionTreeComposerAdapter(activity, this, 1);
        mLevelTwoAdapter = new VersionTreeComposerAdapter(activity, this, 2);
        mLevelThreeAdapter = new VersionTreeComposerAdapter(activity, this, 3);
        mLevelFourAdapter = new VersionTreeComposerAdapter(activity, this, 4);
        versionTextRecycler.setAdapter(mVersionTextAdapter);
        levelOneRecycler.setAdapter(mLevelOneAdapter);
        levelTwoRecycler.setAdapter(mLevelTwoAdapter);
        levelThreeRecycler.setAdapter(mLevelThreeAdapter);
        levelFourRecycler.setAdapter(mLevelFourAdapter);
        TextView levelTwoLabel = view.findViewById(R.id.version_tree_level_two_label);
        TextView levelThreeLabel = view.findViewById(R.id.version_tree_level_three_label);
        TextView levelFourLabel = view.findViewById(R.id.version_tree_level_four_label);
        ItemTouchHelper.SimpleCallback callbackOne = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                if(direction==2) {
                    Version version = mLevelOneVersions.get(viewHolder.getLayoutPosition());
                    mLevelOneVersions.remove(version);
                    mLevelTwoVersions.add(version);
                    mLevelTwoAdapter.setElementList(mLevelTwoVersions);
                    levelTwoLabel.setVisibility(View.VISIBLE);
                }
                mLevelOneAdapter.setElementList(mLevelOneVersions);
            }
        };
        ItemTouchHelper itemTouchHelperOne = new ItemTouchHelper(callbackOne);
        itemTouchHelperOne.attachToRecyclerView(levelOneRecycler);
        ItemTouchHelper.SimpleCallback callbackTwo = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                Version version = mLevelTwoVersions.get(viewHolder.getLayoutPosition());
                mLevelTwoVersions.remove(version);
                if(direction==2) {
                    mLevelThreeVersions.add(version);
                    mLevelThreeAdapter.setElementList(mLevelThreeVersions);
                    levelThreeLabel.setVisibility(View.VISIBLE);
                } else if (direction==1) {
                    mLevelOneVersions.add(version);
                    mLevelOneAdapter.setElementList(mLevelOneVersions);
                }
                mLevelTwoAdapter.setElementList(mLevelTwoVersions);
            }
        };
        ItemTouchHelper itemTouchHelperTwo = new ItemTouchHelper(callbackTwo);
        itemTouchHelperTwo.attachToRecyclerView(levelTwoRecycler);
        ItemTouchHelper.SimpleCallback callbackThree = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                Version version = mLevelThreeVersions.get(viewHolder.getLayoutPosition());
                mLevelThreeVersions.remove(version);
                if(direction==2) {
                    mLevelFourVersions.add(version);
                    mLevelFourAdapter.setElementList(mLevelFourVersions);
                    levelFourLabel.setVisibility(View.VISIBLE);
                } else if(direction==1) {
                    mLevelTwoVersions.add(version);
                    mLevelTwoAdapter.setElementList(mLevelTwoVersions);
                }
                mLevelThreeAdapter.setElementList(mLevelThreeVersions);
            }
        };
        ItemTouchHelper itemTouchHelperThree = new ItemTouchHelper(callbackThree);
        itemTouchHelperThree.attachToRecyclerView(levelThreeRecycler);
        ItemTouchHelper.SimpleCallback callbackFour = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                if(direction==1) {
                    Version version = mLevelFourVersions.get(viewHolder.getLayoutPosition());
                    mLevelFourVersions.remove(version);
                    mLevelThreeVersions.add(version);
                    mLevelThreeAdapter.setElementList(mLevelThreeVersions);
                }
                mLevelFourAdapter.setElementList(mLevelFourVersions);
            }
        };
        ItemTouchHelper itemTouchHelperFour = new ItemTouchHelper(callbackFour);
        itemTouchHelperFour.attachToRecyclerView(levelFourRecycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        mLevelOneAdapter.setElementList(mLevelOneVersions);
        mVersionTextAdapter.setElementList(mAllVersions);
    }

    @Override
    public void giveParent(Version version, int level)
    {

    }
}
