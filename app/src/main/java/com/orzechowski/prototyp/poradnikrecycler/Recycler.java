package com.orzechowski.prototyp.poradnikrecycler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.poradnikrecycler.objects.InstructionSet;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment implements ListAdapter.OnViewClickListener {
    protected RecyclerView mRecycler;
    protected ListAdapter mAdapter;
    private final List<InstructionSet> mInstructionsList;
    private Player mPlayerInstance;
    private boolean mBoot;
    private TextView mTextDisplay;

    public Recycler() {
        super(R.layout.fragment_recycler_main);
        this.mBoot = true;
        this.mInstructionsList = new LinkedList<>();
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        this.mTextDisplay = requireActivity().findViewById(R.id.active_instructions);
        String[] titles = getArguments().getStringArray("title");
        String[] instructions = getArguments().getStringArray("instructions");
        int[] version = getArguments().getIntArray("version");
        int[] duration = getArguments().getIntArray("duration");
        for (int j : version) {
            mInstructionsList.add(new InstructionSet(titles[j], instructions[j], duration[j]));
        }
        mAdapter = new ListAdapter(requireActivity(), mInstructionsList, this);
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requireActivity().runOnUiThread(()->{
                mTextDisplay.setText(mInstructionsList.get(position).getInstructions());
            });
            try {
                sleep(mInstructionsList.get(position).getTime());
                requireActivity().runOnUiThread(()->{
                    play(position);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}