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

public class CategoryAssignment extends Fragment
    implements CategoryAssignmentAdapter.FragmentCallback
{
    private CategoryAssignmentAdapter mAdapter;
    private CategoryViewModel mCategoryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new CategoryAssignmentAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_category_assignment, container,
                false);
        RecyclerView recycler = view.findViewById(R.id.category_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getByLevel(0).observe(activity, categories ->
                mAdapter.setElementList(categories));
        return view;
    }

    @Override
    public void pickCategory(Category category)
    {

    }
}
