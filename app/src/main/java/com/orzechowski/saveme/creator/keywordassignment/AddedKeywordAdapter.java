package com.orzechowski.saveme.creator.keywordassignment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.search.database.Keyword;

import java.util.List;

public class AddedKeywordAdapter
        extends RecyclerView.Adapter<AddedKeywordAdapter.KeywordViewHolder>
{
    private List<Keyword> mKeywords = null;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;

    public AddedKeywordAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    @NonNull
    @Override
    public KeywordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_added_keyword_rv, parent, false);
        return new KeywordViewHolder(row, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordViewHolder holder, int position)
    {
        Keyword keyword = mKeywords.get(position);
        holder.keyword = keyword;
        holder.keywordButton.setText(keyword.getKeyword());
    }

    @Override
    public int getItemCount()
    {
        return (mKeywords==null) ? 0 : mKeywords.size();
    }

    public void setElementList(List<Keyword> keywords)
    {
        mKeywords = keywords;
        notifyDataSetChanged();
    }

    public static class KeywordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        Keyword keyword;
        Button keywordButton;
        FragmentCallback callback;

        public KeywordViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            keywordButton = itemView.findViewById(R.id.added_keyword_button);
            callback = fragmentCallback;
            keywordButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.remove(keyword);
        }
    }

    public interface FragmentCallback
    {
        void remove(Keyword keyword);
    }
}
