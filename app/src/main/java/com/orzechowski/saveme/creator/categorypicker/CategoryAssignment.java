package com.orzechowski.saveme.creator.categorypicker;

import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.categories.database.Category;
import com.orzechowski.saveme.browser.categories.database.CategoryViewModel;
import com.orzechowski.saveme.database.tag.CategoryTag;
import com.orzechowski.saveme.database.tag.CategoryTagViewModel;
import com.orzechowski.saveme.database.tag.Tag;
import com.orzechowski.saveme.database.tag.TagViewModel;
import com.orzechowski.saveme.database.tag.TutorialTag;

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
    private RecyclerView mRecycler;

    public CategoryAssignment(ActivityCallback callback)
    {
        mCallback = callback;
    }

    public void restorePrevious()
    {
        if(mLevel == 1) mLevel = 0;
        else mLevel -= 2;
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
        mRecycler = view.findViewById(R.id.category_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mAdapter);
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
        FragmentActivity activity = requireActivity();
        mCategoryTagViewModel.getByCategoryId(category.getCategoryId())
                .observe(activity, categoryTags -> {
            for(CategoryTag categoryTag : categoryTags) {
                mTagViewModel.getByTagId(categoryTag.getTagId()).observe(activity, tag -> {
                    if(category.getHasSubcategories()) {
                        if(!mCategoryPath.contains(category)) mCategoryPath.add(category);
                        if(tag.getTagLevel() != null && tag.getTagLevel() > mLevel) {
                            mLevel++;
                            mCategoryViewModel.getByLevel(mLevel)
                                    .observe(activity, categories -> {
                                long finalId = categories.get(categories.size()-1).getCategoryId();
                                for (Category cat : categories) {
                                    mCategoryTagViewModel.getByCategoryId(cat.getCategoryId())
                                            .observe(activity, catTag -> {
                                        boolean match = false;
                                        for(CategoryTag oneTag : catTag) {
                                            if(oneTag.getTagId() == tag.getTagId()) match = true;
                                        }
                                        if(!match) categories.remove(cat);
                                        if(cat.getCategoryId() == finalId) {
                                            mAdapter.setElementList(categories);
                                        }
                                    });
                                }
                            });
                        }
                    } else {
                        mTutorialTags
                                .add(new TutorialTag(0L, 0L, tag.getTagId()));
                        if(mTutorialTags.size() == mCategoryPath.size()) {
                            View view = requireView();
                            TextView header = view.findViewById(R.id.category_assignment_header);
                            header.setText(R.string.category_assignment_header_second_step);
                            mRecycler.setVisibility(View.GONE);
                            ConstraintLayout tagInsertLayout = view
                                    .findViewById(R.id.tag_insert_layout);
                            tagInsertLayout.setVisibility(View.VISIBLE);
                            Button submitInsert = view.findViewById(R.id.submit_tag_insert_button);
                            EditText tagInsert = view.findViewById(R.id.tag_insert);
                            submitInsert.setOnClickListener(v -> {
                                if(tagInsert.getText().length() > 3) {
                                    mCallback.finalizeCategory(mTutorialTags, new Tag(0,
                                            tagInsert.getText().toString(), mLevel + 1));
                                } else {
                                    Toast.makeText(activity, R.string.tag_insert_value_too_short,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public interface ActivityCallback
    {
        void finalizeCategory(List<TutorialTag> tutorialTags, Tag uniqueTag);
    }

    public int getLevel()
    {
        return mLevel;
    }
}
