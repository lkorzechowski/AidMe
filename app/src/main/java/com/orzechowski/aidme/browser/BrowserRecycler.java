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
import com.orzechowski.aidme.database.tag.CategoryTag;
import com.orzechowski.aidme.database.tag.CategoryTagViewModel;
import com.orzechowski.aidme.database.tag.TagViewModel;

import org.jetbrains.annotations.NotNull;

public class BrowserRecycler extends Fragment implements BrowserListAdapter.OnClickListener
{
    private BrowserListAdapter mAdapter;
    private CategoryViewModel mCategoryViewModel;
    private int mLevel = 0;
    private long currentTagId = 15L;
    private final CallbackToResults mCallback;
    private boolean mSubcategories;

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
        mSubcategories = true;
        CategoryTagViewModel categoryTagViewModel = new ViewModelProvider(this).get(CategoryTagViewModel.class);
        TagViewModel tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        categoryTagViewModel.getByCategoryId(category.getCategoryId()).observe(requireActivity(), categoryTags-> {
            for(CategoryTag categoryTag : categoryTags) {
                tagViewModel.getById(categoryTag.getTagId()).observe(requireActivity(), tag-> {
                    if(tag.getTagLevel()!=null && tag.getTagLevel() > mLevel){
                        mLevel = tag.getTagLevel();
                        currentTagId = tag.getTagId();
                    }
                    if(category.getHasSubcategories()) {
                        mCategoryViewModel.getByLevel(mLevel).observe(requireActivity(), categories-> {
                            for (Category cat : categories) {
                                categoryTagViewModel.getByCategoryId(cat.getCategoryId()).observe(requireActivity(), catTag-> {
                                    boolean match = false;
                                    for(CategoryTag oneTag : catTag) {
                                        if(oneTag.getTagId()==currentTagId) match = true;
                                    }
                                    if(!match) categories.remove(cat);
                                });
                            }
                            mAdapter.setElementList(categories);
                        });
                    } else {
                        mSubcategories = false;
                    }
                });
            }
        });
        if(!mSubcategories) mCallback.serveResults(currentTagId);
    }

    public interface CallbackToResults
    {
        void serveResults(long tagId);
    }

    public int getLevel()
    {
        return mLevel;
    }
}
