package com.orzechowski.saveme.creator.initial.instructioncomposer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class InstructionComposer extends Fragment
        implements InstructionComposerAdapter.FragmentCallback
{
    private InstructionComposerAdapter mAdapter;
    private final List<InstructionSet> mInstructions = new LinkedList<>();
    private final ActivityCallback mCallback;
    private int mCurrentPosition;

    public InstructionComposer(ActivityCallback callback)
    {
        mCallback = callback;
    }

    public int getCurrentPosition()
    {
        return mCurrentPosition;
    }

    public void resetAdapterElements()
    {
        mAdapter.setElementList(mInstructions);
    }

    public List<InstructionSet> getInstructions()
    {
        return mInstructions;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new InstructionComposerAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_instruction_composer, container,
                false);
        RecyclerView recycler = view.findViewById(R.id.new_instruction_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        if(!mInstructions.isEmpty()) mAdapter.setElementList(mInstructions);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addInstructionButton = view.findViewById(R.id.new_instruction_button);
        addInstructionButton.setOnClickListener(v-> {
            int size = mInstructions.size();
            mInstructions.add(new InstructionSet(size, "", "", 0, 0,
                    size, ""));
            mAdapter.setElementList(mInstructions);
        });
    }

    @Override
    public void delete(InstructionSet instructionSet)
    {
        mInstructions.remove(instructionSet);
        for(int i = instructionSet.getPosition(); i < mInstructions.size(); i++) {
            mInstructions.get(i).setPosition(i);
        }
        mAdapter.setElementList(mInstructions);
    }

    @Override
    public void modifyTitle(String title, int position)
    {
        mInstructions.get(position).setTitle(title);
    }

    @Override
    public void modifyContent(String content, int position)
    {
        mInstructions.get(position).setInstructions(content);
    }

    @Override
    public void modifyDisplayTime(int time, int position)
    {
        mInstructions.get(position).setTime(time);
    }

    @Override
    public void addNarration(int position)
    {
        mCurrentPosition = position;
        mCallback.callNarrationRecycler();
    }

    public interface ActivityCallback
    {
        void callNarrationRecycler();
    }
}
