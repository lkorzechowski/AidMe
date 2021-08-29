package com.orzechowski.aidme.browser.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.categories.database.Category;
import com.orzechowski.aidme.browser.categories.database.CategoryViewModel;
import com.orzechowski.aidme.database.tag.CategoryTag;
import com.orzechowski.aidme.database.tag.CategoryTagViewModel;
import com.orzechowski.aidme.database.tag.TagViewModel;

import java.util.LinkedList;
import java.util.List;

public class CategoryRecycler extends Fragment implements CategoryListAdapter.FragmentCallback
{
    private CategoryListAdapter mAdapter;
    private CategoryViewModel mCategoryViewModel;
    private CategoryTagViewModel mCategoryTagViewModel;
    private TagViewModel mTagViewModel;
    private int mLevel = 0;
    private final List<Category> mCategoryPath = new LinkedList<>();
    private final CallbackToResults mCallback;

    public CategoryRecycler(CallbackToResults callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        ViewModelProvider provider = new ViewModelProvider(this);
        mTagViewModel = provider.get(TagViewModel.class);
        mCategoryViewModel = provider.get(CategoryViewModel.class);
        mCategoryTagViewModel = provider.get(CategoryTagViewModel.class);
        mAdapter = new CategoryListAdapter(activity, this);
        View view = inflater
                .inflate(R.layout.fragment_recycler_browser, container, false);
        RecyclerView recycler = view.findViewById(R.id.browser_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        if(mCategoryPath.isEmpty()) mCategoryViewModel.getByLevel(mLevel)
                .observe(activity, categories->mAdapter.setElementList(categories));
        else {
            mLevel--;
            onClick(mCategoryPath.get(mCategoryPath.size()-1));
        }
        return view;
    }

    public void restorePrevious()
    {
        mLevel -= 2;
        int size = mCategoryPath.size();
        mCategoryPath.remove(size-1);
        onClick(mCategoryPath.get(size-2));
    }

    @Override
    public void onClick(Category category)
    {
        if(category.getHasSubcategories()) {
            if(!mCategoryPath.contains(category)) mCategoryPath.add(category);
            mCategoryTagViewModel.getByCategoryId(category
                    .getCategoryId()).observe(requireActivity(), categoryTags-> {
                for(CategoryTag categoryTag : categoryTags) {
                    mTagViewModel.getById(categoryTag.getTagId()).observe(requireActivity(), tag-> {
                        if(tag.getTagLevel()!=null && tag.getTagLevel()>mLevel) {
                            mLevel++;
                            mCategoryViewModel.getByLevel(mLevel)
                                    .observe(requireActivity(), categories-> {
                                long finalId = categories.get(categories.size()-1).getCategoryId();
                                for (Category cat : categories) {
                                    mCategoryTagViewModel.getByCategoryId(cat.getCategoryId())
                                            .observe(requireActivity(), catTag-> {
                                        boolean match = false;
                                        for(CategoryTag oneTag : catTag) {
                                            if(oneTag.getTagId()==tag.getTagId()) match = true;
                                        }
                                        if(!match) categories.remove(cat);
                                        if(cat.getCategoryId()==finalId) {
                                            mAdapter.setElementList(categories);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        } else {
            mCategoryTagViewModel.getByCategoryId(category.getCategoryId())
                    .observe(requireActivity(), categoryTags-> {
                for (CategoryTag categoryTag : categoryTags) {
                    mTagViewModel.getById(categoryTag.getTagId())
                            .observe(requireActivity(), tag -> {
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
