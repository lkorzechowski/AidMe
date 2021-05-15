package com.orzechowski.prototyp.poradnikrecycler;

import androidx.fragment.app.Fragment;
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

public class Recycler extends Fragment {
    protected RecyclerView recycler;
    protected ListAdapter adapter;
    List<Instrukcja> lista = new LinkedList<>();

    public Recycler() {
        super(R.layout.fragment_recycler_main);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        String[] tytuly = getArguments().getStringArray("tytuly");
        String[] instrukcje = getArguments().getStringArray("instrukcje");
        for (int i = 0; i < tytuly.length; i++) {
            lista.add(new Instrukcja(tytuly[i], instrukcje[i]));
        }
        adapter = new ListAdapter(requireActivity(), lista, (ListAdapter.WybranoTytul)requireActivity());
        View view = inflater.inflate(R.layout.fragment_recycler_poradnik, container, false);
        recycler = view.findViewById(R.id.poradniki_rv);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}