package com.example.insuranceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class RegisterActivity extends AppCompatActivity {

    EditText regisEmail,regisName,regisPassword,regisPassword2;
    String nama,email,password,password2;
    Button registerButton, loginButton;
    FirebaseAuth fAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        regisEmail = findViewById(R.id.edEmailRegister);
        regisName = findViewById(R.id.edUserName);
        regisPassword = findViewById(R.id.edPasswordRegister);
        regisPassword2 = findViewById(R.id.edPasswordRegister2);
        registerButton = findViewById(R.id.btnRegister);
        loginButton = findViewById(R.id.btnloginakun);
        fAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan tunggu");
        progressDialog.setCancelable(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = regisName.getText().toString();
                email = regisEmail.getText().toString();
                password = regisPassword.getText().toString();
                password2 = regisPassword2.getText().toString();

                //form validation
                if (nama.isEmpty()){
                    regisName.setError("Nama tidak boleh kosong !");
                    return;
                }else if (email.isEmpty()){
                    regisEmail.setError("Email tidak boleh kosong !");
                    return;
                } else if (password.isEmpty()){
                    regisPassword.setError("Password tidak boleh kosong !");
                    return;
                } else if (password2.isEmpty()){
                    regisPassword2.setError("Password tidak boleh kosong !");
                    return;
                } else if (!password.equals(password2)){
                    regisPassword2.setError("Password tidak sama !");
                    return;
                } else {
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() && task.getResult()!=null){
                                FirebaseUser firebaseUser = task.getResult().getUser();
                                if (firebaseUser!=null) {
                                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nama)
                                            .build();
                                    firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            reload();

                                        }
                                    });
                                }else{
                                    Toast.makeText(getApplicationContext(),"Register gagal", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

                        }
                    });

                }


            }
        });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}