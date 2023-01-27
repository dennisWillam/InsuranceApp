package com.example.insuranceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.insuranceapp.model.ModelRembuse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RembuseActivity extends AppCompatActivity {
    private ImageButton btnDate;
    private EditText edDate,edNama,edBenefit,edBayar,edTempat,edDokter;
    private int mDate, mMonth, mYear;
    private Button btnLanjut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rembuse);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edDate = findViewById(R.id.editDate);
        edNama = findViewById(R.id.editNama);
        edBenefit = findViewById(R.id.editTipeBenefit);
        edBayar = findViewById(R.id.editPembayaran);
        edTempat = findViewById(R.id.editTempat);
        edDokter = findViewById(R.id.editDokter);
        btnLanjut = findViewById(R.id.buttonLanjut);
        btnDate = findViewById(R.id.imageDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RembuseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        edDate.setText(date+"-"+(month+1)+"-"+year);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = edDate.getText().toString();
                String nama = edNama.getText().toString();
                String benefit = edBenefit.getText().toString();
                String bayar = edBayar.getText().toString();
                String tempat = edTempat.getText().toString();
                String dokter = edDokter.getText().toString();


                if (date.isEmpty()) {
                    edDate.setError("Tanggal tidak boleh kosong !");
                    return;
                }
                else if (nama.isEmpty()) {
                    edNama.setError("Nama tidak boleh kosong !");
                    return;
                }
                else if (benefit.isEmpty()) {
                    edBenefit.setError("Benefit tidak boleh kosong !");
                    return;
                }
                else if (bayar.isEmpty()) {
                    edBayar.setError("Total Bayar tidak boleh kosong !");
                    return;
                }
                else if (tempat.isEmpty()) {
                    edTempat.setError("Tempat tidak boleh kosong !");
                    return;
                }
                else if (dokter.isEmpty()) {
                    edDokter.setError("Nama dokter tidak boleh kosong !");
                    return;
                } else{
                    Intent intent = new Intent(getApplicationContext(), UploadImageActivity.class);
                    intent.putExtra("date",date);
                    intent.putExtra("nama",nama);
                    intent.putExtra("benefit",benefit);
                    intent.putExtra("bayar",bayar);
                    intent.putExtra("tempat",tempat);
                    intent.putExtra("dokter",dokter);
                    startActivity(intent);

                }
            }
        });
    }

}