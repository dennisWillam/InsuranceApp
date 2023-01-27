package com.example.insuranceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.insuranceapp.admin.DashboardAdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText edEmail,edPassword;
    String user,password,userDB,passDB;
    Button btnLogin, btnRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edEmail = findViewById(R.id.emailLogin);
        edPassword = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegisterBaru);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan tunggu");
        progressDialog.setCancelable(false);

        DB = new DBHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edEmail.getText().length()>0 && edPassword.getText().length()>0){
                    user = edEmail.getText().toString();
                    password = edPassword.getText().toString();

                    Cursor usersCursor = DB.getUsers();

                    StringBuffer buffer = new StringBuffer();
                    while (usersCursor.moveToNext()) {
                        userDB = usersCursor.getString(0);
                        passDB = usersCursor.getString(1);
                    }

                    if(user.equals(userDB) && password.equals(passDB)){
                        startActivity(new Intent(getApplicationContext(), DashboardAdminActivity.class));
                        finish();
                    }else{
                        login(edEmail.getText().toString(), edPassword.getText().toString());
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult()!=null){
                    if (task.getResult().getUser()!=null){
                        reload();
                    }else{
                        Toast.makeText(getApplicationContext(),"Login gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Login gagal", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
    }
}