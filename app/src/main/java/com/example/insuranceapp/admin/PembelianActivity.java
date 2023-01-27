package com.example.insuranceapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.insuranceapp.R;

import com.example.insuranceapp.adapter.AdapterPembelian;

import com.example.insuranceapp.model.ModelAsuransiUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PembelianActivity extends AppCompatActivity {
    AdapterPembelian adapterPembelian;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<ModelAsuransiUser> modelAsuransis;
    RecyclerView tvTampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);

        tvTampil = findViewById(R.id.tvTampilPembelian);
        RecyclerView.LayoutManager mlayout = new LinearLayoutManager(this);
        tvTampil.setLayoutManager(mlayout);
        tvTampil.setItemAnimator(new DefaultItemAnimator());
        tampilData();
    }

    private void tampilData() {
        databaseReference.child("AsuransiUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelAsuransis = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    ModelAsuransiUser asr = item.getValue(ModelAsuransiUser.class);
                    asr.setKey(item.getKey());
                    modelAsuransis.add(asr);
                }

                adapterPembelian = new AdapterPembelian(modelAsuransis, PembelianActivity.this);
                tvTampil.setAdapter(adapterPembelian);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}