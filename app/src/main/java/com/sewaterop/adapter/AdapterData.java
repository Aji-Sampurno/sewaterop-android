package com.sewaterop.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sewaterop.DetailSewaan;
import com.sewaterop.SewaBaruActivity;
import com.sewaterop.model.ModelData;
import com.sewaterop.R;

import java.util.List;

//digunakan untuk membuat public class bernama AdapterData dan merupakan extend dari class RecyclerView.Adapter<AdapterData.HolderData>
public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private List<ModelData> mItems ;
    private Context context;

    public AdapterData (Context context, List<ModelData> items)
    {
        this.mItems = items;
        this.context = context;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        ModelData md  = mItems.get(position);
        holder.tvidpesanan.setText(md.getIdPesanan());
        holder.tvmulai.setText("Dimulai pada " + md.getTglMulai() + " " + md.getWaktuMulai());
        holder.tvberakhir.setText("Berakhir pada " + md.getTglSelesai() + " " + md.getWaktuSelesai());


        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvidpesanan,tvmulai,tvberakhir;
        ModelData md;

        public  HolderData (View view)
        {
            super(view);

            tvidpesanan = (TextView) view.findViewById(R.id.idpesanan);
            tvmulai = (TextView) view.findViewById(R.id.tglmulai);
            tvberakhir = (TextView) view.findViewById(R.id.tglberakhir);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, DetailSewaan.class);
                    update.putExtra("update",1);
                    update.putExtra("idpesanan",md.getIdPesanan());

                    context.startActivity(update);
                }
            });
        }
    }
}