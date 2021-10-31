package com.orzechowski.aidme.helper.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class HelperSessionViewModel extends AndroidViewModel
{
    private final MutableLiveData<HelperSession> mHelperSession = new MutableLiveData<>();

    public HelperSessionViewModel(@NonNull Application application)
    {
        super(application);
    }

    public LiveData<HelperSession> getHelperSession()
    {
        return mHelperSession;
    }

    public void setHelperSession(HelperSession helperSession)
    {
        mHelperSession.setValue(helperSession);
    }

    public void setHelping(Boolean helping)
    {
        mHelperSession.setValue(new HelperSession(helping,
                Objects.requireNonNull(mHelperSession.getValue()).getVerified()));
    }
}
