package com.orzechowski.aidme.creator.versioninstruction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class VersionInstructionComposer extends Fragment
    implements VersionInstructionOuterAdapter.FragmentCallback
{
    private VersionInstructionOuterAdapter mOuterAdapter;
    private final List<Version> mVersions = new LinkedList<>();
    private final List<InstructionSet> mInstructions;
    private InstructionTextAdapter mInstructionTextAdapter;
    private final Collection<VersionInstruction> mVersionInstructions = new LinkedList<>();
    private final ActivityCallback mCallback;

    public VersionInstructionComposer(Iterable<Version> versions,
                                      List<InstructionSet> instructionSets,
                                      ActivityCallback callback)
    {
        for(Version version : versions) {
            if(!version.getHasChildren()) mVersions.add(version);
        }
        mInstructions = instructionSets;
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        if(mVersions.size()==1) {
            for(InstructionSet set: mInstructions) {
                mVersionInstructions.add(new VersionInstruction(0, 0,
                        set.getPosition()));
            }
            mCallback.finalizeVersionInstructions(mVersionInstructions);
        }
        FragmentActivity activity = requireActivity();
        View view = inflater.inflate(R.layout.fragment_version_instruction,
                container, false);
        Button finalizeInstructions = view.findViewById(R.id.version_instruction_finalize_button);
        finalizeInstructions.setOnClickListener(v -> {
            if(mVersionInstructions.isEmpty()) {
                Toast.makeText(activity, R.string.version_instructions_not_assigned,
                        Toast.LENGTH_SHORT).show();
            } else {
                mCallback.finalizeVersionInstructions(mVersionInstructions);
            }
        });
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
    public void select(InstructionSet instructionSet, Version version)
    {
        mVersionInstructions.add(new VersionInstruction(0, version.getVersionId(),
                instructionSet.getPosition()));
    }

    @Override
    public void unselect(InstructionSet instructionSet, Version version)
    {
        mVersionInstructions.remove(new VersionInstruction(0, version.getVersionId(),
                instructionSet.getPosition()));
    }

    public interface ActivityCallback
    {
        void finalizeVersionInstructions(Collection<VersionInstruction> versionInstructions);
    }
}
