package com.orzechowski.aidme.creator.keywordadder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.search.database.Keyword;

import java.util.LinkedList;
import java.util.List;

public class AddedKeywordRecycler extends Fragment implements AddedKeywordAdapter.FragmentCallback
{
    private AddedKeywordAdapter mAdapter;
    private final List<Keyword> mKeywords = new LinkedList<>();
    private final ActivityCallback mCallback;

    public AddedKeywordRecycler(ActivityCallback callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new AddedKeywordAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_added_keyword, container, false);
        RecyclerView recycler = view.findViewById(R.id.added_keyword_rv);
        recycler.setLayoutManager(
                new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));
        recycler.setAdapter(mAdapter);
        Button submitButton = view.findViewById(R.id.approve_keywords);
        submitButton.setOnClickListener(v -> {
            if(!mKeywords.isEmpty()) mCallback.submitKeywords(mKeywords);
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
