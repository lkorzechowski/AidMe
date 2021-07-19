package com.orzechowski.aidme.browser.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.results.ResultsListAdapter;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Search extends Fragment implements ResultsListAdapter.OnClickListener
{
    private ResultsListAdapter mAdapter;
    public CallbackForTutorial mCallback;
    private InstructionSetViewModel mInstructionSetViewModel;
    private final Map<Integer, Tutorial> mScoredTutorials = new HashMap<>();

    public Search(CallbackForTutorial callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mInstructionSetViewModel = new ViewModelProvider(this).get(InstructionSetViewModel.class);
        mAdapter = new ResultsListAdapter(requireActivity(), this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recycler = view.findViewById(R.id.search_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        EditText searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mScoredTutorials.clear();
                mInstructionSetViewModel.getAll().observe(requireActivity(), instructionSets-> {
                    for(InstructionSet instructionSet : instructionSets) {
                        if(instructionSet.getInstructions().contains(s) || instructionSet.getTitle().contains(s)){
                            
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(Tutorial tutorial)
    {
        mCallback.serveSearchedTutorial(tutorial);
    }

    public interface CallbackForTutorial
    {
        void serveSearchedTutorial(Tutorial tutorial);
    }
}
