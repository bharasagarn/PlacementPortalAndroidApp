package com.example.placementportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
        findViewById(R.id.viewOpenings).setOnClickListener(ViewOpeningsListener);
    }

    View.OnClickListener EditProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StudentHomeActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener ViewOpeningsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DocumentReference docRef = db.collection("studentProfiles").document(user.getUid());

        }
    };
}