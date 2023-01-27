package com.example.insuranceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.insuranceapp.admin.MainActivity;
import com.example.insuranceapp.model.ModelAsuransi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahActivity extends AppCompatActivity {
    EditText nama, harga, deskripsi;
    Button simpan;
    String key;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        nama = findViewById(R.id.edNama);
        harga = findViewById(R.id.edHarga);
        deskripsi = findViewById(R.id.edDescription);
        simpan = findViewById(R.id.btnSimpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getNama = nama.getText().toString();
                String getHarga = harga.getText().toString();
                String getDesc = deskripsi.getText().toString();

                if (getNama.isEmpty()) {
                    nama.setError("Masukan nama.... ");
                } else if (getDesc.isEmpty()) {
                    deskripsi.setError("Masukan Deskripsi... ");
                } else if (getHarga.isEmpty()) {
                    harga.setError("Masukan Jenis... ");
                } else {
                    databaseReference.child("Asuransi").push().setValue(new ModelAsuransi(getNama, getDesc, getHarga))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(TambahActivity.this, "Data berhasil disimpan !", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(TambahActivity.this, "Data gagal disimpan !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}