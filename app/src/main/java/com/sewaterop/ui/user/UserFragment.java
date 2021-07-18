package com.sewaterop.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sewaterop.LoginActivity;
import com.sewaterop.R;
import com.sewaterop.util.Preferences;

public class UserFragment extends Fragment {

    private Button Logout;
    private UserViewModel userViewModel;
    private TextView tvnama, tvuser, tvalamat, tvtelepon;

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

        tvnama = root.findViewById(R.id.tvnamalengkap);
        tvuser = root.findViewById(R.id.tvusername);
        tvalamat = root.findViewById(R.id.alamat);
        tvtelepon = root.findViewById(R.id.telepon);

        tvnama.setText(Preferences.getKeyNamaPemesan(getActivity()));
        tvuser.setText(Preferences.getRegisteredUser(getActivity()));
        tvalamat.setText(Preferences.getKeyAlamat(getActivity()));
        tvtelepon.setText(Preferences.getKeyTelepon(getActivity()));

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
                                Preferences.clearLoggedInUser(getActivity());
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