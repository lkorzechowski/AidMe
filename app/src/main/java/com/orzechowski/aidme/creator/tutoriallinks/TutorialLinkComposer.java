package com.orzechowski.aidme.creator.tutoriallinks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialLink;
import com.orzechowski.aidme.tutorial.database.TutorialViewModel;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;

import java.util.LinkedList;
import java.util.List;

public class TutorialLinkComposer extends Fragment implements
        TutorialLinkComposerAdapter.FragmentCallback,
        TutorialLinkTutorialListAdapter.FragmentCallback,
        TutorialLinkInstructionAdapter.FragmentCallback
{
    private TutorialLinkComposerAdapter mLinkAdapter;
    private TutorialLinkTutorialListAdapter mTutorialAdapter;
    private final ActivityCallback mCallback;
    private RecyclerView mTutorialRecycler, mInstructionRecycler;
    public ConstraintLayout mPrimaryLayout;
    private ConstraintLayout mEditingLayout;
    private final List<TutorialLink> mTutorialLinks = new LinkedList<>();
    private List<InstructionSet> mInstructions;
    private boolean mEditing = false;
    private int mEditingIndex;
    private long mSelectedTutorialId;
    private TextView mHeading;

    public TutorialLinkComposer(ActivityCallback callback)
    {
        mCallback = callback;
    }

    public void setInstructions(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        return inflater
                .inflate(R.layout.fragment_tutorial_link_composer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        FragmentActivity activity = requireActivity();
        RecyclerView linkRecycler = view.findViewById(R.id.link_rv);
        Context context = view.getContext();
        mHeading = view.findViewById(R.id.tutorial_link_composer_heading);
        mPrimaryLayout = view.findViewById(R.id.tutorial_link_primary_layout);
        mEditingLayout = view.findViewById(R.id.tutorial_link_edit_layout);
        linkRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL
                ,false));
        mTutorialRecycler = view.findViewById(R.id.tutorials_rv);
        mTutorialRecycler.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        mInstructionRecycler = view.findViewById(R.id.instructions_rv);
        mInstructionRecycler.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        TutorialLinkInstructionAdapter instructionAdapter =
                new TutorialLinkInstructionAdapter(activity, this);
        mInstructionRecycler.setAdapter(instructionAdapter);
        instructionAdapter.setElementList(mInstructions);
        TutorialViewModel tutorialViewModel = new ViewModelProvider(this)
                .get(TutorialViewModel.class);
        tutorialViewModel.getAll().observe(activity, tutorials -> {
            mLinkAdapter = new TutorialLinkComposerAdapter(activity, this, tutorials);
            mTutorialAdapter = new TutorialLinkTutorialListAdapter(activity, this);
            mTutorialRecycler.setAdapter(mTutorialAdapter);
            linkRecycler.setAdapter(mLinkAdapter);
            mTutorialAdapter.setElementList(tutorials);
            Button addLink = view.findViewById(R.id.add_tutorial_link_button);
            addLink.setOnClickListener(v -> {
                mPrimaryLayout.setVisibility(View.INVISIBLE);
                mTutorialRecycler.setVisibility(View.VISIBLE);
            });
            Button finalizeLinks = view.findViewById(R.id.tutorial_links_finalize_button);
            finalizeLinks.setOnClickListener(v -> mCallback.finalizeTutorialLinks(mTutorialLinks));
            Button editTutorial = view.findViewById(R.id.tutorial_link_edit_tutorial);
            editTutorial.setOnClickListener(v -> {
                mEditing = true;
                mEditingLayout.setVisibility(View.INVISIBLE);
                mTutorialRecycler.setVisibility(View.VISIBLE);
                mHeading.setText(R.string.tutorial_link_heading_tutorials);
            });
            Button editInstruction = view.findViewById(R.id.tutorial_link_edit_instruction);
            editInstruction.setOnClickListener(v -> {
                mEditing = true;
                mEditingLayout.setVisibility(View.INVISIBLE);
                mInstructionRecycler.setVisibility(View.VISIBLE);
                mHeading.setText(R.string.tutorial_link_heading_instructions);
            });
        });
    }

    @Override
    public void edit(TutorialLink tutorialLink)
    {
        mPrimaryLayout.setVisibility(View.INVISIBLE);
        mHeading.setText(R.string.link_edit);
        mEditingIndex = mTutorialLinks.indexOf(tutorialLink);
        mEditingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void delete(TutorialLink tutorialLink)
    {
        mTutorialLinks.remove(tutorialLink);
        mLinkAdapter.setElementList(mTutorialLinks);
    }

    @Override
    public void selectTutorial(Tutorial tutorial)
    {
        mTutorialRecycler.setVisibility(View.INVISIBLE);
        if(!mEditing) {
            mInstructionRecycler.setVisibility(View.VISIBLE);
            mHeading.setText(R.string.tutorial_link_heading_instructions);
            mSelectedTutorialId = tutorial.getTutorialId();
        } else {
            mTutorialLinks.set(mEditingIndex, new TutorialLink(0,
                    tutorial.getTutorialId(), 0,
                    mTutorialLinks.get(mEditingIndex).getInstructionNumber()));
            mPrimaryLayout.setVisibility(View.VISIBLE);
            mHeading.setText(R.string.tutorial_link_heading_primary);
            mLinkAdapter.setElementList(mTutorialLinks);
            mEditing = false;
        }
    }

    @Override
    public void selectInstruction(InstructionSet instructionSet)
    {
        mInstructionRecycler.setVisibility(View.INVISIBLE);
        mPrimaryLayout.setVisibility(View.VISIBLE);
        mHeading.setText(R.string.tutorial_link_heading_primary);
        if(!mEditing) {
            mTutorialLinks.add(new TutorialLink(0, mSelectedTutorialId, 0,
                    instructionSet.getPosition()));
        } else {
            mTutorialLinks.set(mEditingIndex, new TutorialLink(0,
                    mTutorialLinks.get(mEditingIndex).getTutorialId(), 0,
                    instructionSet.getPosition()));
            mEditing = false;
        }
        mLinkAdapter.setElementList(mTutorialLinks);
    }

    public void back()
    {
        mPrimaryLayout.setVisibility(View.VISIBLE);
        mEditingLayout.setVisibility(View.INVISIBLE);
        mInstructionRecycler.setVisibility(View.INVISIBLE);
        mTutorialRecycler.setVisibility(View.INVISIBLE);
        mEditing = false;
    }

    public interface ActivityCallback
    {
        void finalizeTutorialLinks(List<TutorialLink> tutorialLinks);
    }
}
