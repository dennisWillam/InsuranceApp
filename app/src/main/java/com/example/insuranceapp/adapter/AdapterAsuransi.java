package com.example.insuranceapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuranceapp.dialog.DialogFrom;
import com.example.insuranceapp.R;
import com.example.insuranceapp.model.ModelAsuransi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterAsuransi extends RecyclerView.Adapter<AdapterAsuransi.MyViewHolder> {
    private List<ModelAsuransi> mlist;
    private Activity activity;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AdapterAsuransi(List<ModelAsuransi>mlist, Activity activity){
        this.mlist = mlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ModelAsuransi dataAsuransi = mlist.get(position);
        holder.tvNama.setText(dataAsuransi.getNama());
        holder.tvJenis.setText(dataAsuransi.getDesciption());
        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child("Asuransi").child(dataAsuransi.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah yakin mau menghapus? "+dataAsuransi.getNama());
                builder.show();
            }
        });

        holder.cardHasil.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogFrom dialog = new DialogFrom(
                        dataAsuransi.getNama(),
                        dataAsuransi.getHarga(),
                        dataAsuransi.getDesciption(),
                        dataAsuransi.getKey(),
                        "Ubah"
                );
                dialog.show(manager, "form");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama,tvJenis;
        ImageView btnHapus;
        CardView cardHasil;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvJenis = itemView.findViewById(R.id.tvJenis);
            cardHasil = itemView.findViewById(R.id.card_hasil);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}
