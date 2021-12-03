package com.orzechowski.saveme.creator.keywordassignment;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.search.database.Keyword;

public class KeywordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    Keyword mKeyword;
    Button mKeywordButton;
    AddedKeywordAdapter.FragmentCallback mCallback;

    public KeywordViewHolder(@NonNull View itemView,
                             AddedKeywordAdapter.FragmentCallback fragmentCallback)
    {
        super(itemView);
        mKeywordButton = itemView.findViewById(R.id.added_keyword_button);
        mCallback = fragmentCallback;
        mKeywordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.remove(mKeyword);
    }
}