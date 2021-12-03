package com.orzechowski.saveme.creator.initial.versioncomposer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.version.database.Version;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class VersionComposer extends Fragment implements VersionComposerAdapter.FragmentCallback
{
    private VersionComposerAdapter mAdapter;
    private final List<Version> mVersions = new LinkedList<>();

    public List<Version> getVersions()
    {
        return mVersions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new VersionComposerAdapter(activity, this);
        View view = inflater.inflate(R.layout.fragment_version_composer, container,
                false);
        RecyclerView recycler = view.findViewById(R.id.new_version_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        if(!mVersions.isEmpty()) mAdapter.setElementList(mVersions);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addVersionButton = view.findViewById(R.id.new_version_button);
        addVersionButton.setOnClickListener(v -> {
            mVersions.add(new Version(mVersions.size(), "", 0, true,
                    false, false, null));
            mAdapter.setElementList(mVersions);
        });
    }

    @Override
    public void delete(Version versionToDelete)
    {
        mVersions.remove(versionToDelete);
        for(int i = (int) versionToDelete.getVersionId(); i < mVersions.size(); i++)
        {
            Version version = mVersions.get(i);
            mVersions.remove(version);
            mVersions.add(new Version(i, version.getText(), 0, version
                    .getDelayGlobalSound(), false, false, null));
        }
        mAdapter.setElementList(mVersions);
    }

    @Override
    public void modifyText(String text, Long versionId)
    {
        mVersions.get(versionId.intValue()).setText(text);
    }

    @Override
    public void modifyDelay(boolean delay, Long versionId)
    {
        mVersions.get(versionId.intValue()).setDelayGlobalSound(delay);
    }
}
