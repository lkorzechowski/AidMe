package com.orzechowski.aidme.creator.versioninstruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.version.database.Version;
import com.orzechowski.aidme.tutorial.version.database.VersionInstruction;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class VersionInstructionComposer extends Fragment
        implements VersionInstructionInnerAdapter.OnClickListener
{
    private VersionInstructionOuterAdapter mOuterAdapter;
    private final List<Version> mVersions;
    private final List<InstructionSet> mInstructions;
    private InstructionTextAdapter mInstructionTextAdapter;
    private List<VersionInstruction> mVersionInstructions = new LinkedList<>();

    public VersionInstructionComposer(List<Version> versions, List<InstructionSet> instructionSets)
    {
        mVersions = versions;
        mInstructions = instructionSets;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_version_instruction,
                container, false);
        mOuterAdapter = new VersionInstructionOuterAdapter(activity, this);
        RecyclerView outerRecycler = view.findViewById(R.id.version_instruction_outer_rv);
        outerRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        outerRecycler.setAdapter(mOuterAdapter);
        mInstructionTextAdapter = new InstructionTextAdapter(activity);
        RecyclerView instructionTextRecycler = view.findViewById(R.id.instruction_text_rv);
        instructionTextRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        instructionTextRecycler.setAdapter(mInstructionTextAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        mOuterAdapter.setElementList(mVersions, mInstructions);
        mInstructionTextAdapter.setElementList(mInstructions);
    }

    @Override
    public void callback(InstructionSet instructionSet, Version version)
    {
        mVersionInstructions.add(new VersionInstruction(0, version.getVersionId(),
                instructionSet.getPosition()));
    }
}
