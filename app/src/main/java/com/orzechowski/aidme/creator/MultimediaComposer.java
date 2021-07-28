package com.orzechowski.aidme.creator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.mediaplayer.multimedia.database.Multimedia;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class MultimediaComposer extends Fragment implements MultimediaComposerAdapter.FragmentCallback
{
    private MultimediaComposerAdapter mAdapter;
    private final List<Multimedia> mMultimedias = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new MultimediaComposerAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_multimedia_composer, container, false);
        RecyclerView recycler = view.findViewById(R.id.new_multimedia_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addMultimediaButton = view.findViewById(R.id.new_multimedia_button);
        addMultimediaButton.setOnClickListener(v-> {
            mMultimedias.add(new Multimedia(0, 0, 0, false, "", false, mMultimedias.size()));
            mAdapter.setElementList(mMultimedias);
        });
    }

    @Override
    public void delete(Multimedia multimedia)
    {
        mMultimedias.remove(multimedia);
        for(int i = multimedia.getPosition(); i < mMultimedias.size(); i++) {
            mMultimedias.get(i).setPosition(i);
        }
        mAdapter.setElementList(mMultimedias);
    }

    @Override
    public void modifyDisplayTime(int time, int position)
    {
        mMultimedias.get(position).setDisplayTime(time);
    }

    @Override
    public void modifyLoop(boolean loop, int position)
    {
        mMultimedias.get(position).setLoop(loop);
    }
}
