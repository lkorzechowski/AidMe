package com.orzechowski.aidme.browser.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.orzechowski.aidme.browser.search.database.KeywordViewModel;
import com.orzechowski.aidme.browser.search.database.TagKeyword;
import com.orzechowski.aidme.browser.search.database.TagKeywordViewModel;
import com.orzechowski.aidme.database.helper.HelperViewModel;
import com.orzechowski.aidme.database.tag.TagViewModel;
import com.orzechowski.aidme.database.tag.TutorialTag;
import com.orzechowski.aidme.database.tag.TutorialTagViewModel;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialViewModel;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.aidme.tutorial.instructions.database.InstructionSetViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Search extends Fragment implements ResultsListAdapter.OnClickListener
{
    private ResultsListAdapter mAdapter;
    public CallbackForTutorial mCallback;
    private InstructionSetViewModel mInstructionSetViewModel;
    private TutorialViewModel mTutorialViewModel;
    private KeywordViewModel mKeywordViewModel;
    private TagViewModel mTagViewModel;
    private TagKeywordViewModel mTagKeywordViewModel;
    private TutorialTagViewModel mTutorialTagViewModel;
    private HelperViewModel mHelperViewModel;
    private final Map<Long, Integer> mScoredTutorialIds = new HashMap<>();

    public Search(CallbackForTutorial callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mInstructionSetViewModel = new ViewModelProvider(this).get(InstructionSetViewModel.class);
        mTutorialViewModel = new ViewModelProvider(this).get(TutorialViewModel.class);
        mTagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        mKeywordViewModel = new ViewModelProvider(this).get(KeywordViewModel.class);
        mTutorialTagViewModel = new ViewModelProvider(this).get(TutorialTagViewModel.class);
        mTagKeywordViewModel = new ViewModelProvider(this).get(TagKeywordViewModel.class);
        mHelperViewModel = new ViewModelProvider(this).get(HelperViewModel.class);
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
                mScoredTutorialIds.clear();
                String [] words = String.valueOf(s).split ("\\W+");
                mHelperViewModel.getAll().observe(requireActivity(), helpers-> {
                    List<Tutorial> pickedTutorials = new LinkedList<>();
                    mTutorialViewModel.getAll().observe(requireActivity(), tutorials->
                            mInstructionSetViewModel.getAll().observe(requireActivity(), instructionSets-> {
                        for(InstructionSet instructionSet : instructionSets) {
                            if(instructionSet.getInstructions().contains(s) || instructionSet.getTitle().contains(s)) {
                                putScore(instructionSet.getTutorialId());
                            }
                        }
                        for(Tutorial tutorial : tutorials) {
                            long id = tutorial.getTutorialId();
                            if(tutorial.getTutorialName().contains(s)) {
                                putScore(id);
                            }
                        }
                        for(String word : words){
                            mKeywordViewModel.getByPartialWord(word).observe(requireActivity(), keyword-> {
                                Log.w("turkusowy", "match!");
                                mTagKeywordViewModel.getByKeywordId(keyword.getKeywordId()).observe(requireActivity(), tagKeywords-> {
                                    for(TagKeyword tagKeyword : tagKeywords) {
                                        mTagViewModel.getById(tagKeyword.getTagId()).observe( requireActivity(), tag->
                                                mTutorialTagViewModel.getByTutorialId(tag.getTagId()).observe(requireActivity(), tutorialTags-> {
                                            for(TutorialTag tutorialTag : tutorialTags) {
                                                putScore(tutorialTag.getTutorialId());
                                            }
                                            int displayLimit = 20;
                                            while(displayLimit>0) {
                                                int matchScale = 0;
                                                Long tutorialId = null;
                                                for(long id : mScoredTutorialIds.keySet()) {
                                                    int value = Objects.requireNonNull(mScoredTutorialIds.get(id));
                                                    if(value>matchScale) {
                                                        matchScale = value;
                                                        tutorialId = id;
                                                    }
                                                }
                                                if(tutorialId!=null) {
                                                    mScoredTutorialIds.remove(tutorialId);
                                                    mTutorialViewModel.getByTutorialId(tutorialId).observe(requireActivity(), tutorial-> {
                                                        pickedTutorials.add(tutorial);
                                                        mAdapter.setElementList(pickedTutorials, helpers);
                                                    });
                                                }
                                                displayLimit--;
                                            }
                                        }));
                                    }
                                });
                            });
                        }
                    }));
                });
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void putScore(long id)
    {
        if(mScoredTutorialIds.containsKey(id)) {
            int oldValue = Objects.requireNonNull(mScoredTutorialIds.get(id));
            mScoredTutorialIds.remove(id);
            mScoredTutorialIds.put(id, oldValue+1);
        } else {
            mScoredTutorialIds.put(id, 1);
        }
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
