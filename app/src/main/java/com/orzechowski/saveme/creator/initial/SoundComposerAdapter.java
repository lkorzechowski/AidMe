package com.orzechowski.saveme.creator.initial;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.sound.database.TutorialSound;

import java.util.List;

public class SoundComposerAdapter
    extends RecyclerView.Adapter<SoundComposerAdapter.SoundViewHolder>
{
    private List<TutorialSound> mSounds = null;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;
    private final Activity mActivity;

    public SoundComposerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mActivity = activity;
    }

    @NonNull
    @Override
    public SoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_sound_rv, parent, false);
        return new SoundViewHolder(row, mActivity, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundViewHolder holder, int position)
    {
        TutorialSound sound = mSounds.get(position);
        holder.soundStart.setText(String.valueOf(sound.getSoundStart()));
        holder.soundLoop.setChecked(sound.getSoundLoop());
        holder.playInterval.setText(String.valueOf(sound.getInterval()));
        holder.fileName.setText(sound.getFileName());
        holder.sound = sound;
    }

    @Override
    public int getItemCount()
    {
        return (mSounds==null) ? 0 : mSounds.size();
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
        TutorialSound sound;
        ImageView deleteSound;
        Activity activity;

        public SoundViewHolder(@NonNull View itemView, Activity requestActivity,
                               FragmentCallback callback)
        {
            super(itemView);
            activity = requestActivity;
            playInterval = itemView.findViewById(R.id.new_sound_interval_input);
            uploadSoundButton = itemView.findViewById(R.id.new_sound_upload_button);
            soundStart = itemView.findViewById(R.id.new_sound_start_input);
            fileName = itemView.findViewById(R.id.new_sound_filename_display);
            soundLoop = itemView.findViewById(R.id.new_sound_loop_checkbox);
            deleteSound = itemView.findViewById(R.id.delete_new_sound);
            deleteSound.setOnClickListener(v-> callback.delete(sound));
            soundLoop.setOnCheckedChangeListener((buttonView, isChecked)-> {
                if(sound!=null && callback!=null) {
                    callback.modifyLoop(isChecked, sound.getSoundId());
                }
            });
            uploadSoundButton.setOnClickListener(v -> {
                if(ActivityCompat.checkSelfPermission(activity, Manifest.permission
                        .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(activity,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            122);
                } else {
                    callback.addSound((int)sound.getSoundId());
                }
            });
            soundStart.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    String startText = String.valueOf(soundStart.getText());
                    if(sound!=null  && callback!=null && !startText.isEmpty()) {
                        callback.modifyStart(Integer.parseInt(startText), sound.getSoundId());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
            playInterval.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    String intervalText = String.valueOf(playInterval.getText());
                    if(sound!=null  && callback!=null && !intervalText.isEmpty()) {
                        callback.modifyInterval(Integer.parseInt(intervalText), sound.getSoundId());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    public interface FragmentCallback
    {
        void delete(TutorialSound tutorialSound);
        void modifyLoop(boolean loop, Long soundId);
        void modifyStart(int start, Long soundId);
        void modifyInterval(int interval, Long soundId);
        void addSound(int position);
    }
}
