package com.example.insuranceapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.insuranceapp.R;
import com.example.insuranceapp.TambahActivity;
import com.example.insuranceapp.adapter.AdapterAsuransi;
import com.example.insuranceapp.model.ModelAsuransi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton tambah;
    AdapterAsuransi adapterAsuransi;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<ModelAsuransi> modelAsuransis;
    RecyclerView tvTampil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tambah = findViewById(R.id.btn_tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TambahActivity.class));
            }
        });
        tvTampil = findViewById(R.id.tvTampil);
        RecyclerView.LayoutManager mlayout = new LinearLayoutManager(this);
        tvTampil.setLayoutManager(mlayout);
        tvTampil.setItemAnimator(new DefaultItemAnimator());
        
        tampilData();
    }

    private void tampilData() {
        databaseReference.child("Asuransi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelAsuransis = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    ModelAsuransi asr = item.getValue(ModelAsuransi.class);
                    asr.setKey(item.getKey());
                    modelAsuransis.add(asr);
                }

                adapterAsuransi = new AdapterAsuransi(modelAsuransis, MainActivity.this);
                tvTampil.setAdapter(adapterAsuransi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}