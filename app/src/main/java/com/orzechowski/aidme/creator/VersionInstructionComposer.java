package com.orzechowski.aidme.creator;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VersionInstructionComposer extends Fragment
{
    private VersionInstructionOuterAdapter mAdapter;
    private final List<Version> mVersions;
    private final List<InstructionSet> mInstructions;

    public VersionInstructionComposer(List<Version> versions, List<InstructionSet> instructionSets)
    {
        mVersions = versions;
        mInstructions = instructionSets;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new VersionInstructionOuterAdapter(activity);
        View view = inflater.inflate(R.layout.fragment_version_instruction,
                container, false);
        RecyclerView recycler = view.findViewById(R.id.version_instruction_outer_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {

    }
}
