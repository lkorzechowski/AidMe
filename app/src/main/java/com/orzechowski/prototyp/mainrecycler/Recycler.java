package com.orzechowski.prototyp.mainrecycler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.objects.NumerAlarmowy;

import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment {
    protected RecyclerView recycler;
    protected ListAdapter adapter;

    public Recycler() {
        super(R.layout.fragment_recycler);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
        ) {
            List<NumerAlarmowy> numery = new LinkedList<>();
            String[] uslugi = getResources().getStringArray(R.array.numeralarmowy_uslugi);
            int[] telefony = getResources().getIntArray(R.array.numeralarmowy_numery);
            for(int i = 0; i < uslugi.length; i++){
                numery.add(new NumerAlarmowy(telefony[i], uslugi[i]));
            }
            adapter = new ListAdapter(getActivity(), numery);
            View view = inflater.inflate(R.layout.fragment_recycler, container, false);
            recycler = view.findViewById(R.id.numery_rv);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
            return view;
        }
}