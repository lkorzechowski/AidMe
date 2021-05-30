package com.orzechowski.prototyp.instructionsrecycler;

import androidx.fragment.app.Fragment;
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
import com.orzechowski.prototyp.versionrecycler.database.VersionViewModel;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class InstructionsRecycler extends Fragment implements InstructionsListAdapter.OnViewClickListener {

    protected RecyclerView mRecycler;
    protected InstructionsListAdapter mAdapter;
    private Player mPlayerInstance;
    private boolean mBoot;
    private TextView mTextDisplay;
    private final String mIdResource;
    private final List<InstructionSet> mInstructionsList;

    public InstructionsRecycler() {
        super(R.layout.fragment_recycler_main);
        this.mBoot = true;
        this.mIdResource = "ania_";
        this.mInstructionsList = new LinkedList<>();
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        long tutorialId = getArguments().getLong("tutorialId");
        long versionId = getArguments().getLong("versionId");

        this.mTextDisplay = requireActivity().findViewById(R.id.active_instructions);

        InstructionSetViewModel instructionSetViewModel =
                new InstructionSetViewModel(getActivity().getApplication());
        List<InstructionSet> instructionSets =
                instructionSetViewModel.getByTutorialId(tutorialId).getValue();

        VersionViewModel versionViewModel = new VersionViewModel(getActivity().getApplication());
        List<Integer> versionInstructions =
                versionViewModel.getByVersionId(versionId).getValue().get(0).getNumbers();

        for(Integer v : versionInstructions){
            mInstructionsList.add(instructionSets.get(v));
        }

        mAdapter = new InstructionsListAdapter(requireActivity(), mInstructionsList, this);
        View view = inflater.inflate(R.layout.fragment_recycler_poradnik, container, false);
        mRecycler = view.findViewById(R.id.poradniki_rv);
        mRecycler.setAdapter(mAdapter);
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
        return view;
    }

    private void play(int position){
        if(mPlayerInstance!=null){
            mPlayerInstance.interrupt();
            position++;
        }
        if(position < mInstructionsList.size()) {
            mPlayerInstance = new Player(position);
            mPlayerInstance.start();
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
    public void onViewClick(int position) {
        mTextDisplay.setVisibility(View.VISIBLE);
        play(position-1);
    }

    private class Player extends Thread{
        private final int position;

        public Player(int position){
            this.position = position;
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
                    mTextDisplay.setText(mInstructionsList.get(position).getInstructions()));
            try {
                mPlayer.start();
                sleep(mInstructionsList.get(position).getTime());
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