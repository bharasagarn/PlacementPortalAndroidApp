package com.example.placementportal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompanyLoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseLoginWithEmail();
            }
        });
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            updateUI();
        }
    }

    private void updateUI() {
        Intent intent = new Intent(this, CompanyHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void FirebaseLoginWithEmail() {
        EditText emailET = findViewById(R.id.email);
        EditText passwordET = findViewById(R.id.password);
        String email,password;
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) Toast.makeText(CompanyLoginActivity.this,"Please try again.",Toast.LENGTH_LONG).show();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) updateUI();
                        else Toast.makeText(CompanyLoginActivity.this,"Please try again.",Toast.LENGTH_LONG).show();
                    }
                });
    }
}

