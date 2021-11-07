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

public class NarrationBrowserAdapter
        extends RecyclerView.Adapter<NarrationBrowserAdapter.NarrationViewHolder>
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
        holder.narration = narration;
        holder.narrationNameDisplay.setText(narration.getDisplayName());
    }

    @Override
    public int getItemCount()
    {
        return (mNarrations == null) ? 0 : mNarrations.size();
    }

    public static class NarrationViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        Sound narration;
        TextView narrationNameDisplay;
        FragmentCallback callback;

        public NarrationViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback)
        {
            super(itemView);
            narrationNameDisplay = itemView.findViewById(R.id.sound_display_name);
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.narrationClick(narration);
        }
    }

    public interface FragmentCallback
    {
        void narrationClick(Sound sound);
    }
}
