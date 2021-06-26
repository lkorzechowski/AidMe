package com.orzechowski.aidme.tutorial.version;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.orzechowski.aidme.R;
import com.orzechowski.aidme.tutorial.version.database.VersionViewModel;
import org.jetbrains.annotations.NotNull;

public class VersionRecycler extends Fragment
{
    protected RecyclerView mRecycler;
    protected VersionListAdapter mAdapter;

    public VersionRecycler(){
        super(R.layout.fragment_recycler_versions);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        VersionViewModel versionViewModel = new ViewModelProvider(this).get(VersionViewModel.class);
        long tutorialId = requireArguments().getLong("tutorialId");
        View view = inflater.inflate(R.layout.fragment_recycler_versions, container, false);
        mAdapter = new VersionListAdapter(requireActivity());
        mRecycler = view.findViewById(R.id.versions_rv);
        mRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        versionViewModel.getByTutorialId(tutorialId).observe(requireActivity(),
                versions-> mAdapter.setElementList(versions));
        mRecycler.setAdapter(mAdapter);
        return view;
    }
}
