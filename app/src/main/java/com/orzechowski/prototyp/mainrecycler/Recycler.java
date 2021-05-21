package com.orzechowski.prototyp.mainrecycler;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.orzechowski.prototyp.R;
import com.orzechowski.prototyp.mainrecycler.objects.EmergencyNumber;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class Recycler extends Fragment implements ListAdapter.OnViewClickListener{
    protected RecyclerView recycler;
    protected ListAdapter adapter;

    public Recycler() {
        super(R.layout.fragment_recycler_main);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
            List<EmergencyNumber> numbersList = new LinkedList<>();
            String[] serviceNames = getResources().getStringArray(R.array.numeralarmowy_uslugi);
            int[] phoneNumbers = getResources().getIntArray(R.array.numeralarmowy_numery);
            for(int i = 0; i < serviceNames.length; i++){
                numbersList.add(new EmergencyNumber(phoneNumbers[i], serviceNames[i]));
            }
            adapter = new ListAdapter(requireActivity(), numbersList, this);
            View view = inflater.inflate(R.layout.fragment_recycler_main, container, false);
            recycler = view.findViewById(R.id.numery_rv);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
            return view;
    }

    @Override
    public void onViewClick(int position) {
        Intent phone_intent = new Intent(Intent.ACTION_CALL);
        phone_intent.setData(Uri.parse("tel:" + 123));
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            startActivity(phone_intent);
        }
    }
}