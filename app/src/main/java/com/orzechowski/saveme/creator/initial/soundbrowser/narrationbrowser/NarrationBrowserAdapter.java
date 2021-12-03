package com.orzechowski.saveme.creator.initial.soundbrowser.narrationbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.creator.initial.soundbrowser.Sound;

import java.util.List;

public class NarrationBrowserAdapter extends RecyclerView.Adapter<NarrationViewHolder>
{
    private List<Sound> mNarrations;
    private final FragmentCallback mCallback;
    private final LayoutInflater mInflater;

    public NarrationBrowserAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
    }

    public void setElementList(List<Sound> narrations)
    {
        mNarrations = narrations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NarrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row_sound_browser, parent, false);
        return new NarrationViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull NarrationViewHolder holder, int position)
    {
        Sound narration = mNarrations.get(position);
        holder.mNarration = narration;
        holder.mNarrationNameDisplay.setText(narration.getDisplayName());
    }

    @Override
    public int getItemCount()
    {
        return (mNarrations == null) ? 0 : mNarrations.size();
    }

    public interface FragmentCallback
    {
        void narrationClick(Sound sound);
    }
}
