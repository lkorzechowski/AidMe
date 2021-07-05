package com.orzechowski.aidme.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private CategoryViewModel mCategoryViewModel;
    private int mLevel = 0;
    private final CallbackToResults mCallback;

    public BrowserRecycler(CallbackToResults callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FragmentActivity activity = requireActivity();
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mAdapter = new BrowserListAdapter(activity, this);
        mCategoryViewModel.getByLevel(mLevel)
                .observe(activity, categories->mAdapter.setElementList(categories));
        View view = inflater.inflate(R.layout.fragment_recycler_browser, container, false);
        RecyclerView recycler = view.findViewById(R.id.browser_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(Category category)
    {
        String tags = category.getCategoryTags();
        if(category.getHasSubcategories()){
            mLevel = category.getCategoryLevel()+1;
            mCategoryViewModel.getByLevelAndTags(mLevel, tags)
                    .observe(requireActivity(), categories ->mAdapter.setElementList(categories));
        }
        else {
            mCallback.serveResults(tags);
        }
    }

    public interface CallbackToResults
    {
        void serveResults(String tags);
    }
}