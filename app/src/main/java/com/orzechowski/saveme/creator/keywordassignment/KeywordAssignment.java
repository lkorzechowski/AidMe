package com.orzechowski.saveme.creator.keywordassignment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.search.database.Keyword;
import com.orzechowski.saveme.browser.search.database.KeywordViewModel;

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
                new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        recycler.setAdapter(mAdapter);
        Button submitButton = view.findViewById(R.id.approve_keywords);
        submitButton.setOnClickListener(v -> {
            if(!mKeywords.isEmpty()) mCallback.finalizeKeywords(mKeywords);
        });
        EditText keywordInput = view.findViewById(R.id.keyword_input);
        TextView suggest = view.findViewById(R.id.keyword_top_suggestion);
        ConstraintLayout suggestLayout = view.findViewById(R.id.keyword_suggestion_layout);
        keywordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                suggestLayout.setVisibility(View.GONE);
                if(s.length()>2) {
                    mKeywordViewModel.getAll().observe(activity, keywords -> {
                        String inputLower = keywordInput.getText().toString().toLowerCase();
                        for(Keyword keyword : keywords) {
                            String word = keyword.getKeyword();
                            if(word.contains(inputLower)) {
                                if(mKeywords.contains(keyword)) keywords.remove(keyword);
                                else {
                                    suggest.setText(word);
                                    suggestLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        Button addKeyword = view.findViewById(R.id.add_keyword_button);
        addKeyword.setOnClickListener(v -> {
            String input = keywordInput.getText().toString();
            if(input.length()>2) {
                mKeywords.add(new Keyword(0L, input));
                keywordInput.setText("");
                mAdapter.setElementList(mKeywords);
            } else {
                Toast.makeText(requireActivity(), R.string.keyword_assignment_word_too_short,
                        Toast.LENGTH_SHORT).show();
            }
        });
        suggestLayout.setOnClickListener(v -> {
            mKeywords.add(new Keyword(0L, suggest.getText().toString()));
            keywordInput.setText("");
            mAdapter.setElementList(mKeywords);
        });
        return view;
    }

    @Override
    public void remove(Keyword keyword)
    {
        mKeywords.remove(keyword);
        mAdapter.setElementList(mKeywords);
    }

    public interface ActivityCallback
    {
        void finalizeKeywords(List<Keyword> keywords);
    }
}
