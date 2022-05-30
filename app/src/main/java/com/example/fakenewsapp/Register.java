package com.example.fakenewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText name, emailRegister, setPassword;
    Button registerBtn;
    TextView loginBtn, rHead1, rHead2;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        emailRegister = findViewById(R.id.emailRegister);
        setPassword = findViewById(R.id.setPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginBtn = findViewById(R.id.loginTextBtn);
        rHead1 = findViewById(R.id.rHead1);
        rHead2 = findViewById(R.id.rHead2);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Home.class) );
            finish();

        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailRegister.getText().toString();
                String password = setPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    emailRegister.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    setPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    setPassword.setError("Password Must be more than 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase
                
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class) );
                            
                        }else{
                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class) );

            }
        });

    }
}