package com.example.insuranceapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.insuranceapp.R;
import com.example.insuranceapp.adapter.AdapterHome;
import com.example.insuranceapp.adapter.AdapterRembuse;
import com.example.insuranceapp.adapter.AdapterRembuseAdmin;
import com.example.insuranceapp.fragment.FragmentRembuse;
import com.example.insuranceapp.model.ModelAsuransi;
import com.example.insuranceapp.model.ModelRembuse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RembuseAdminActivity extends AppCompatActivity {
    AdapterRembuseAdmin adapterRembuseAdmin;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<ModelRembuse> modelRembuses;
    RecyclerView tvTampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rembuse_admin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tvTampil = findViewById(R.id.rcRembuseAdmin);
        RecyclerView.LayoutManager mlayout = new LinearLayoutManager(this);
        tvTampil.setLayoutManager(mlayout);
        tvTampil.setItemAnimator(new DefaultItemAnimator());
        tampilData();
    }

    private void tampilData() {
        databaseReference.child("Rembuse").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelRembuses = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {

                    for (DataSnapshot rembuse : item.getChildren()) {
                        ModelRembuse data = rembuse.getValue(ModelRembuse.class);
                        data.setUid(item.getKey());
                        data.setKey(rembuse.getKey());
                        modelRembuses.add(data);
                    }
                }

                adapterRembuseAdmin = new AdapterRembuseAdmin(modelRembuses, RembuseAdminActivity.this );
                tvTampil.setAdapter(adapterRembuseAdmin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}