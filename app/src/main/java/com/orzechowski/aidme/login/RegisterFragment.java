package com.orzechowski.aidme.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.orzechowski.aidme.R;

public class RegisterFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public boolean onBackPressed()
    {
        return true;
    }
}
