package com.example.insuranceapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuranceapp.dialog.DialogPembelian;
import com.example.insuranceapp.R;
import com.example.insuranceapp.model.ModelAsuransiUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterPembelian extends RecyclerView.Adapter<AdapterPembelian.MyViewHolder>{
    private List<ModelAsuransiUser> mlist;
    private Activity activity;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AdapterPembelian(List<ModelAsuransiUser> mlist, Activity activity) {
        this.mlist = mlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterPembelian.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_list_pembelian, parent, false);
        return new AdapterPembelian.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPembelian.MyViewHolder holder, int position) {
        final ModelAsuransiUser dataAsuransi = mlist.get(position);
        holder.tvNama.setText("Nama Asuransi : "+dataAsuransi.getNama());
        holder.tvEmail.setText("Email : "+dataAsuransi.getEmail());
        holder.tvStatus.setText("Status : "+dataAsuransi.getStatus());
        holder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogPembelian dialog = new DialogPembelian(
                        dataAsuransi.getNama(),
                        dataAsuransi.getEmail(),
                        dataAsuransi.getHarga(),
                        dataAsuransi.getStatus(),
                        dataAsuransi.getKey(),
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
        TextView tvNama, tvEmail, tvStatus;
        CardView items;
        Spinner spStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaPembelian);
            tvEmail = itemView.findViewById(R.id.tvEmailPembelian);
            tvStatus = itemView.findViewById(R.id.tvStatusPembelian);
            items = itemView.findViewById(R.id.card_hasil_pembelian);
            spStatus = itemView.findViewById(R.id.spinnerStatus);
        }
    }
}
