package com.orzechowski.aidme.tutorial.instructions;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.database.tools.AssetObtainer;
import com.orzechowski.aidme.tutorial.database.TutorialLink;
import com.orzechowski.aidme.tutorial.database.TutorialLinkViewModel;
import com.orzechowski.aidme.tutorial.database.TutorialViewModel;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetViewModel;
import com.orzechowski.aidme.tutorial.version.database.VersionInstructionViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InstructionsRecycler
        extends Fragment implements InstructionsListAdapter.OnClickListener
{
    private InstructionsListAdapter mAdapter;
    private Player mPlayerInstance;
    private TextView mTextDisplay;
    private Button mTutorialLink;
    private InstructionSetViewModel mInstructionSetViewModel;
    private TutorialLinkViewModel mTutorialLinkViewModel;
    private TutorialViewModel mTutorialViewModel;
    private long mTutorialId;
    private List<Integer> mTutorialInstructions;
    private boolean mAutoplay = true;
    private int mSize;
    private final CallbackForTutorialLink mCallback;

    public InstructionsRecycler(CallbackForTutorialLink callbackForTutorialLink)
    {
        mCallback = callbackForTutorialLink;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup group, Bundle bundle)
    {
        bundle = requireArguments();
        FragmentActivity activity = requireActivity();
        VersionInstructionViewModel versionInstructionViewModel = new ViewModelProvider(this)
                .get(VersionInstructionViewModel.class);
        versionInstructionViewModel.getByVersionId(bundle.getLong("versionId"))
                .observe(activity, instructionNumbers -> {
                    mTutorialInstructions = instructionNumbers;
                    play(0);
                });
        mTutorialId = bundle.getLong("tutorialId");
        mTextDisplay = activity.findViewById(R.id.active_instructions);
        mTutorialLink = activity.findViewById(R.id.tutorial_link);
        mInstructionSetViewModel = new ViewModelProvider(this)
                .get(InstructionSetViewModel.class);
        mTutorialLinkViewModel = new ViewModelProvider(this)
                .get(TutorialLinkViewModel.class);
        mTutorialViewModel = new ViewModelProvider(this)
                .get(TutorialViewModel.class);
        mAdapter = new InstructionsListAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_recycler_tutorial, group, false);
        mInstructionSetViewModel.getTutorialSize(mTutorialId).observe(activity, size -> {
            mSize = size;
            mInstructionSetViewModel.getByTutorialId(mTutorialId)
                    .observe(activity, instructions->mAdapter.setElementList(instructions));
            RecyclerView recycler = view.findViewById(R.id.tutorial_rv);
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                    LinearLayoutManager.VERTICAL, false));
            recycler.setAdapter(mAdapter);
        });
        return view;
    }

    private void play(int position)
    {
        if (mPlayerInstance != null) {
            mPlayerInstance.interrupt();
            position++;
        }
        if(mAutoplay) {
            if (mTutorialInstructions.contains(position)) {
                getPlayer(position);
            } else if(position < mSize) {
                if(mPlayerInstance==null) play(position+1);
                else play(position);
            } else mTextDisplay.setVisibility(View.GONE);
        } else {
            if(mTutorialInstructions.contains(position)) {
                mAutoplay = true;
            }
            getPlayer(position);
        }
    }

    public void getPlayer(int position)
    {
        mInstructionSetViewModel.getByPositionAndTutorialId(position, mTutorialId)
                .observe(requireActivity(), selected -> {
                    if(selected != null) {
                        mPlayerInstance = new Player(selected);
                        mPlayerInstance.start();
                    } else  mTextDisplay.setVisibility(View.GONE);
                });
    }

    @Override
    public void onPause()
    {
        if(mPlayerInstance != null) {
            mPlayerInstance.interrupt();
        }
        super.onPause();
    }

    @Override
    public void onClick(InstructionSet instructionSet)
    {
        mTextDisplay.setVisibility(View.VISIBLE);
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
                interrupt();
            }
            Uri uri = null;
            String fileName = set.getNarrationFileName();
            if(fileName!=null) {
                try {
                    uri = Uri.fromFile(assetObtainer.getFileFromAssets(requireContext(), fileName));
                } catch (Exception ignored) {}
            }
            FragmentActivity activity = requireActivity();
            MediaPlayer mPlayer = null;
            if(uri!=null) {
                mPlayer = MediaPlayer.create(getContext(), uri);
            }
            if(mPlayer != null) {
                mPlayer.setLooping(false);
                mPlayer.setVolume(1F, 1F);
                activity.runOnUiThread(this::displayText);
                try {
                    mPlayer.start();
                    sleep(set.getTime());
                    activity.runOnUiThread(() -> {
                        mTutorialLink.setVisibility(View.GONE);
                        if (mAutoplay) play(position);
                    });
                } catch (IllegalStateException | InterruptedException e) {
                    mPlayer.stop();
                    mPlayer.release();
                    activity.runOnUiThread(() -> mTutorialLink.setVisibility(View.GONE));
                    interrupt();
                }
            } else {
                activity.runOnUiThread(this::displayText);
                try {
                    sleep(set.getTime());
                    activity.runOnUiThread(() -> {
                        mTutorialLink.setVisibility(View.GONE);
                        if (mAutoplay) play(position);
                    });
                } catch (InterruptedException e) {
                    interrupt();
                    activity.runOnUiThread(() -> mTutorialLink.setVisibility(View.GONE));
                }
            }
        }
        private void displayText()
        {
            mTextDisplay.setText(set.getInstructions());
            mTutorialLinkViewModel.getByOriginIdAndPosition(mTutorialId, position)
                    .observe(requireActivity(), tutorialLink-> {
                    if(tutorialLink!=null) {
                        mTutorialViewModel.getByTutorialId(tutorialLink.getTutorialId())
                                .observe(requireActivity(), tutorial -> {
                            mTutorialLink.setText(tutorial.getTutorialName());
                            mTutorialLink.setVisibility(View.VISIBLE);
                            mTutorialLink.setOnClickListener(v->
                                    mCallback.serveNewTutorial(tutorialLink));
                        });
                    }
            });
        }
    }

    public interface CallbackForTutorialLink
    {
        void serveNewTutorial(TutorialLink tutorialLink);
    }
}
