package com.orzechowski.prototyp.versionrecycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.versionrecycler.database.objects.VersionPrompt;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment implements ListAdapter.OnViewClickListener{
    protected RecyclerView recycler;
    protected ListAdapter adapter;

    public Recycler() {
        super(R.layout.fragment_recycler_version);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        List<VersionPrompt> versionsList = new LinkedList<>();
        for(int i = 0; i < ...length; i++){
            versionsList.add(new VersionPrompt());
        }
        adapter = new ListAdapter(requireActivity(), versionsList, this);
        View view = inflater.inflate(R.layout.fragment_recycler_version, container, false);
        recycler = view.findViewById(R.id.poradniki_rv);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onViewClick(int position) {

    }
}