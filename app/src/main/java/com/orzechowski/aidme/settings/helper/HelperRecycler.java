package com.orzechowski.aidme.settings.helper;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orzechowski.aidme.R;
import com.orzechowski.aidme.settings.helper.database.Helper;
import com.orzechowski.aidme.settings.helper.database.HelperViewModel;

public class HelperRecycler extends Fragment implements HelperListAdapter.OnClickListener
{
    private HelperListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        FragmentActivity activity = requireActivity();
        mAdapter = new HelperListAdapter(activity, this);
        HelperViewModel helperViewModel = new ViewModelProvider(this).get(HelperViewModel.class);
        helperViewModel.getAll().observe(activity, helpers->mAdapter.setElementList(helpers));
        View view = inflater.inflate(R.layout.fragment_recycler_helper, container, false);
        RecyclerView recycler = view.findViewById(R.id.helper_rv);
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onClick(Helper helper)
    {

    }
}