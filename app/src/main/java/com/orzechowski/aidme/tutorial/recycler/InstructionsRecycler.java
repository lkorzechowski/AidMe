package com.orzechowski.aidme.tutorial.recycler;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tools.AssetObtainer;
import com.orzechowski.aidme.tutorial.database.InstructionSet;
import com.orzechowski.aidme.tutorial.database.InstructionSetViewModel;
import com.orzechowski.aidme.tutorial.version.database.VersionInstructionViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class InstructionsRecycler extends Fragment implements InstructionsListAdapter.OnClickListener
{
    private InstructionsListAdapter mAdapter;
    private Player mPlayerInstance;
    private boolean mBoot = true;
    private TextView mTextDisplay;
    private InstructionSetViewModel mInstructionSetViewModel;
    private long mTutorialId;
    private List<Integer> mTutorialInstructions;
    private int mPlayCount = 0;
    private boolean mAutoplay = true;
    private int mSize;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState)
    {
        Bundle bundle = requireArguments();
        FragmentActivity activity = requireActivity();
        VersionInstructionViewModel versionInstructionViewModel = new ViewModelProvider(this)
                .get(VersionInstructionViewModel.class);
        versionInstructionViewModel.getByVersionId(bundle.getLong("versionId"))
                .observe(activity, instructionNumbers ->{
                    mTutorialInstructions = instructionNumbers;
                    if(mBoot){
                        play(0);
                        mBoot = false;
                    }
                });
        mTutorialId = bundle.getLong("tutorialId");
        mTextDisplay = activity.findViewById(R.id.active_instructions);
        mInstructionSetViewModel = new ViewModelProvider(this).get(InstructionSetViewModel.class);
        mAdapter = new InstructionsListAdapter(activity, this);
        mInstructionSetViewModel.getTutorialSize(mTutorialId).observe(activity, size -> mSize = size);
        mInstructionSetViewModel.getByTutorialId(mTutorialId)
                .observe(activity, instructions->mAdapter.setElementList(instructions));
        View view = inflater.inflate(R.layout.fragment_recycler_tutorial, group, false);
        RecyclerView recycler = view.findViewById(R.id.tutorial_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    private void play(int position)
    {
        if (mPlayerInstance != null) {
            mPlayerInstance.interrupt();
            position++;
        }
        if(mAutoplay) {
            int size = mTutorialInstructions.size();
            if (mPlayCount < size) {
                if (mTutorialInstructions.contains(position)) {
                    getPlayer(position);
                    mPlayCount++;
                } else if(position < mSize){
                    play(position + 1);
                }
            } else mTextDisplay.setVisibility(View.GONE);
        } else {
            if(mTutorialInstructions.contains(position)){
                mAutoplay = true;
                getPlayer(position);
                mPlayCount++;
            } else {
                getPlayer(position);
            }
        }
    }

    public void getPlayer(int position)
    {
            mInstructionSetViewModel
                .getByPositionAndTutorialId(position, mTutorialId)
                    .observe(requireActivity(), selected -> {
                    mPlayerInstance = new Player(selected.get(0));
                    mPlayerInstance.start();
                });
    }

    @Override
    public void onPause()
    {
        if(mPlayerInstance!=null){
            mPlayerInstance.interrupt();
        }
        super.onPause();
    }

    @Override
    public void onClick(InstructionSet instructionSet)
    {
        mTextDisplay.setVisibility(View.VISIBLE);
        mPlayCount = instructionSet.getPosition();
        mAutoplay = false;

        play(instructionSet.getPosition()-1);
    }

    private class Player extends Thread
    {
        private final InstructionSet set;
        private final int position;
        private final AssetObtainer assetObtainer = new AssetObtainer();

        public Player(InstructionSet instructionSet)
        {
            set = instructionSet;
            position = instructionSet.getPosition();
        }

        @Override
        public void run()
        {
            try {
                sleep(100);
            } catch (InterruptedException | IllegalStateException e) {
                e.printStackTrace();
                interrupt();
            }
            String fileName = "s" + mTutorialId + "_" + position+".m4a";
            Uri uri;
            try {
                uri = Uri.fromFile(assetObtainer.getFileFromAssets(requireContext(), fileName));
            } catch (IOException e) {
                e.printStackTrace();
                uri = null;
            }
            FragmentActivity activity = requireActivity();
            MediaPlayer mPlayer = null;
            if(uri!=null){
                mPlayer = MediaPlayer.create(getContext(), uri);
            }
            if(mPlayer!=null) {
                mPlayer.setLooping(false);
                mPlayer.setVolume(1F, 1F);
                activity.runOnUiThread(() ->
                        mTextDisplay.setText(set.getInstructions()));
                try {
                    mPlayer.start();
                    sleep(set.getTime());
                    activity.runOnUiThread(() -> {
                        if (mAutoplay) play(position);
                    });
                } catch (IllegalStateException | InterruptedException e) {
                    mPlayer.stop();
                    interrupt();
                }
            } else {
                activity.runOnUiThread(() ->
                        mTextDisplay.setText(set.getInstructions()));
                try {
                    sleep(set.getTime());
                    activity.runOnUiThread(() -> {
                        if (mAutoplay) play(position);
                    });
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }
}