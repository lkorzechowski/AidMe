package com.orzechowski.aidme.browser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.database.Category;

import java.util.List;

public class BrowserListAdapter extends RecyclerView.Adapter<BrowserListAdapter.CategoryViewHolder>
{
    private List<Category> mCategories = null;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;

    public BrowserListAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_categories_rv, viewGroup, false);
        return new CategoryViewHolder(row, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryHolder, int rowNumber)
    {
        Category category = mCategories.get(rowNumber);
        categoryHolder.thisCategory = category;
        categoryHolder.name.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount()
    {
        if(mCategories!=null) return mCategories.size();
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
        TextView name;
        OnClickListener listenerForThisRow;
        Category thisCategory;

        public CategoryViewHolder(@NonNull View viewForThisRow, OnClickListener listener)
        {
            super(viewForThisRow);
            name = viewForThisRow.findViewById(R.id.category_name_text);
            listenerForThisRow = listener;
            viewForThisRow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listenerForThisRow.onClick(thisCategory);
        }
    }

    public interface OnClickListener
    {
        void onClick(Category category);
    }
}