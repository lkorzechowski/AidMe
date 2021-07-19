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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.results.ResultsListAdapter;
import com.orzechowski.aidme.tutorial.database.Tutorial;

import org.jetbrains.annotations.NotNull;

public class Search extends Fragment implements ResultsListAdapter.OnClickListener
{
    private ResultsListAdapter mAdapter;
    public CallbackForTutorial mCallback;

    public Search(CallbackForTutorial callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
