package com.orzechowski.prototyp.instructionsrecycler;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.instructionsrecycler.database.InstructionSet;
import com.orzechowski.prototyp.instructionsrecycler.database.InstructionSetViewModel;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;

public class InstructionsRecycler extends Fragment implements InstructionsListAdapter.OnClickListener {

    protected RecyclerView mRecycler;
    protected InstructionsListAdapter mAdapter;
    private Player mPlayerInstance;
    private boolean mBoot;
    private TextView mTextDisplay;
    private final String mIdResource;
    private int mTutorialLength;
    private InstructionSetViewModel mInstructionSetViewModel;
    private long mTutorialId;
    private String mTutorialParts;
    private int mPlayCount;
    private boolean mAutoplay;

    public InstructionsRecycler() {
        super(R.layout.fragment_recycler_main);
        this.mBoot = true;
        this.mIdResource = "ania_";
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        this.mTutorialId = requireArguments().getLong("tutorialId");
        this.mTutorialParts = requireArguments().getString("versionTutorialParts");
        this.mTextDisplay = requireActivity().findViewById(R.id.active_instructions);
        this.mPlayCount = 0;
        this.mInstructionSetViewModel = new ViewModelProvider(this).get(InstructionSetViewModel.class);
        this.mAutoplay = true;
        this.mTutorialLength= mTutorialParts.length();


        Log.w("THE LENGTH IS: ", String.valueOf(mTutorialLength));
        mInstructionSetViewModel.getByTutorialId(mTutorialId)
                .observe(requireActivity(), instructions->mAdapter.setElementList(instructions));
        mAdapter = new InstructionsListAdapter(requireActivity(), this);
        View view = inflater.inflate(R.layout.fragment_recycler_tutorial, container, false);
        mRecycler = view.findViewById(R.id.tutorial_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false){
            @Override
            public void onLayoutCompleted(RecyclerView.State state) {
                super.onLayoutCompleted(state);
                if(mBoot){
                    play(0);
                    mBoot = false;
                }
            }
        });
        mRecycler.setAdapter(mAdapter);
        return view;
    }

    private void play(int position){
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

    public void getPlayer(int position){
            mInstructionSetViewModel
                .getByPositionAndTutorialId(position, mTutorialId)
                    .observe(requireActivity(), selected -> {
                    mPlayerInstance = new Player(selected.get(0));
                    mPlayerInstance.start();
                });
    }

    @Override
    public void onPause() {
        if(mPlayerInstance!=null){
            mPlayerInstance.interrupt();
        }
        super.onPause();
    }

    @Override
    public void onClick(InstructionSet instructionSet) {
        this.mTextDisplay.setVisibility(View.VISIBLE);
        this.mPlayCount = instructionSet.getPosition()-1;
        this.mAutoplay = false;

        play(instructionSet.getPosition()-1);
    }

    private class Player extends Thread{
        private final InstructionSet instructionSet;
        private final int position;

        public Player(InstructionSet instructionSet){
            this.instructionSet = instructionSet;
            this.position = instructionSet.getPosition();
        }

        @Override
        public void run(){
            try {
                sleep(100);
            } catch (InterruptedException | IllegalStateException e) {
                e.printStackTrace();
                interrupt();
            }
            String idFinal = mIdResource + position;
            MediaPlayer mPlayer = MediaPlayer.create(getContext(), getResId(idFinal, R.raw.class));
            mPlayer.setLooping(false);
            mPlayer.setVolume(1F, 1F);
            requireActivity().runOnUiThread(()->
                    mTextDisplay.setText(instructionSet.getInstructions()));
            try {
                mPlayer.start();
                sleep(instructionSet.getTime());
                requireActivity().runOnUiThread(()->{
                    if(mAutoplay) play(position);
                });
            } catch (IllegalStateException | InterruptedException e) {
                mPlayer.stop();
                interrupt();
            }
        }
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}