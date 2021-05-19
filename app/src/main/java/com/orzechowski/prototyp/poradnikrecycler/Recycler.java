package com.orzechowski.prototyp.poradnikrecycler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.poradnikrecycler.objects.Instrukcja;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment implements ListAdapter.OnViewClickListener {
    protected RecyclerView mRecycler;
    protected ListAdapter mAdapter;
    private final List<Instrukcja> mInstructionsList;
    private Scroller mScrollerInstance;
    private boolean mBoot;

    public Recycler() {
        super(R.layout.fragment_recycler_main);
        this.mBoot = true;
        this.mInstructionsList = new LinkedList<>();
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        String[] titles = getArguments().getStringArray("title");
        String[] instructions = getArguments().getStringArray("instructions");
        int[] version = getArguments().getIntArray("version");
        int[] duration = getArguments().getIntArray("duration");
        for (int i = 0; i < version.length; i++) {
            mInstructionsList.add(new Instrukcja(titles[version[i]], instructions[version[i]], duration[i]));
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
                    scroll(0);
                    mBoot = false;
                }
            }
        });
        return view;
    }

    private void scroll(int position){
        if(mScrollerInstance!=null){
            mScrollerInstance.interrupt();
            position++;
        }
        if(position < mInstructionsList.size()) {
            mScrollerInstance = new Scroller(position);
            mScrollerInstance.start();
        }
    }

    @Override
    public void onViewClick(int position) {
        scroll(position-1);
    }

    private class Scroller extends Thread{
        private final int position;

        public Scroller(int position){
            this.position = position;
        }

        @Override
        public void run(){
            requireActivity().runOnUiThread(()->{
                mRecycler.scrollToPosition(position);
                mRecycler.scrollBy(0, mRecycler.getHeight()/mAdapter.getItemCount()*3/2);
                mAdapter.expandAt(position);
            });
            try {
                sleep(mInstructionsList.get(position).getTime());
                requireActivity().runOnUiThread(() -> scroll(position));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            requireActivity().runOnUiThread(() -> mAdapter.hideAt(position));
        }
    }
}