package com.example.placementportal;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class EditProfile2 extends AppCompatActivity {

    FirebaseUser user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("studentProfiles").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if(documentSnapshot.exists()) {
                        Map<String,Object> data = documentSnapshot.getData();
                        assert data != null;
                        EditText nameET = findViewById(R.id.namespET);
                        EditText branchET = findViewById(R.id.branchcpET);
                        EditText cpiET = findViewById(R.id.cpispET);
                        EditText bioET = findViewById(R.id.biospET);
                        nameET.setText(data.get("name")!=null ? data.get("name").toString() : "");
                        branchET.setText(data.get("branch")!=null ? data.get("branch").toString() : "");
                        cpiET.setText(data.get("CPI")!=null ? data.get("CPI").toString() : "");
                        bioET.setText(data.get("bio")!=null ? data.get("bio").toString() : "");
                    }
                }
            }
        });
        findViewById(R.id.submitspbutton).setOnClickListener(submitButtonListener);
    }

    private void updateUI() {
        Toast toast = Toast.makeText(this,"Profile Updated!", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }

    private View.OnClickListener submitButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText nameET = findViewById(R.id.namespET);
            EditText branchET = findViewById(R.id.branchcpET);
            EditText cpiET = findViewById(R.id.cpispET);
            EditText bioET = findViewById(R.id.biospET);
            final String name = nameET.getText().toString();
            final String branch = branchET.getText().toString();
            final Double cpi = Double.parseDouble(cpiET.getText().toString());
            final String bio = bioET.getText().toString();
            StudentProfile newData = new StudentProfile();
            newData.name = name;
            newData.branch = branch;
            newData.CPI = cpi;
            newData.bio = bio;
            db.collection("studentProfiles").document(user.getUid()).set(newData);
            Log.d("update", "done");
            updateUI();
        }
    };
}