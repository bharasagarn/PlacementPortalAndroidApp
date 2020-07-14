package com.example.placementportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompanyHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        findViewById(R.id.PostOpeningButton).setOnClickListener(OpeningListener);
        findViewById(R.id.manageOpeningsButton).setOnClickListener(OpeningListener);
        TextView welcomeTV = findViewById(R.id.welcomeTV);
        assert user != null;
        welcomeTV.setText(user.getUid());
    }

    private View.OnClickListener OpeningListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View v) {
            if(v==findViewById(R.id.PostOpeningButton)) {
                intent = new Intent(CompanyHomeActivity.this,PostOpeningActivity.class);
            }
            else {
                intent = new Intent(CompanyHomeActivity.this,ManageOpeningsActivity.class);
            }
            startActivity(intent);
        }
    };
}