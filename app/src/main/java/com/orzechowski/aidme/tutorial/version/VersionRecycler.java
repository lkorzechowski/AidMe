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
import com.orzechowski.aidme.tutorial.version.database.Version;
import com.orzechowski.aidme.tutorial.version.database.VersionViewModel;

import org.jetbrains.annotations.NotNull;

public class VersionRecycler extends Fragment
{
    protected VersionListAdapter mAdapter;
    private VersionViewModel mVersionViewModel;
    private final ActivityCallback mCallback;

    public VersionRecycler(ActivityCallback callback){
        super(R.layout.fragment_recycler_versions);
        mCallback = callback;
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mVersionViewModel = new ViewModelProvider(this).get(VersionViewModel.class);
        long tutorialId = requireArguments().getLong("tutorialId");
        View view = inflater
                .inflate(R.layout.fragment_recycler_versions, container, false);
        mAdapter = new VersionListAdapter(requireActivity());
        RecyclerView recycler = view.findViewById(R.id.versions_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        mVersionViewModel.getBaseByTutorialId(tutorialId).observe(requireActivity(), versions-> {
            if(versions.size()>1) mAdapter.setElementList(versions);
            else if(!versions.isEmpty()) mCallback.defaultVersion(versions.get(0));
        });
        recycler.setAdapter(mAdapter);
        return view;
    }

    public void getChildVersions(Long versionId)
    {
        mVersionViewModel.getByParentVersionId(versionId).observe(requireActivity(),
                versions -> mAdapter.setElementList(versions));
    }

    public interface ActivityCallback
    {
        void defaultVersion(Version version);
    }
}
