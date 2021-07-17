package com.orzechowski.aidme.browser.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orzechowski.aidme.R;

import org.jetbrains.annotations.NotNull;

public class Search extends Fragment
{
    TextView mSuggestionHeader, mSuggestionName;
    ImageView mSuggestionImage;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        mSuggestionHeader = view.findViewById(R.id.search_suggestion_header);
        View suggestionItem = view.findViewById(R.id.search_suggestion_item);
        mSuggestionName = suggestionItem.findViewById(R.id.result_name_text);
        mSuggestionImage = suggestionItem.findViewById(R.id.result_image);
    }
}
