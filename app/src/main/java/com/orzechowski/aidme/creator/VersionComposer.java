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
import com.orzechowski.aidme.tutorial.version.database.Version;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class VersionComposer extends Fragment
{
    private VersionComposerAdapter mAdapter;
    private final List<Version> versionList = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new VersionComposerAdapter(activity);
        View view = inflater.inflate(R.layout.fragment_version_composer, container, false);
        RecyclerView recycler = view.findViewById(R.id.new_version_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button addVersionButton = view.findViewById(R.id.new_version_button);
        addVersionButton.setOnClickListener(v-> {
            versionList.add(new Version(0, "", 0, true, null, false, false, null));
            mAdapter.setElementList(versionList);
        });
    }
}
