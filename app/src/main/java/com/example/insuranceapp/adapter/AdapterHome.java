package com.example.insuranceapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuranceapp.HomeActivity;
import com.example.insuranceapp.MainMenuActivity;
import com.example.insuranceapp.R;
import com.example.insuranceapp.model.ModelAsuransi;
import com.example.insuranceapp.model.ModelAsuransiUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder>{
    private List<ModelAsuransi> mlist;
    private Activity activity;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AdapterHome(List<ModelAsuransi>mlist, Activity activity){
        this.mlist = mlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_list_asuransi, parent, false);
        return new AdapterHome.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHome.MyViewHolder holder, int position) {
        final ModelAsuransi dataAsuransi = mlist.get(position);
        holder.tvNama.setText(dataAsuransi.getNama());
        holder.tvHarga.setText("Rp. "+dataAsuransi.getHarga()+"/bulan");
        holder.tvDesc.setText(dataAsuransi.getDesciption());
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String nama = dataAsuransi.getNama();
        String harga = dataAsuransi.getHarga();
        String desc = dataAsuransi.getDesciption();
        String status = "Pending";
        holder.cardHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child("AsuransiUser").child(uid).setValue(new ModelAsuransiUser(email,nama,desc,harga,status)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Pembelian berhasil, akan segera diproses", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage("Apakah anda ingin membeli asuransi ");
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvDesc,tvHarga;
        CardView cardHasil;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNamaUser);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            cardHasil = itemView.findViewById(R.id.card_hasil_user);

        }
    }
}
