package com.orzechowski.aidme.creator;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.mediaplayer.sound.database.TutorialSound;

import java.util.List;

public class SoundComposerAdapter
    extends RecyclerView.Adapter<SoundComposerAdapter.SoundViewHolder>
{
    private List<TutorialSound> mSounds = null;
    private final LayoutInflater mInflater;

    public SoundComposerAdapter(Activity activity)
    {
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_sound_rv, parent, false);
        return new SoundViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position)
    {
        TutorialSound sound = mSounds.get(position);
        holder.soundStart.setText(String.valueOf(sound.getSoundStart()));
        holder.soundLoop.setChecked(sound.getSoundLoop());
        holder.playInterval.setText(String.valueOf(sound.getInterval()));
        holder.fileName.setText(sound.getFileName());
    }

    @Override
    public int getItemCount()
    {
        if(mSounds!=null) return mSounds.size();
        else return 0;
    }

    public void setElementList(List<TutorialSound> sounds)
    {
        mSounds = sounds;
        notifyDataSetChanged();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder
    {
        EditText soundStart, playInterval;
        CheckBox soundLoop;
        TextView fileName;
        Button uploadSoundButton;

        public SoundViewHolder(@NonNull View itemView)
        {
            super(itemView);
            playInterval = itemView.findViewById(R.id.new_sound_interval_input);
            uploadSoundButton = itemView.findViewById(R.id.new_sound_upload_button);
            soundStart = itemView.findViewById(R.id.new_sound_start_input);
            fileName = itemView.findViewById(R.id.new_sound_filename_display);
            soundLoop = itemView.findViewById(R.id.new_sound_loop_checkbox);
        }
    }
}
