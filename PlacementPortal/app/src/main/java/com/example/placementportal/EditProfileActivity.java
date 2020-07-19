package com.example.placementportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fillData();
        findViewById(R.id.EditButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,EditProfile2.class);
                startActivity(intent);
            }
        });
    }

    private void fillData() {
        DocumentReference docRef = db.collection("studentProfiles").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if(documentSnapshot.exists()) {
                        Map<String,Object> data = documentSnapshot.getData();
                        TextView nameTV = findViewById(R.id.nameTV);
                        TextView cpiTV = findViewById(R.id.cpiTV);
                        TextView branchTV = findViewById(R.id.branchTV);
                        TextView bioTV = findViewById(R.id.bioTV);
                        assert data != null;
                        nameTV.setText(data.get("name")!=null ? data.get("name").toString() : "Nil" );
                        cpiTV.setText(data.get("cpi")!=null ? data.get("CPI").toString() : "Nil" );
                        branchTV.setText(data.get("branch")!=null ? data.get("branch").toString() : "Nil" );
                        bioTV.setText(data.get("bio")!=null ? data.get("bio").toString() : "Nil" );
                    }
                }
            }
        });
    }
}