package com.orzechowski.prototyp.instructionsrecycler;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.media.MediaPlayer;
import android.os.Bundle;
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
    private int tutorialLength;
    private InstructionSetViewModel mInstructionSetViewModel;
    private long mTutorialId;

    public InstructionsRecycler() {
        super(R.layout.fragment_recycler_main);
        this.mBoot = true;
        this.mIdResource = "ania_";
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        mTutorialId = requireArguments().getLong("tutorialId");
        String tutorialParts = requireArguments().getString("versionTutorialParts");//TODO
        this.mTextDisplay = requireActivity().findViewById(R.id.active_instructions);

        mInstructionSetViewModel = new ViewModelProvider(this).get(InstructionSetViewModel.class);
        tutorialLength=8;//TODO
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
        if(mPlayerInstance!=null){
            mPlayerInstance.interrupt();
            position++;
        }
        if(position < tutorialLength) {
            mInstructionSetViewModel
                    .getByPositionAndTutorialId(position, mTutorialId)
                    .observe(requireActivity(), selected ->{
                        mPlayerInstance = new Player(selected.get(0));
                        mPlayerInstance.start();
                    });
        } else mTextDisplay.setVisibility(View.GONE);
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
        mTextDisplay.setVisibility(View.VISIBLE);
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
                requireActivity().runOnUiThread(()-> play(position));
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