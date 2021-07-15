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

import java.util.LinkedList;
import java.util.List;

public class BrowserRecycler extends Fragment implements BrowserListAdapter.OnClickListener
{
    private BrowserListAdapter mAdapter;
    private CategoryViewModel mCategoryViewModel;
    private CategoryTagViewModel mCategoryTagViewModel;
    private TagViewModel mTagViewModel;
    private int mLevel = 0;
    private long currentTagId = 15L;
    private final List<Category> mCategoryPath = new LinkedList<>();
    private final CallbackToResults mCallback;

    public BrowserRecycler(CallbackToResults callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FragmentActivity activity = requireActivity();
        mTagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryTagViewModel = new ViewModelProvider(this).get(CategoryTagViewModel.class);
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

    public boolean restorePrevious()
    {
        mLevel -= 2;
        int size = mCategoryPath.size();
        if(size>1) {
            mCategoryPath.remove(size-1);
            onClick(mCategoryPath.get(size-2));
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void onClick(Category category)
    {
        if(category.getHasSubcategories()) {
            if(!mCategoryPath.contains(category)) mCategoryPath.add(category);
            mCategoryTagViewModel.getByCategoryId(category.getCategoryId()).observe(requireActivity(), categoryTags-> {
                for(CategoryTag categoryTag : categoryTags) {
                    mTagViewModel.getById(categoryTag.getTagId()).observe(requireActivity(), tag-> {
                        if(tag.getTagLevel()!=null && tag.getTagLevel()>mLevel) {
                            mLevel = tag.getTagLevel();
                            currentTagId = tag.getTagId();
                            mCategoryViewModel.getByLevel(mLevel).observe(requireActivity(), categories-> {
                                for (Category cat : categories) {
                                    mCategoryTagViewModel.getByCategoryId(cat.getCategoryId()).observe(requireActivity(), catTag-> {
                                        boolean match = false;
                                        for(CategoryTag oneTag : catTag) {
                                            if(oneTag.getTagId()==currentTagId) match = true;
                                        }
                                        if(!match) categories.remove(cat);
                                    });
                                }
                                mAdapter.setElementList(categories);
                            });
                        }
                    });
                }
            });
        } else {
            mCategoryTagViewModel.getByCategoryId(category.getCategoryId()).observe(requireActivity(), categoryTags-> {
                for (CategoryTag categoryTag : categoryTags) {
                    mTagViewModel.getById(categoryTag.getTagId()).observe(requireActivity(), tag -> {
                        if (tag.getTagLevel() != null && tag.getTagLevel() > mLevel) {
                            mCallback.serveResults(tag.getTagId());
                        }
                    });
                }
            });

        }
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
