package com.example.sewaterop.ui.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sewaterop.LoginActivity;
import com.example.sewaterop.R;

public class UserFragment extends Fragment {

    private Button Logout;
    private UserViewModel userViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        //final TextView textView = root.findViewById(R.id.text_notifications);
        //userViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
           // @Override
            //public void onChanged(@Nullable String s) {
                //textView.setText(s);
            //}
        //});
        Logout = root.findViewById(R.id.btnlogout);

        Intent in = getActivity().getIntent();
        String string = in.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Konfirmasi Logout").
                        setMessage("apakah kamu yakin untuk logout?");
                builder.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                startActivity(i);
                            }
                        });
                builder.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        });
        return root;
    }


}