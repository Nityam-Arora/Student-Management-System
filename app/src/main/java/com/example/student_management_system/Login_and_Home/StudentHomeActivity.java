package com.example.student_management_system.Login_and_Home;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.R;
import com.example.student_management_system.Core.StudentManagementApp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentHomeActivity extends AppCompatActivity {

    MaterialCardView attendanceCard, courseCard, resultCard;
    TextView tvName;
    MaterialButton logoutButton;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);

        tvName = findViewById(R.id.tvName);
        attendanceCard = findViewById(R.id.attendanceCard);
        courseCard = findViewById(R.id.courseCard);
        resultCard = findViewById(R.id.resultCard);
        logoutButton = findViewById(R.id.logoutButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            dbRef = FirebaseDatabase.getInstance().getReference("students");

            // Query the student node by uid
            dbRef.orderByChild("uid").equalTo(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                DataSnapshot studentSnap = snapshot.getChildren().iterator().next();
                                String name = studentSnap.child("name").getValue(String.class);
                                tvName.setText(name != null ? name : "Unknown");
                            } else {
                                tvName.setText("Name not found");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(StudentHomeActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        attendanceCard.setOnClickListener(v -> {
            // TODO : Implement Student Attendance
            Toast.makeText(StudentHomeActivity.this, "Attendance", Toast.LENGTH_SHORT).show();
        });

        courseCard.setOnClickListener(v -> {
            // TODO : Implement Student Course
            Toast.makeText(StudentHomeActivity.this, "Courses", Toast.LENGTH_SHORT).show();
        });

        resultCard.setOnClickListener(v -> {
            // TODO : Implement Student Result
            Toast.makeText(StudentHomeActivity.this, "Results", Toast.LENGTH_SHORT).show();
        });

        logoutButton.setOnClickListener(v -> {
            StudentManagementApp.sessionManager.logOut();
            finishAffinity();
            Toast.makeText(StudentHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
        });
    }
}
