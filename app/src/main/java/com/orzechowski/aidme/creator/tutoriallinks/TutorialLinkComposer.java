package com.orzechowski.aidme.creator.tutoriallinks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.database.Tutorial;

import java.util.List;

public class TutorialLinkComposer extends Fragment
{
    private TutorialLinkComposerAdapter mLinkAdapter;
    private TutorialLinkTutorialListAdapter mTutorialAdapter;
    private final List<Tutorial> mAllTutorials;
    private final ActivityCallback mCallback;

    public TutorialLinkComposer(List<Tutorial> tutorials, ActivityCallback callback)
    {
        mAllTutorials = tutorials;
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        View view = inflater
                .inflate(R.layout.fragment_tutorial_link_composer, container, false);
        RecyclerView linkRecycler = view.findViewById(R.id.link_rv);
        Context context = view.getContext();
        linkRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
                false));
        RecyclerView tutorialRecycler = view.findViewById(R.id.tutorials_rv);
        tutorialRecycler.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

    }

    public interface ActivityCallback
    {
        void finalizeTutorialLinks(List<Tutorial> tutorials);
    }
}
