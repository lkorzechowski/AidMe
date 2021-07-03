package com.orzechowski.aidme.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.database.Category;
import com.orzechowski.aidme.browser.database.CategoryViewModel;

import org.jetbrains.annotations.NotNull;

public class BrowserRecycler extends Fragment implements BrowserListAdapter.OnClickListener
{
    private BrowserListAdapter mAdapter;
    private int mLevel = 0;
    private String mTags = "";

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = requireArguments();
        if(bundle.containsKey("level")) mLevel = bundle.getInt("level");
        if(bundle.containsKey("tags")) mTags = bundle.getString("tags");
        FragmentActivity activity = requireActivity();
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mAdapter = new BrowserListAdapter(activity, this);
        categoryViewModel.getByLevelAndTags(mLevel, mTags)
                .observe(activity, categories->mAdapter.setElementList(categories));
        View view = inflater.inflate(R.layout.fragment_recycler_browser, container, false);
        RecyclerView recycler = view.findViewById(R.id.browser_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v->{

        });
    }

    @Override
    public void onClick(Category category) {

    }
}