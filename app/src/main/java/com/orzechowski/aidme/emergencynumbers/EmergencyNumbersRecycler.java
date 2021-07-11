package com.orzechowski.aidme.emergencynumbers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.aidme.R;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class EmergencyNumbersRecycler extends Fragment
        implements EmergencyNumbersListAdapter.OnViewClickListener
{
    protected RecyclerView recycler;
    protected EmergencyNumbersListAdapter adapter;
    private ActivityResultLauncher<String> mPermissionResult;
    private Intent mPhoneIntent;

    public EmergencyNumbersRecycler() {
        super(R.layout.fragment_recycler_main);
    }

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        mPermissionResult = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                result -> {
                    if(result)startActivity(mPhoneIntent);
                });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        List<EmergencyNumber> numbersList = new LinkedList<>();
        Resources resources = getResources();
        String[] serviceNames = resources.getStringArray(R.array.services);
        int[] phoneNumbers = resources.getIntArray(R.array.main_phone_numbers);
        for(int i = 0; i < serviceNames.length; i++) {
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
    public void onViewClick(int position)
    {
        mPhoneIntent = new Intent(Intent.ACTION_CALL);
        mPhoneIntent.setData(Uri.parse("tel:" + 123));

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            mPermissionResult.launch(Manifest.permission.CALL_PHONE);
        } else {
            startActivity(mPhoneIntent);
        }
    }
}
