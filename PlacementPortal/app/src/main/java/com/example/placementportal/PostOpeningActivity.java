package com.example.placementportal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostOpeningActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_opening);
        findViewById(R.id.submit_button).setOnClickListener(SubmitOpeningListener);
        mAuth = FirebaseAuth.getInstance();
    }

    private void updateUI() {
        Toast toast = Toast.makeText(this,"Opening posted!", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }

    private View.OnClickListener SubmitOpeningListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser user = mAuth.getCurrentUser();
            EditText CompanyNameET = findViewById(R.id.CompanyNameET);
            EditText JobTitleET = findViewById(R.id.jobTitleET);
            EditText JobDescET = findViewById(R.id.jobDescriptionET);
            EditText cutoffCPIET = findViewById(R.id.cutoffCPIET);
            String companyName = CompanyNameET.getText().toString();
            String jobTitle = JobTitleET.getText().toString();
            String jobDesc = JobDescET.getText().toString();
            Double CPI = Double.parseDouble(cutoffCPIET.getText().toString());
            final Opening opening = new Opening(companyName, jobTitle,jobDesc,CPI);
            assert user != null;
            db.collection("openings").document(user.getUid()+companyName+ jobTitle).set(opening);
            updateUI();
        }
    };
}