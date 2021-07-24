package com.orzechowski.aidme.creator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class MultimediaComposer extends Fragment
{
    private MultimediaComposerAdapter mAdapter;
    private final List<Multimedia> multimediaList = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new MultimediaComposerAdapter(activity);
        return inflater.inflate(R.layout.fragment_multimedia_composer, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addMultimediaButton = view.findViewById(R.id.new_multimedia_button);
        addMultimediaButton.setOnClickListener(v -> {
            multimediaList.add(new Multimedia(0, 0, 0, false, "", false, multimediaList.size()));
            mAdapter.setElementList(multimediaList);
        });
    }
}
