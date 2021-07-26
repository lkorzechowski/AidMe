package com.orzechowski.aidme.creator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class InstructionComposer extends Fragment
{
    private InstructionComposerAdapter mAdapter;
    private final List<InstructionSet> instructionList = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new InstructionComposerAdapter(activity);
        View view = inflater.inflate(R.layout.fragment_instruction_composer, container, false);
        RecyclerView recycler = view.findViewById(R.id.new_instruction_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addInstructionButton = view.findViewById(R.id.new_instruction_button);
        addInstructionButton.setOnClickListener(v-> {
            instructionList.add(new InstructionSet(0, "", "", 0, 0, instructionList.size(), ""));
            mAdapter.setElementList(instructionList);
        });
    }
}
