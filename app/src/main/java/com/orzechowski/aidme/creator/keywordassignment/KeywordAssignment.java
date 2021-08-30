package com.orzechowski.aidme.creator.keywordassignment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.search.database.Keyword;
import com.orzechowski.aidme.browser.search.database.KeywordViewModel;

import java.util.LinkedList;
import java.util.List;

public class KeywordAssignment extends Fragment implements AddedKeywordAdapter.FragmentCallback
{
    private AddedKeywordAdapter mAdapter;
    private final List<Keyword> mKeywords = new LinkedList<>();
    private final ActivityCallback mCallback;
    private KeywordViewModel mKeywordViewModel;

    public KeywordAssignment(ActivityCallback callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        ViewModelProvider provider = new ViewModelProvider(this);
        mKeywordViewModel = provider.get(KeywordViewModel.class);
        mAdapter = new AddedKeywordAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_keyword_assignment, container,
                false);
        RecyclerView recycler = view.findViewById(R.id.added_keyword_rv);
        recycler.setLayoutManager(
                new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));
        recycler.setAdapter(mAdapter);
        Button submitButton = view.findViewById(R.id.approve_keywords);
        submitButton.setOnClickListener(v -> {
            if(!mKeywords.isEmpty()) mCallback.submitKeywords(mKeywords);
        });
        EditText keywordInput = view.findViewById(R.id.keyword_input);
        keywordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(s.length()>2) {
                    mKeywordViewModel.getAll().observe(activity, keywords -> {
                        for(Keyword keyword : keywords) {
                            if(mKeywords.contains(keyword)) keywords.remove(keyword);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return view;
    }

    @Override
    public void select(Keyword keyword)
    {
        mKeywords.add(keyword);
    }

    public interface ActivityCallback
    {
        void submitKeywords(List<Keyword> keywords);
    }
}
