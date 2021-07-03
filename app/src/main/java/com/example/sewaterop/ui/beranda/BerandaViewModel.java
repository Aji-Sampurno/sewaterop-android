package com.example.sewaterop.ui.beranda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BerandaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BerandaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is beranda fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}