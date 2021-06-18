package com.orzechowski.aidme.emergencynumbers;

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
import com.orzechowski.aidme.R;

import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class EmergencyNumbersRecycler extends Fragment implements EmergencyNumbersListAdapter.OnViewClickListener{
    protected RecyclerView recycler;
    protected EmergencyNumbersListAdapter adapter;

    public EmergencyNumbersRecycler() {
        super(R.layout.fragment_recycler_main);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
            List<EmergencyNumber> numbersList = new LinkedList<>();
            String[] serviceNames = getResources().getStringArray(R.array.services);
            int[] phoneNumbers = getResources().getIntArray(R.array.main_phone_numbers);
            for(int i = 0; i < serviceNames.length; i++){
                numbersList.add(new EmergencyNumber(phoneNumbers[i], serviceNames[i]));
            }
            adapter = new EmergencyNumbersListAdapter(requireActivity(), numbersList, this);
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