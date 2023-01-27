package com.example.insuranceapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insuranceapp.R;
import com.example.insuranceapp.adapter.AdapterAsuransi;
import com.example.insuranceapp.adapter.AdapterRembuse;
import com.example.insuranceapp.admin.MainActivity;
import com.example.insuranceapp.model.ModelAsuransi;
import com.example.insuranceapp.model.ModelRembuse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentRembuse extends Fragment {
    AdapterRembuse adapterRembuse;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<ModelRembuse> modelRembuses;
    private static final String TAG = "RembuseFragment";

    public FragmentRembuse() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_rembuse, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rcRembuse);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Rembuse").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelRembuses = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    ModelRembuse asr = item.getValue(ModelRembuse.class);
                    modelRembuses.add(asr);
                }

                adapterRembuse = new AdapterRembuse(modelRembuses, FragmentRembuse.class );
                recyclerView.setAdapter(adapterRembuse);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }
}
