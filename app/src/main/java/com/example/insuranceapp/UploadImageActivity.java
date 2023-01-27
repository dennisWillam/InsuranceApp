package com.example.insuranceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.insuranceapp.databinding.ActivityUploadImageBinding;
import com.example.insuranceapp.model.ModelRembuse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadImageActivity extends AppCompatActivity {

    ActivityUploadImageBinding binding;
    Uri imageUri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        binding = ActivityUploadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.uploadimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        UploadTask uploadTask = storageReference.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent intent = getIntent();
                        String date = intent.getStringExtra("date");
                        String nama = intent.getStringExtra("nama");
                        String benefit = intent.getStringExtra("benefit");
                        String bayar = intent.getStringExtra("bayar");
                        String tempat = intent.getStringExtra("tempat");
                        String dokter = intent.getStringExtra("dokter");
                        String url = uri.toString();
                        String status = "Diproses";
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        Toast.makeText(getApplicationContext(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        databaseReference.child("Rembuse").child(uid).push().setValue(new ModelRembuse(date, nama, benefit, bayar, tempat, dokter, email,status,url)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Data berhasil di simpan", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
            }
        });
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            binding.firebaseimage.setImageURI(imageUri);
        }
    }
}