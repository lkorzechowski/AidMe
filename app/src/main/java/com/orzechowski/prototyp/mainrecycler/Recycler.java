package com.orzechowski.prototyp.mainrecycler;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.mainrecycler.objects.NumerAlarmowy;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment {
    protected RecyclerView recycler;
    protected ListAdapter adapter;

    public Recycler() {
        super(R.layout.fragment_recycler_main);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
        ) {
            List<NumerAlarmowy> numbersList = new LinkedList<>();
            String[] serviceNames = getResources().getStringArray(R.array.numeralarmowy_uslugi);
            int[] phoneNumbers = getResources().getIntArray(R.array.numeralarmowy_numery);
            for(int i = 0; i < serviceNames.length; i++){
                numbersList.add(new NumerAlarmowy(phoneNumbers[i], serviceNames[i]));
            }
            adapter = new ListAdapter(requireActivity(), numbersList);
            View view = inflater.inflate(R.layout.fragment_recycler_main, container, false);
            recycler = view.findViewById(R.id.numery_rv);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
            return view;
        }
}