package com.orzechowski.saveme.browser.categories;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.categories.database.Category;

import java.util.List;

public class CategoryListAdapter
        extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>
{
    private List<Category> mCategories;
    private final LayoutInflater mInflater;
    private final FragmentCallback mListener;
    private final String mPathBase;

    public CategoryListAdapter(Activity activity, FragmentCallback listener)
    {
        mInflater = LayoutInflater.from(activity);
        mListener = listener;
        mPathBase = activity.getFilesDir().getAbsolutePath()+"/";
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
        Uri uri = Uri.parse(mPathBase + category.getMiniatureName());
        if(uri != null) categoryHolder.image.setImageURI(uri);
    }

    @Override
    public int getItemCount()
    {
        return (mCategories == null) ? 0 : mCategories.size();
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
        FragmentCallback listenerForThisRow;
        Category thisCategory;

        public CategoryViewHolder(@NonNull View itemView, FragmentCallback listener)
        {
            super(itemView);
            name = itemView.findViewById(R.id.category_name_text);
            image = itemView.findViewById(R.id.category_image);
            listenerForThisRow = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            listenerForThisRow.onClick(thisCategory);
        }
    }

    public interface FragmentCallback
    {
        void onClick(Category category);
    }
}
