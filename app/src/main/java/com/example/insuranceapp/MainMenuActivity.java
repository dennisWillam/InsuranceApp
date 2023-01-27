package com.example.insuranceapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.insuranceapp.fragment.ConnectFragment;
import com.example.insuranceapp.fragment.FragmentRembuse;
import com.example.insuranceapp.maps.MapsActivity;
import com.example.insuranceapp.model.ModelAsuransiUser;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private TextView textname, tvAsuransiNama, tvAsuransiDesrkripsi,tvAsuransiHarga, tvAsuransiStatus;
    ImageView profile;
    private String nama,desc,harga,status;
    private DrawerLayout drawer;
    private ImageView imageProvider,imageViewPolis, imageRembuse;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolBarr);

        profile = findViewById(R.id.imageProfile);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navview);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        imageProvider = findViewById(R.id.imageProvider);
        imageViewPolis = findViewById(R.id.imagePolis);
        imageRembuse = findViewById(R.id.imageRembuse);

        tvAsuransiNama = findViewById(R.id.cdNamaAsuransi);
        tvAsuransiDesrkripsi = findViewById(R.id.cdDescription);
        tvAsuransiHarga = findViewById(R.id.cdHarga);
        tvAsuransiStatus = findViewById(R.id.tvStatusUser);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("AsuransiUser").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelAsuransiUser dataUser = snapshot.getValue(ModelAsuransiUser.class);
                if(dataUser != null) {
                    nama = dataUser.getNama();
                    desc = dataUser.getDesc();
                    harga = dataUser.getHarga();
                    status = dataUser.getStatus();
                    tvAsuransiNama.setText(nama);
                    tvAsuransiDesrkripsi.setText(desc);
                    tvAsuransiHarga.setText(harga);
                    tvAsuransiStatus.setText(status);
                }else{
                    tvAsuransiNama.setText("Anda belum memiliki Asuransi");
                    tvAsuransiDesrkripsi.setText("-");
                    tvAsuransiHarga.setText("-");
                    tvAsuransiStatus.setText("-");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageRembuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RembuseActivity.class));
            }
        });

        imageViewPolis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        imageProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });



        textname = findViewById(R.id.namakamu);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            textname.setText(firebaseUser.getDisplayName());
        }else{
            textname.setText("Login gagal");
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reimburse:
                replaceFragment(new FragmentRembuse());
                break;
            case R.id.nav_home:
                //replaceFragment(new HomeFragment());
                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
                finish();
                break;
            case R.id.about:
                replaceFragment(new ConnectFragment());
                break;
            case R.id.logoutBtn:
                AlertDialog.Builder alertBuild = new AlertDialog.Builder(this);
                //set title
                alertBuild.setTitle("Confirm Sign Out");
                //set pesan
                alertBuild.setMessage("Yakin ingin Sign Out?");
                //set option
                alertBuild.setCancelable(true);
                alertBuild.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(),"Berhasil Sign out",Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
                //no
                alertBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainMenuActivity.this,"Klik No",Toast.LENGTH_SHORT).show();
                    }
                });
                //cancel/netral
                //set alert
                AlertDialog alert = alertBuild.create();
                //menampilkan alert
                alert.show();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fremleyout, fragment);
        fragmentTransaction.commit();
    }
}