package com.orzechowski.saveme.creator.categorypicker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.categories.database.Category;

public class CategoryViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
{
    TextView mCategoryName;
    Category mCategory;
    CategoryAssignmentAdapter.FragmentCallback mCallback;

    public CategoryViewHolder(@NonNull View itemView,
                              CategoryAssignmentAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mCategoryName = itemView.findViewById(R.id.category_name_text);
        mCallback = fragmentCallback;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.pickCategory(mCategory);
    }
}