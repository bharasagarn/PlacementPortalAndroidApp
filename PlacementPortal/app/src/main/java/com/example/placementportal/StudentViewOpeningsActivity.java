package com.example.placementportal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StudentViewOpeningsActivity extends AppCompatActivity {

    private FirebaseUser user;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirestoreRecyclerAdapter<Opening,ViewHolder> firestoreRecyclerAdapter;
    private FirebaseFirestore db;
    private Double cpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_openings);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.studentViewOpeningsList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.stopListening();
    }

    private void fetch() {
        DocumentReference docRef = db.collection("studentProfiles").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    if(documentSnapshot.exists()) {
                        cpi = Double.parseDouble(documentSnapshot.getData().get("CPI").toString());
                        Log.d("CPI", cpi.toString());
                    }
                }
            }
        });
        Query query = db.collection("openings").orderBy("JobTitle", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Opening> options = new FirestoreRecyclerOptions.Builder<Opening>()
                .setQuery(query,Opening.class)
                .build();
        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Opening, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull Opening model) {
                holder.setCompanyName(model.Company);
                holder.setJobTitle(model.JobTitle);
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(StudentViewOpeningsActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_view_openings_list_item,parent,false);
                return new ViewHolder(view);
            }
        };
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView jobTitle;
        public TextView companyName;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.studentViewOpeningsListRoot);
            jobTitle = itemView.findViewById(R.id.jobTitleTV1);
            companyName = itemView.findViewById(R.id.CompanyNameTV1);
        }

        public void setJobTitle(String string) {
            jobTitle.setText(string);
        }
        public void setCompanyName(String string) {
            companyName.setText(string);
        }
    }

}

