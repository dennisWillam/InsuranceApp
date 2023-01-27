package com.example.insuranceapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.insuranceapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DialogRembuse extends DialogFragment {
    String email,nama,harga,status,url,key,uid,pilih;
    ImageView imgNota;
    TextView tEmail,tNama,tTotalBayar;
    Spinner spStatus;
    Button simpan;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogRembuse(String email, String nama, String harga, String status, String url, String key, String uid, String pilih) {
        this.email = email;
        this.nama = nama;
        this.harga = harga;
        this.status = status;
        this.url = url;
        this.key = key;
        this.uid = uid;
        this.pilih = pilih;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_rembuse, container, false);
        tNama = view.findViewById(R.id.tvNamaRembuse);
        tEmail = view.findViewById(R.id.tvEmailRembuse);
        tTotalBayar = view.findViewById(R.id.tvJumlahRembuse);
        spStatus = view.findViewById(R.id.spinnerStatusRembuse);
        imgNota = view.findViewById(R.id.imagetNota);
        simpan = view.findViewById(R.id.btnSimpanRembuse);

        tNama.setText(nama);
        tEmail.setText(email);
        tTotalBayar.setText(harga);
        Picasso.get().load(url)
                .fit().into(imgNota);
        spStatus.setSelected(true);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fStatus = spStatus.getSelectedItem().toString();
                if (pilih.equals("Ubah")){
                    databaseReference.child("Rembuse").child(uid).child(key).child("status").setValue(fStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Data berhasil di ubah", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
