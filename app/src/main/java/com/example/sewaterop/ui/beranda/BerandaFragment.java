package com.example.sewaterop.ui.beranda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sewaterop.R;
import com.example.sewaterop.SewaBaruActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BerandaFragment extends Fragment {

    FloatingActionButton bt;

    private BerandaViewModel berandaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        berandaViewModel = new ViewModelProvider(this).get(BerandaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_beranda, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        //berandaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            //@Override
            //public void onChanged(@Nullable String s) {
                //textView.setText(s);
            //}
        //});

        bt = root.findViewById(R.id.sewabaru);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),SewaBaruActivity.class);
                startActivity(in);
            }
        });
        return root;
    }
}