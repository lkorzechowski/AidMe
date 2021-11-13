package com.orzechowski.saveme.browser.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.database.tag.HelperTag;
import com.orzechowski.saveme.database.tag.HelperTagViewModel;
import com.orzechowski.saveme.database.tag.TutorialTag;
import com.orzechowski.saveme.database.tag.TutorialTagViewModel;
import com.orzechowski.saveme.helper.database.Helper;
import com.orzechowski.saveme.helper.database.HelperViewModel;
import com.orzechowski.saveme.tutorial.database.Tutorial;
import com.orzechowski.saveme.tutorial.database.TutorialViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class ResultsRecycler extends Fragment implements ResultsListAdapter.FragmentCallback
{
    private ResultsListAdapter mAdapter;
    public CallbackForTutorial mCallback;

    public ResultsRecycler(CallbackForTutorial callback)
    {
        mCallback = callback;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        long tagId = requireArguments().getLong("tagId");
        TutorialTagViewModel tutorialTagViewModel = new ViewModelProvider(this)
                .get(TutorialTagViewModel.class);
        TutorialViewModel tutorialViewModel = new ViewModelProvider(this)
                .get(TutorialViewModel.class);
        HelperTagViewModel helperTagViewModel = new ViewModelProvider(this)
                .get(HelperTagViewModel.class);
        HelperViewModel helperViewModel = new ViewModelProvider(this)
                .get(HelperViewModel.class);
        mAdapter = new ResultsListAdapter(requireActivity(), this);
        tutorialTagViewModel.getByTagId(tagId).observe(requireActivity(), tutorialTags-> {
            List<Tutorial> tutorials = new LinkedList<>();
            helperTagViewModel.getByTagId(tagId).observe(requireActivity(), helperTags-> {
                List<Helper> helpers = new LinkedList<>();
                for(HelperTag helperTag : helperTags) {
                    helperViewModel.getByHelperId(helperTag.getHelperId())
                            .observe(requireActivity(), helpers::add);
                }
                for(TutorialTag tutorialTag : tutorialTags) {
                    tutorialViewModel.getByTutorialId(tutorialTag.getTutorialId())
                            .observe(requireActivity(), tutorial-> {
                                tutorials.add(tutorial);
                                mAdapter.setElementList(tutorials, helpers);
                            });
                }
            });
        });
        View view = inflater
                .inflate(R.layout.fragment_recycler_results, container, false);
        RecyclerView recycler = view.findViewById(R.id.results_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(Tutorial tutorial)
    {
        mCallback.serveTutorial(tutorial);
    }

    public interface CallbackForTutorial
    {
        void serveTutorial(Tutorial tutorial);
    }
}
