package com.orzechowski.aidme.browser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.database.helper.HelperViewModel;
import com.orzechowski.aidme.tutorial.database.Tutorial;
import com.orzechowski.aidme.tutorial.database.TutorialViewModel;

import org.jetbrains.annotations.NotNull;

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
        FragmentActivity activity = requireActivity();
        String tags = requireArguments().getString("tags");
        TutorialViewModel tutorialViewModel = new ViewModelProvider(this)
                .get(TutorialViewModel.class);
        HelperViewModel helperViewModel = new ViewModelProvider(this).get(HelperViewModel.class);
        mAdapter = new ResultsListAdapter(activity, this);
        String lastTag = tags.replaceAll("^.*?(\\w+)\\W*$", "$1");
        tutorialViewModel.getByTag(lastTag).observe(activity, results->
                helperViewModel.getByTags(lastTag)
                        .observe(getViewLifecycleOwner(),
                                authors -> mAdapter.setElementList(results, authors)));
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
