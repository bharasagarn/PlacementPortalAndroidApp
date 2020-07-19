package com.example.placementportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentHomeActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null) {
            Intent intent = new Intent(this,StudentLoginActivity.class);
            startActivity(intent);
            finish();
        }
        findViewById(R.id.editProfileButton).setOnClickListener(EditProfileListener);
//        findViewById(R.id.viewOpenings).setOnClickListener(ViewOpeningsListener);
        findViewById(R.id.logoutbutton).setOnClickListener(LogoutListener);
    }

    View.OnClickListener EditProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StudentHomeActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener LogoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(StudentHomeActivity.this,MainActivity.class));
            finish();
        }
    };
}