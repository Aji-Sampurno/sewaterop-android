package com.example.sewaterop.sewaan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sewaterop.R;

public class PaketSewa extends Fragment {

    View view;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_paket_sewa, container, false);
        return view;
    }
}