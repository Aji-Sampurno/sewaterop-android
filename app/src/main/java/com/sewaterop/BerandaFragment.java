package com.sewaterop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BerandaFragment extends Fragment {


    public BerandaFragment() {
        // Required empty public constructor
    }

    public static BerandaFragment newInstance() {

        Bundle args = new Bundle();

        BerandaFragment fragment = new BerandaFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false);
    }

}