package com.orzechowski.saveme.browser.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.results.ResultsListAdapter;
import com.orzechowski.saveme.browser.search.database.Keyword;
import com.orzechowski.saveme.browser.search.database.KeywordViewModel;
import com.orzechowski.saveme.browser.search.database.TagKeyword;
import com.orzechowski.saveme.browser.search.database.TagKeywordViewModel;
import com.orzechowski.saveme.database.tag.TagViewModel;
import com.orzechowski.saveme.database.tag.TutorialTag;
import com.orzechowski.saveme.database.tag.TutorialTagViewModel;
import com.orzechowski.saveme.helper.database.Helper;
import com.orzechowski.saveme.helper.database.HelperViewModel;
import com.orzechowski.saveme.tutorial.database.Tutorial;
import com.orzechowski.saveme.tutorial.database.TutorialViewModel;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSetViewModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Search extends Fragment implements ResultsListAdapter.FragmentCallback
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        ViewModelProvider provider = new ViewModelProvider(this);
        mInstructionSetViewModel = provider.get(InstructionSetViewModel.class);
        mTutorialViewModel = provider.get(TutorialViewModel.class);
        mTagViewModel = provider.get(TagViewModel.class);
        mKeywordViewModel = provider.get(KeywordViewModel.class);
        mTutorialTagViewModel = provider.get(TutorialTagViewModel.class);
        mTagKeywordViewModel = provider.get(TagKeywordViewModel.class);
        mHelperViewModel = provider.get(HelperViewModel.class);
        mAdapter = new ResultsListAdapter(requireActivity(), this);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recycler = view.findViewById(R.id.search_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle)
    {
        EditText searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length() > 2) {
                    mScoredTutorialIds.clear();
                    String[] words = String.valueOf(s).toLowerCase().split("\\W+");
                    mHelperViewModel.getAll().observe(requireActivity(), helpers ->
                            mTutorialViewModel.getAll().observe(requireActivity(), tutorials ->
                                    mInstructionSetViewModel.getAll()
                                            .observe(requireActivity(), instructionSets -> {
                                        for (String word : words) {
                                            String wordClean = removeSpecialSigns(word);
                                            for (InstructionSet instructionSet : instructionSets) {
                                                if (removeSpecialSigns(instructionSet
                                                        .getTitle()
                                                        .toLowerCase()).contains(wordClean)) {
                                                    putScore(instructionSet.getTutorialId(),
                                                            false);
                                                }
                                                if (removeSpecialSigns(instructionSet
                                                        .getInstructions()
                                                        .toLowerCase()).contains(wordClean)) {
                                                    putScore(instructionSet.getTutorialId(),
                                                            false);
                                                }
                                            }
                                            for (Tutorial tutorial : tutorials) {
                                                if (removeSpecialSigns(tutorial.getTutorialName())
                                                        .toLowerCase().contains(wordClean)) {
                                                    putScore(tutorial.getTutorialId(),
                                                            true);
                                                }
                                            }
                                            mKeywordViewModel.getAll()
                                                    .observe(requireActivity(), keywords -> {
                                                for (Keyword keyword : keywords) {
                                                    String obtainedWord = keyword.getKeyword();
                                                    if (obtainedWord.contains(wordClean) ||
                                                            wordClean.contains(obtainedWord)) {
                                                        mTagKeywordViewModel
                                                                .getByKeywordId(keyword.getKeywordId())
                                                                .observe(requireActivity(), tagKeywords -> {
                                                            for (TagKeyword tagKeyword : tagKeywords) {
                                                                mTagViewModel
                                                                        .getByTagId(tagKeyword.getTagId())
                                                                        .observe(requireActivity(), tag ->
                                                                        mTutorialTagViewModel
                                                                                .getByTagId(tag.getTagId())
                                                                                .observe(requireActivity(),
                                                                                        tutorialTags -> {
                                                                            for (TutorialTag tutorialTag : tutorialTags) {
                                                                                putScore(tutorialTag.getTutorialId(),
                                                                                        false);
                                                                            }
                                                                            setAdapter(helpers);
                                                                        }));
                                                            }
                                                        });
                                                    } else setAdapter(helpers);
                                                }
                                            });
                                        }
                                    })));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private String removeSpecialSigns(String input)
    {
        return input.replace('ł', 'l')
                .replace('ą', 'a')
                .replace('ń', 'n')
                .replace('ę', 'e')
                .replace('ć', 'c')
                .replace('ż', 'z')
                .replace('ź', 'z')
                .replace('ó', 'o')
                .replace('ś', 's');
    }

    private void setAdapter(List<Helper> helpers)
    {
        List<Tutorial> pickedTutorials = new LinkedList<>();
        for(int i = 0; i < 12; i++) {
            int match = 0;
            Long tutorialId = null;
            for (long id : mScoredTutorialIds.keySet()) {
                int value = Objects.requireNonNull(mScoredTutorialIds.get(id));
                if (value > match) {
                    match = value;
                    tutorialId = id;
                }
            }
            if (tutorialId != null) {
                mScoredTutorialIds.remove(tutorialId);
                mTutorialViewModel.getByTutorialId(tutorialId)
                        .observe(requireActivity(), tutorial-> {
                    pickedTutorials.add(tutorial);
                    mAdapter.setElementList(pickedTutorials, helpers);
                });
            } else return;
        }
    }

    private void putScore(long id, boolean titleMatch)
    {
        if(mScoredTutorialIds.containsKey(id)) {
            int oldValue = Objects.requireNonNull(mScoredTutorialIds.get(id));
            mScoredTutorialIds.remove(id);
            if(!titleMatch) mScoredTutorialIds.put(id, oldValue + 1);
            else mScoredTutorialIds.put(id, oldValue + 10);
        } else {
            if(!titleMatch) mScoredTutorialIds.put(id, 1);
            else mScoredTutorialIds.put(id, 10);
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
