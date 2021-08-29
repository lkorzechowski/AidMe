package com.orzechowski.aidme.creator.versiontree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.creator.initial.VersionTextAdapter;
import com.orzechowski.aidme.tutorial.version.database.Version;

import java.util.LinkedList;
import java.util.List;

public class VersionTreeComposer extends Fragment
        implements VersionTreeComposerAdapter.FragmentCallback
{
    private VersionTreeComposerAdapter mLevelOneAdapter, mLevelTwoAdapter, mLevelThreeAdapter,
            mLevelFourAdapter, mParentPickingAdapter;
    private VersionTextAdapter mVersionTextAdapter;
    private final List<Version> mAllVersions;
    private final List<Version> mLevelOneVersions;
    private final List<Version> mLevelTwoVersions = new LinkedList<>();
    private final List<Version> mLevelThreeVersions = new LinkedList<>();
    private final List<Version> mLevelFourVersions = new LinkedList<>();
    private Version mCurrentlyMovedVersion = null;
    private int mMovedFromLevel;
    private ConstraintLayout mPrimaryLayout, mSecondaryLayout;
    private final ActivityCallback mCallback;

    public VersionTreeComposer(List<Version> versions, ActivityCallback callback)
    {
        mAllVersions = versions;
        mLevelOneVersions = versions;
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        if(mAllVersions.size()==1) {
            mCallback.finalizeVersionTree(mAllVersions);
        }
        FragmentActivity activity = requireActivity();
        View view = inflater
                .inflate(R.layout.fragment_version_tree_composer, container, false);
        Button cancelAddingParent = view.findViewById(R.id.parent_assignment_cancel);
        cancelAddingParent.setOnClickListener(v -> {
            if(mMovedFromLevel==1) {
                mLevelTwoVersions.remove(mCurrentlyMovedVersion);
                mLevelOneVersions.add(mCurrentlyMovedVersion);
            } else if(mMovedFromLevel==2) {
                mLevelThreeVersions.remove(mCurrentlyMovedVersion);
                mLevelTwoVersions.add(mCurrentlyMovedVersion);
            } else if(mMovedFromLevel==3) {
                mLevelFourVersions.remove(mCurrentlyMovedVersion);
                mLevelTwoVersions.add(mCurrentlyMovedVersion);
            }
            mSecondaryLayout.setVisibility(View.GONE);
            mPrimaryLayout.setVisibility(View.VISIBLE);
        });
        Button moveFurtherDown = view.findViewById(R.id.parent_assignment_move_further_down);
        moveFurtherDown.setOnClickListener(v -> {
            if(mMovedFromLevel==1 && mLevelTwoVersions.size()>1) {
                mLevelTwoVersions.remove(mCurrentlyMovedVersion);
                mLevelThreeVersions.add(mCurrentlyMovedVersion);
                pickParent(2);
                mLevelThreeAdapter.setElementList(mLevelThreeVersions);
            } else if(mMovedFromLevel==2 && mLevelThreeVersions.size()>1) {
                mLevelThreeVersions.remove(mCurrentlyMovedVersion);
                mLevelFourVersions.add(mCurrentlyMovedVersion);
                pickParent(3);
                mLevelFourAdapter.setElementList(mLevelFourVersions);
            }
        });
        Button versionTreeFinalizeButton = view.findViewById(R.id.version_tree_finalize_button);
        versionTreeFinalizeButton.setOnClickListener(v -> {
            mLevelOneVersions.addAll(mLevelTwoVersions);
            mLevelOneVersions.addAll(mLevelThreeVersions);
            mLevelOneVersions.addAll(mLevelFourVersions);
            mCallback.finalizeVersionTree(mLevelOneVersions);
        });
        mPrimaryLayout = view.findViewById(R.id.version_tree_primary_layout);
        mSecondaryLayout = view.findViewById(R.id.version_tree_secondary_layout);
        RecyclerView versionTextRecycler = view.findViewById(R.id.version_text_rv);
        RecyclerView levelOneRecycler = view.findViewById(R.id.version_tree_first_level_rv);
        RecyclerView levelTwoRecycler = view.findViewById(R.id.version_tree_second_level_rv);
        RecyclerView levelThreeRecycler = view.findViewById(R.id.version_tree_third_level_rv);
        RecyclerView levelFourRecycler = view.findViewById(R.id.version_tree_fourth_level_rv);
        RecyclerView parentPickingRecycler = view.findViewById(R.id.parent_assignment_rv);
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
        parentPickingRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        mVersionTextAdapter = new VersionTextAdapter(activity);
        mLevelOneAdapter = new VersionTreeComposerAdapter(activity, 1, null);
        mLevelTwoAdapter = new VersionTreeComposerAdapter(activity, 2, null);
        mLevelThreeAdapter = new VersionTreeComposerAdapter(activity, 3, null);
        mLevelFourAdapter = new VersionTreeComposerAdapter(activity, 4, null);
        mParentPickingAdapter = new VersionTreeComposerAdapter(activity, 0, this);
        versionTextRecycler.setAdapter(mVersionTextAdapter);
        levelOneRecycler.setAdapter(mLevelOneAdapter);
        levelTwoRecycler.setAdapter(mLevelTwoAdapter);
        levelThreeRecycler.setAdapter(mLevelThreeAdapter);
        levelFourRecycler.setAdapter(mLevelFourAdapter);
        parentPickingRecycler.setAdapter(mParentPickingAdapter);
        Toast hasChildren = Toast.makeText(getContext(), "Ta wersja ma już wersje potomne",
                Toast.LENGTH_SHORT);
        ItemTouchHelper itemTouchHelperOne = new ItemTouchHelper(new ItemTouchHelper
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
                if (direction==2 && mLevelOneVersions.size()>1) {
                    mCurrentlyMovedVersion = mLevelOneVersions.get(viewHolder.getLayoutPosition());
                    if(!mCurrentlyMovedVersion.getHasChildren()) {
                        mLevelOneVersions.remove(mCurrentlyMovedVersion);
                        mLevelTwoVersions.add(mCurrentlyMovedVersion);
                        mLevelTwoAdapter.setElementList(mLevelTwoVersions);
                        pickParent(1);
                    } else {
                        hasChildren.show();
                    }
                }
                mLevelOneAdapter.setElementList(mLevelOneVersions);
            }
        });
        itemTouchHelperOne.attachToRecyclerView(levelOneRecycler);
        ItemTouchHelper itemTouchHelperTwo = new ItemTouchHelper(new ItemTouchHelper
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
                mCurrentlyMovedVersion = mLevelTwoVersions.get(viewHolder.getLayoutPosition());
                if(!mCurrentlyMovedVersion.getHasChildren()) {
                    if (direction == 2 && mLevelTwoVersions.size() > 1) {
                        mLevelTwoVersions.remove(mCurrentlyMovedVersion);
                        mLevelThreeVersions.add(mCurrentlyMovedVersion);
                        mLevelThreeAdapter.setElementList(mLevelThreeVersions);
                        pickParent(2);
                    } else if (direction == 1) {
                        mLevelTwoVersions.remove(mCurrentlyMovedVersion);
                        mCurrentlyMovedVersion.setParentVersionId(null);
                        mCurrentlyMovedVersion.setHasParent(false);
                        mLevelOneVersions.add(mCurrentlyMovedVersion);
                        mLevelOneAdapter.setElementList(mLevelOneVersions);
                    }
                } else {
                    hasChildren.show();
                }
                mLevelTwoAdapter.setElementList(mLevelTwoVersions);
            }
        });
        itemTouchHelperTwo.attachToRecyclerView(levelTwoRecycler);
        ItemTouchHelper itemTouchHelperThree = new ItemTouchHelper(new ItemTouchHelper
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
                mCurrentlyMovedVersion = mLevelThreeVersions.get(viewHolder.getLayoutPosition());
                if(!mCurrentlyMovedVersion.getHasChildren()) {
                    if (direction == 2 && mLevelThreeVersions.size() > 1) {
                        mLevelThreeVersions.remove(mCurrentlyMovedVersion);
                        mLevelFourVersions.add(mCurrentlyMovedVersion);
                        mLevelFourAdapter.setElementList(mLevelFourVersions);
                        pickParent(3);
                    } else if (direction == 1) {
                        mLevelThreeVersions.remove(mCurrentlyMovedVersion);
                        mLevelTwoVersions.add(mCurrentlyMovedVersion);
                        mLevelTwoAdapter.setElementList(mLevelTwoVersions);
                        pickParent(1);
                    }
                } else {
                    hasChildren.show();
                }
                mLevelThreeAdapter.setElementList(mLevelThreeVersions);
            }
        });
        itemTouchHelperThree.attachToRecyclerView(levelThreeRecycler);
        ItemTouchHelper itemTouchHelperFour = new ItemTouchHelper(new ItemTouchHelper
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
                if (direction == 1) {
                    Version version = mLevelFourVersions.get(viewHolder.getLayoutPosition());
                    mLevelFourVersions.remove(version);
                    mLevelThreeVersions.add(version);
                    mLevelThreeAdapter.setElementList(mLevelThreeVersions);
                    pickParent(2);
                }
                mLevelFourAdapter.setElementList(mLevelFourVersions);
            }
        });
        itemTouchHelperFour.attachToRecyclerView(levelFourRecycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        mLevelOneAdapter.setElementList(mLevelOneVersions);
        mVersionTextAdapter.setElementList(mAllVersions);
    }

    public void pickParent(int level)
    {
        mPrimaryLayout.setVisibility(View.GONE);
        mSecondaryLayout.setVisibility(View.VISIBLE);
        if(level==1) {
            mParentPickingAdapter.setElementList(mLevelOneVersions);
            mMovedFromLevel = 1;
        } else if(level==2) {
            mParentPickingAdapter.setElementList(mLevelTwoVersions);
            mMovedFromLevel = 2;
        } else {
            mParentPickingAdapter.setElementList(mLevelThreeVersions);
            mMovedFromLevel = 3;
        }
    }

    @Override
    public void callback(Version version)
    {
        int index;
        mSecondaryLayout.setVisibility(View.GONE);
        mPrimaryLayout.setVisibility(View.VISIBLE);
        version.setHasChildren(true);
        if(mMovedFromLevel==1) {
            index = mLevelTwoVersions.size()-1;
            mLevelTwoVersions.get(index).setHasParent(true);
            mLevelTwoVersions.get(index).setParentVersionId(version.getVersionId());
        } else if(mMovedFromLevel==2) {
            index = mLevelThreeVersions.size()-1;
            mLevelThreeVersions.get(index).setHasParent(true);
            mLevelThreeVersions.get(index).setParentVersionId(version.getVersionId());
        } else {
            index = mLevelFourVersions.size()-1;
            mLevelFourVersions.get(index).setHasParent(true);
            mLevelFourVersions.get(index).setParentVersionId(version.getVersionId());
        }
    }

    public interface ActivityCallback
    {
        void finalizeVersionTree(List<Version> versions);
    }
}
