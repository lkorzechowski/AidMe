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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InstructionsRecycler extends Fragment
        implements InstructionsListAdapter.OnClickListener
{
    private InstructionsListAdapter mAdapter;
    private Player mPlayerInstance;
    private boolean mBoot;
    private TextView mTextDisplay;
    private int mTutorialLength;
    private InstructionSetViewModel mInstructionSetViewModel;
    private long mTutorialId;
    private String mTutorialParts;
    private int mPlayCount = 0;
    private boolean mAutoplay = true;

    public InstructionsRecycler()
    {
        mBoot = true;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = requireArguments();
        mTutorialId = bundle.getLong("tutorialId");
        mTutorialParts = bundle.getString("versionTutorialParts");
        FragmentActivity activity = requireActivity();
        mTextDisplay = activity.findViewById(R.id.active_instructions);
        mInstructionSetViewModel = new ViewModelProvider(this).get(InstructionSetViewModel.class);
        mTutorialLength = mTutorialParts.length();
        mAdapter = new InstructionsListAdapter(activity, this);
        mInstructionSetViewModel.getByTutorialId(mTutorialId)
                .observe(activity, instructions->mAdapter.setElementList(instructions));
        View view = inflater.inflate(R.layout.fragment_recycler_tutorial, container, false);
        RecyclerView recycler = view.findViewById(R.id.tutorial_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false)
        {
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                if(mBoot){
                    play(0);
                    mBoot = false;
                }
            }
        });
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
            if (mPlayCount < mTutorialLength) {
                if (mTutorialParts.contains(String.valueOf(position))) {
                    getPlayer(position);
                    mPlayCount++;
                } else {
                    play(position + 1);
                }
            } else mTextDisplay.setVisibility(View.GONE);
        } else {
            if(mTutorialParts.contains(String.valueOf(position))){
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
        private final InstructionSet instructionSet;
        private final int position;
        private final AssetObtainer assetObtainer = new AssetObtainer();

        public Player(InstructionSet instructionSet)
        {
            this.instructionSet = instructionSet;
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
            Uri uri = null;
            try {
                uri = Uri.fromFile(assetObtainer.getFileFromAssets(requireContext(), fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            FragmentActivity activity = requireActivity();
            if(uri!=null) {
                MediaPlayer mPlayer = MediaPlayer.create(getContext(), uri);
                mPlayer.setLooping(false);
                mPlayer.setVolume(1F, 1F);
                activity.runOnUiThread(() ->
                        mTextDisplay.setText(instructionSet.getInstructions()));
                try {
                    mPlayer.start();
                    sleep(instructionSet.getTime());
                    activity.runOnUiThread(() -> {
                        if (mAutoplay) play(position);
                    });
                } catch (IllegalStateException | InterruptedException e) {
                    mPlayer.stop();
                    interrupt();
                }
            } else {
                activity.runOnUiThread(() ->
                        mTextDisplay.setText(instructionSet.getInstructions()));
                try {
                    sleep(instructionSet.getTime());
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