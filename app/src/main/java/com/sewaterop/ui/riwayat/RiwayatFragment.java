package com.sewaterop.ui.riwayat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.sewaterop.R;
import com.sewaterop.adapter.AdapterDataRiwayat;
import com.sewaterop.model.ModelDataRiwayat;
import com.sewaterop.util.Preferences;
import com.sewaterop.util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RiwayatFragment extends Fragment {

    private RiwayatViewModel riwayatViewModel;
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    SwipeRefreshLayout refreshLayout;
    List<ModelDataRiwayat> mItems;
    ProgressDialog pd;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        riwayatViewModel = new ViewModelProvider(this).get(RiwayatViewModel.class);
        View root = inflater.inflate(R.layout.fragment_riwayat, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        //riwayatViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            //@Override
            //public void onChanged(@Nullable String s) {
                //textView.setText(s);
            //}
        //});
        mRecyclerview = root.findViewById(R.id.recyclerviewRiwayat);
        refreshLayout = root.findViewById(R.id.swiperefresh1);
        pd = new ProgressDialog(getActivity());
        mItems = new ArrayList<>();

        loadJson(true);

        mManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterDataRiwayat(getActivity(),mItems);
        mRecyclerview.setAdapter(mAdapter);

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
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerAPI.URL_RIWAYAT + Preferences.getKeyIdPemesan(getActivity()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) pd.cancel();
                        else refreshLayout.setRefreshing(false);
                        System.out.println("response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                mItems.clear();
                                JSONObject data = response.getJSONObject(i);
                                ModelDataRiwayat md = new ModelDataRiwayat();
                                md.setIdPesanan(data.getString("id_pesanan"));
                                md.setTglMulai(data.getString("tgl_mulai"));
                                md.setWaktuMulai(data.getString("waktu_mulai"));
                                md.setTglSelesai(data.getString("tgl_selesai"));
                                md.setWaktuSelesai(data.getString("waktu_selesai"));
                                md.setStatusPembayaran(data.getString("status_pembayaran"));
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