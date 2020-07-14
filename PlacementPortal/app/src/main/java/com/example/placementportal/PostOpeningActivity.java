package com.example.placementportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostOpeningActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageReference;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_opening);
        findViewById(R.id.submit_button).setOnClickListener(SubmitOpeningListener);
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.brochurechoosebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Brochure JPEG file here"),
                PICK_IMAGE_REQUEST
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            filePath = data.getData();
        }
    }

    private void uploadImage() {
        if(filePath!=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            FirebaseUser user = mAuth.getCurrentUser();
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            assert user != null;
            StorageReference ref = storageReference.child("images/"+ user.getUid());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(PostOpeningActivity.this,"Uploaded!",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(PostOpeningActivity.this,"Failed! "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
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
            uploadImage();
            Opening opening = new Opening(companyName,jobTitle,jobDesc,CPI);
            assert user != null;
            opening.brochureURL = storageReference.child("images/"+user.getUid()).getDownloadUrl().toString();
            db.collection("openings").document(companyName+jobTitle).set(opening);
            updateUI();
        }
    };
}