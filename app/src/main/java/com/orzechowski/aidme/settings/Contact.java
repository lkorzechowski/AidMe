package com.orzechowski.aidme.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orzechowski.aidme.R;

import org.jetbrains.annotations.NotNull;

public class Contact extends Fragment
{
    private final OnClickListener mListener;

    public Contact(OnClickListener listener){
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState)
    {
        Button contactFormButton = view.findViewById(R.id.contact_form_button);
        contactFormButton.setOnClickListener(v -> mListener.onClick());
    }

    public boolean onBackPressed()
    {
        return true;
    }

    public interface OnClickListener
    {
        void onClick();
    }
}
