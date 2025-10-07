package com.example.student_management_system.Login_and_Home;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.R;
import com.example.student_management_system.Core.StudentManagementApp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class TeacherHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_home);

        MaterialCardView attendanceCard = findViewById(R.id.attendanceCard);
        MaterialCardView courseCard = findViewById(R.id.courseCard);
        MaterialCardView resultCard = findViewById(R.id.resultCard);
        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        attendanceCard.setOnClickListener(v -> {
            // TODO : Implement attendance activity
            Toast.makeText(TeacherHomeActivity.this, "Attendance", Toast.LENGTH_SHORT).show();
        });

        courseCard.setOnClickListener(v -> {
            // TODO : Implement course activity
            Toast.makeText(TeacherHomeActivity.this, "Course", Toast.LENGTH_SHORT).show();
        });

        resultCard.setOnClickListener(v -> {
            // TODO : Implement result activity
            Toast.makeText(TeacherHomeActivity.this, "Result", Toast.LENGTH_SHORT).show();
        });

        logoutButton.setOnClickListener(v -> {
            StudentManagementApp.sessionManager.logOut();
            finishAffinity();
            Toast.makeText(TeacherHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
        });
    }
}