package com.orzechowski.saveme.creator.categorypicker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.categories.database.Category;

import java.util.List;

public class CategoryAssignmentAdapter
        extends RecyclerView.Adapter<CategoryAssignmentAdapter.CategoryViewHolder>
{
    private List<Category> mCategories;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public CategoryAssignmentAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_category_assignment, parent, false);
        return new CategoryViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position)
    {
        Category category = mCategories.get(position);
        holder.category = category;
        holder.categoryName.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount()
    {
        if(mCategories != null) return mCategories.size();
        else return 0;
    }

    public void setElementList(List<Category> categories)
    {
        mCategories = categories;
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        TextView categoryName;
        Category category;
        FragmentCallback callback;

        public CategoryViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name_text);
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.pickCategory(category);
        }
    }

    public interface FragmentCallback
    {
        void pickCategory(Category category);
    }
}
