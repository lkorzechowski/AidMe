package com.orzechowski.prototyp.poradnikrecycler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.poradnikrecycler.objects.Instrukcja;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment implements ListAdapter.ClickedTitleListener {
    protected RecyclerView recycler;
    protected ListAdapter adapter;
    List<Instrukcja> mInstructionsList = new LinkedList<>();

    public Recycler() {
        super(R.layout.fragment_recycler_main);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        String[] titles = getArguments().getStringArray("title");
        String[] instructions = getArguments().getStringArray("instructions");
        int[] version = getArguments().getIntArray("version");
        int[] duration = getArguments().getIntArray("duration");
        for (int i = 0; i < version.length; i++) {
            mInstructionsList.add(new Instrukcja(titles[version[i]], instructions[version[i]], duration[i]));
        }
        adapter = new ListAdapter(requireActivity(), mInstructionsList, this);
        View view = inflater.inflate(R.layout.fragment_recycler_poradnik, container, false);
        recycler = view.findViewById(R.id.poradniki_rv);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onTitleClicked(int position) {

    }
}