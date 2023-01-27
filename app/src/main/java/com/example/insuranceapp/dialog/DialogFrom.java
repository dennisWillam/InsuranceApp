package com.example.insuranceapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.insuranceapp.R;
import com.example.insuranceapp.model.ModelAsuransi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogFrom extends DialogFragment {
    String nama, jenis, deskripsi, key, pilih;
    TextView tNama, tJenis, tDesc;
    Button btnSimpan;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogFrom(String nama, String jenis, String deskripsi, String key, String pilih) {
        this.nama = nama;
        this.jenis = jenis;
        this.deskripsi = deskripsi;
        this.key = key;
        this.pilih = pilih;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_tambah, container, false);
        tNama = view.findViewById(R.id.edNama);
        tJenis = view.findViewById(R.id.edHarga);
        tDesc = view.findViewById(R.id.edDescription);
        btnSimpan = view.findViewById(R.id.btnSimpan);
        btnSimpan.setText("Simpan");

        tNama.setText(nama);
        tJenis.setText(jenis);
        tDesc.setText(deskripsi);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fNama = tNama.getText().toString();
                String fJenis = tJenis.getText().toString();
                String fDesc = tDesc.getText().toString();
                if (pilih.equals("Ubah")){
                    databaseReference.child("Asuransi").child(key).setValue(new ModelAsuransi(fNama,fDesc,fJenis)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
