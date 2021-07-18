package com.sewaterop.ui.beranda;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sewaterop.LoginActivity;
import com.sewaterop.R;
import com.sewaterop.SewaBaruActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sewaterop.adapter.AdapterData;
import com.sewaterop.model.ModelData;
import com.sewaterop.util.Preferences;
import com.sewaterop.util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BerandaFragment extends Fragment {

    FloatingActionButton bt;
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    SwipeRefreshLayout refreshLayout;
    List<ModelData> mItems;
    ProgressDialog pd;

    private BerandaViewModel berandaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        berandaViewModel = new ViewModelProvider(this).get(BerandaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_beranda, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        berandaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mRecyclerview = root.findViewById(R.id.recyclerviewTemp);
        bt = root.findViewById(R.id.sewabaru);
        refreshLayout = root.findViewById(R.id.swiperefresh);
        pd = new ProgressDialog(getActivity());
        mItems = new ArrayList<>();

        loadJson(true);

        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterData(getActivity(),mItems);
        mRecyclerview.setAdapter(mAdapter);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),SewaBaruActivity.class);
                startActivity(in);
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJson(false);
            }
        });
        return root;
    }

    private void loadJson(boolean showProgressDialog)
    {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        if (showProgressDialog) pd.show();
        else pd.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerAPI.URL_DATA + Preferences.getKeyIdPemesan(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) pd.cancel();
                        else refreshLayout.setRefreshing(false);
                        mItems.clear();
//                        System.out.println("response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setIdPesanan(data.getString("id_pesanan"));
                                md.setTglMulai(data.getString("tgl_mulai"));
                                md.setWaktuMulai(data.getString("waktu_mulai"));
                                md.setTglSelesai(data.getString("tgl_selesai"));
                                md.setWaktuSelesai(data.getString("waktu_selesai"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (showProgressDialog) pd.cancel();
                        else refreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

       queue.add(reqData);
    }

}