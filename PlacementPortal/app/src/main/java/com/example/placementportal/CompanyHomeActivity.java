package com.example.placementportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompanyHomeActivity extends AppCompatActivity {
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);
        user = FirebaseAuth.getInstance().getCurrentUser();
        findViewById(R.id.PostOpeningButton).setOnClickListener(OpeningListener);
        findViewById(R.id.logoutbutton2).setOnClickListener(OpeningListener);
    }

    private View.OnClickListener OpeningListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View v) {
            if(v==findViewById(R.id.PostOpeningButton)) {
                intent = new Intent(CompanyHomeActivity.this,PostOpeningActivity.class);
            }
            else if(v==findViewById(R.id.logoutbutton2)) {
                if(user!=null) FirebaseAuth.getInstance().signOut();
                intent = new Intent(CompanyHomeActivity.this,MainActivity.class);
            }
            startActivity(intent);
            if(v==findViewById(R.id.logoutbutton2)) finish();
        }
    };
}