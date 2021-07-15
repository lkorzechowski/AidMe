package com.orzechowski.aidme.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.database.helper.Helper;
import com.orzechowski.aidme.database.helper.HelperViewModel;
import com.orzechowski.aidme.database.tag.HelperTag;
import com.orzechowski.aidme.database.tag.HelperTagViewModel;
import com.orzechowski.aidme.database.tag.TutorialTag;
import com.orzechowski.aidme.database.tag.TutorialTagViewModel;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class ResultsRecycler extends Fragment implements ResultsListAdapter.OnClickListener
{
    private ResultsListAdapter mAdapter;
    public CallbackForTutorial mCallbackForTutorial;

    public ResultsRecycler(CallbackForTutorial callback)
    {
        mCallbackForTutorial = callback;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        long tagId = requireArguments().getLong("tagId");
        TutorialTagViewModel tutorialTagViewModel = new ViewModelProvider(this).get(TutorialTagViewModel.class);
        TutorialViewModel tutorialViewModel = new ViewModelProvider(this).get(TutorialViewModel.class);
        HelperTagViewModel helperTagViewModel = new ViewModelProvider(this).get(HelperTagViewModel.class);
        HelperViewModel helperViewModel = new ViewModelProvider(this).get(HelperViewModel.class);
        mAdapter = new ResultsListAdapter(requireActivity(), this);
        tutorialTagViewModel.getByTagId(tagId).observe(requireActivity(), tutorialTags-> {
            List<Tutorial> tutorials = new LinkedList<>();
            List<Helper> helpers = new LinkedList<>();
            helperTagViewModel.getByTagId(tagId).observe(requireActivity(), helperTags-> {
                for(TutorialTag tutorialTag : tutorialTags) {
                    tutorialViewModel.getByTutorialId(tutorialTag.getTutorialId()).observe(requireActivity(), tutorials::add);
                }
                for(HelperTag helperTag : helperTags) {
                    helperViewModel.getById(helperTag.getHelperId()).observe(requireActivity(), helpers::add);
                }
                helperViewModel.getById(1L).observe(requireActivity(), helpers::add);
                //programatically adding myself here, because tags are also used for suggesting numbers
                //to call for help, I ought not to have any, as I am not a medical professional.
                mAdapter.setElementList(tutorials, helpers);
            });
        });
        View view = inflater.inflate(R.layout.fragment_recycler_results, container, false);
        RecyclerView recycler = view.findViewById(R.id.results_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(Tutorial tutorial)
    {
        mCallbackForTutorial.serveTutorial(tutorial);
    }

    public interface CallbackForTutorial
    {
        void serveTutorial(Tutorial tutorial);
    }
}
