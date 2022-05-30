package com.example.fakenewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView signBtn;
    EditText loginEmail, loginPassword;
    Button loginBtn;
    ImageView imageView;
    ProgressBar progressBar2;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        signBtn = findViewById(R.id.signBtn);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        imageView = findViewById(R.id.imageView);
        progressBar2 = findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    loginPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    loginPassword.setError("Password Must be more than 6 Characters");
                    return;
                }

                progressBar2.setVisibility(View.VISIBLE);

                // authenticate the user

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }else{
                            Toast.makeText(Login.this, "Please Enter the Correct Email ID and Password" , Toast.LENGTH_SHORT).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class) );

            }
        });

    }
}