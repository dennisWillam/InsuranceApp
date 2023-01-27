package com.example.insuranceapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuranceapp.R;
import com.example.insuranceapp.dialog.DialogPembelian;
import com.example.insuranceapp.dialog.DialogRembuse;
import com.example.insuranceapp.model.ModelRembuse;

import java.util.List;

public class AdapterRembuseAdmin extends RecyclerView.Adapter<AdapterRembuseAdmin.MyViewHolder> {
    private List<ModelRembuse> mlist;
    private Activity activity;

    public AdapterRembuseAdmin(List<ModelRembuse> mlist, Activity activity) {
        this.mlist = mlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterRembuseAdmin.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_list_rembuse, parent, false);
        return new AdapterRembuseAdmin.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRembuseAdmin.MyViewHolder holder, int position) {
        final ModelRembuse dataRembuse = mlist.get(position);
        holder.tvJenis.setText(dataRembuse.getNama());
        holder.tvTanggal.setText(dataRembuse.getTanggal());
        holder.tvBayar.setText("Rp. " + dataRembuse.getTotalBayar());
        holder.tvStatus.setText(dataRembuse.getStatus());
        holder.tvDokter.setText(dataRembuse.getNamaDokter());
        holder.tvTempat.setText(dataRembuse.getTempat());

        holder.cardRembuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogRembuse dialog = new DialogRembuse(
                        dataRembuse.getEmail(),
                        dataRembuse.getNama(),
                        dataRembuse.getTotalBayar(),
                        dataRembuse.getStatus(),
                        dataRembuse.getUrl(),
                        dataRembuse.getKey(),
                        dataRembuse.getUid(),
                        "Ubah"
                );
                dialog.show(manager, "form");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvJenis, tvTanggal, tvBayar, tvStatus, tvDokter, tvTempat;
        CardView cardRembuse;

        public MyViewHolder(View viewItem) {
            super(viewItem);
            tvJenis = viewItem.findViewById(R.id.tvJenisKlaim);
            tvTanggal = viewItem.findViewById(R.id.tvTanggalKlaim);
            tvBayar = viewItem.findViewById(R.id.tvTotalKlaim);
            tvStatus = viewItem.findViewById(R.id.tvStatusKlaim);
            tvDokter = viewItem.findViewById(R.id.tvDokterKlaim);
            tvTempat = viewItem.findViewById(R.id.tvTempatKlaim);
            cardRembuse = viewItem.findViewById(R.id.cardKlaim);

        }
    }
}