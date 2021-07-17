package com.orzechowski.aidme.browser.categories;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.browser.categories.database.Category;
import com.orzechowski.aidme.tools.AssetObtainer;

import java.io.IOException;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>
{
    private List<Category> mCategories = null;
    private final LayoutInflater mInflater;
    private final OnClickListener mListener;
    private final AssetObtainer assetObtainer = new AssetObtainer();
    private final Context mContext;

    public CategoryListAdapter(Activity activity, OnClickListener listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
        mContext = activity.getBaseContext();
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
        String categoryName = category.getCategoryName();
        categoryHolder.name.setText(categoryName);
        if(categoryName.length() > 20) categoryHolder.name.setTextSize(20);
        Uri uri = null;
        try {
            uri = Uri.fromFile(assetObtainer.getFileFromAssets(mContext, category.getMiniatureName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(uri != null) categoryHolder.image.setImageURI(uri);
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
        ImageView image;
        OnClickListener listenerForThisRow;
        Category thisCategory;

        public CategoryViewHolder(@NonNull View viewForThisRow, OnClickListener listener)
        {
            super(viewForThisRow);
            name = viewForThisRow.findViewById(R.id.category_name_text);
            image = viewForThisRow.findViewById(R.id.category_image);
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
