package com.example.insuranceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.insuranceapp.adapter.AdapterAsuransi;
import com.example.insuranceapp.adapter.AdapterHome;
import com.example.insuranceapp.model.ModelAsuransi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton btnHome;
    AdapterHome adapterHome;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<ModelAsuransi> modelAsuransis;
    RecyclerView tvTampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btnHome = findViewById(R.id.btnMainMenu);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            }
        });

        tvTampil = findViewById(R.id.tvTampilUser);
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

                adapterHome = new AdapterHome(modelAsuransis, HomeActivity.this);
                tvTampil.setAdapter(adapterHome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}