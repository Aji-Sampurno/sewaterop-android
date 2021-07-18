package com.sewaterop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sewaterop.DetailRiwayat;
import com.sewaterop.R;
import com.sewaterop.model.ModelData;
import com.sewaterop.model.ModelDataRiwayat;

import java.util.List;

//digunakan untuk membuat public class bernama AdapterData dan merupakan extend dari class RecyclerView.Adapter<AdapterData.HolderData>
public class AdapterDataRiwayat extends RecyclerView.Adapter<AdapterDataRiwayat.HolderData>{
    private List<ModelDataRiwayat> mItems ;
    private Context context;

    public AdapterDataRiwayat(Context context, List<ModelDataRiwayat> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_riwayat,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelDataRiwayat md  = mItems.get(position);
        holder.tvidpesanan.setText(md.getIdPesanan());
        holder.tvmulai.setText("Dimulai pada " + md.getTglMulai() + " " + md.getWaktuMulai());
        holder.tvberakhir.setText("Berakhir pada " + md.getTglSelesai() + " " + md.getWaktuSelesai());
        holder.tvstatus.setText(md.getStatusPembayaran());

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvidpesanan,tvmulai,tvberakhir,tvstatus;
        ModelDataRiwayat md;

        public  HolderData (View view)
        {
            super(view);

            tvidpesanan = (TextView) view.findViewById(R.id.idpesanan);
            tvmulai = (TextView) view.findViewById(R.id.mulai);
            tvberakhir = (TextView) view.findViewById(R.id.berakhir);
            tvstatus = (TextView) view.findViewById(R.id.status);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailRiwayat.class);
                    update.putExtra("update",1);
                    update.putExtra("idpesanan",md.getIdPesanan());
                    update.putExtra("tglmulai",md.getTglMulai()+md.getWaktuMulai());
                    update.putExtra("tglselesai",md.getTglSelesai()+md.getWaktuSelesai());
                    update.putExtra("statuspembayaran",md.getStatusPembayaran());

                    context.startActivity(update);
                }
            });
        }
    }
}