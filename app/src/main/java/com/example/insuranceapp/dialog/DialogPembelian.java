package com.example.insuranceapp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.insuranceapp.R;
import com.example.insuranceapp.model.ModelAsuransiUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogPembelian extends DialogFragment {
    String nama, email, harga, status, key, pilih;
    TextView tNama, tEmail;
    Spinner spStatus;
    Button simpan;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogPembelian(String nama, String email, String harga, String status, String key, String pilih) {
        this.nama = nama;
        this.email = email;
        this.harga = harga;
        this.status = status;
        this.key = key;
        this.pilih = pilih;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_pembelian, container, false);
        tNama = view.findViewById(R.id.tvNamaForm);
        tEmail = view.findViewById(R.id.tvEmailForm);
        spStatus = view.findViewById(R.id.spinnerStatus);
        simpan = view.findViewById(R.id.btnSimpanForm);

        tNama.setText(nama);
        tEmail.setText(email);
        spStatus.setSelected(true);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fStatus = spStatus.getSelectedItem().toString();
                if (pilih.equals("Ubah")){
                    databaseReference.child("AsuransiUser").child(key).child("status").setValue(fStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
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
