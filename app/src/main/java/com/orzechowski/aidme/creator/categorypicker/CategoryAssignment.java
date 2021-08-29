package com.orzechowski.aidme.creator.categorypicker;

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
import com.orzechowski.aidme.database.tag.TutorialTag;

import java.util.LinkedList;
import java.util.List;

public class CategoryAssignment extends Fragment
    implements CategoryAssignmentAdapter.FragmentCallback
{
    private CategoryAssignmentAdapter mAdapter;
    private final List<Category> mCategoryPath = new LinkedList<>();
    private CategoryViewModel mCategoryViewModel;
    private CategoryTagViewModel mCategoryTagViewModel;
    private TagViewModel mTagViewModel;
    private int mLevel = 0;
    private final List<TutorialTag> mTutorialTags = new LinkedList<>();
    private final ActivityCallback mCallback;

    public CategoryAssignment(ActivityCallback callback)
    {
        mCallback = callback;
    }

    public void restorePrevious()
    {
        mLevel -= 2;
        int size = mCategoryPath.size();
        mCategoryPath.remove(size-1);
        pickCategory(mCategoryPath.get(size-2));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        ViewModelProvider provider = new ViewModelProvider(this);
        mCategoryViewModel = provider.get(CategoryViewModel.class);
        mCategoryTagViewModel = provider.get(CategoryTagViewModel.class);
        mTagViewModel = provider.get(TagViewModel.class);
        mAdapter = new CategoryAssignmentAdapter(activity, this);
        View view = inflater
                .inflate(R.layout.fragment_category_assignment, container, false);
        RecyclerView recycler = view.findViewById(R.id.category_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        if(mCategoryPath.isEmpty()) mCategoryViewModel.getByLevel(mLevel)
                .observe(activity, categories->mAdapter.setElementList(categories));
        else {
            mLevel--;
            pickCategory(mCategoryPath.get(mCategoryPath.size()-1));
        }
        return view;
    }

    @Override
    public void pickCategory(Category category)
    {
        mCategoryTagViewModel.getByCategoryId(category.getCategoryId())
                .observe(requireActivity(), categoryTags-> {
            for(CategoryTag categoryTag : categoryTags) {
                mTagViewModel.getById(categoryTag.getTagId()).observe(requireActivity(), tag-> {
                    if(category.getHasSubcategories()) {
                        if(!mCategoryPath.contains(category)) mCategoryPath.add(category);
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
                    } else {
                        mTutorialTags
                                .add(new TutorialTag(0L, 0L, tag.getTagId()));
                        if(mTutorialTags.size()==mCategoryPath.size()) {
                            mCallback.categorySelected(mTutorialTags);
                        }
                    }
                });
            }
        });
    }

    public interface ActivityCallback
    {
        void categorySelected(List<TutorialTag> tutorialTags);
    }

    public int getLevel()
    {
        return mLevel;
    }
}
