package com.orzechowski.saveme.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.orzechowski.saveme.R;

public class Policy extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_policy, container, false);
    }

    public boolean onBackPressed()
    {
        return true;
    }
}
