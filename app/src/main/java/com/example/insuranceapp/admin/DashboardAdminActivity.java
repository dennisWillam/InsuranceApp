package com.example.insuranceapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.insuranceapp.R;

public class DashboardAdminActivity extends AppCompatActivity {
    CardView asuransiData,rembuseData,cardPembelian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        asuransiData = findViewById(R.id.cardAsuransi);
        asuransiData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        rembuseData = findViewById(R.id.cardRembuse);
        rembuseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RembuseAdminActivity.class));
            }
        });

        cardPembelian = findViewById(R.id.cardPembelian);
        cardPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PembelianActivity.class));
            }
        });

    }
}