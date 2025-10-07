package com.example.student_management_system.Login_and_Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.R;
import com.example.student_management_system.Admin.StudentListActivity;
import com.example.student_management_system.Core.StudentManagementApp;
import com.example.student_management_system.Admin.TeacherListActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home);

        MaterialCardView teacherCard = findViewById(R.id.teacherCard);
        MaterialCardView studentCard = findViewById(R.id.studentCard);
        MaterialButton logoutButton = findViewById(R.id.logoutButton);

        teacherCard.setOnClickListener(v -> {
            startActivity(new Intent(AdminHomeActivity.this, TeacherListActivity.class));
            Toast.makeText(AdminHomeActivity.this, "Teacher", Toast.LENGTH_SHORT).show();
        });

        studentCard.setOnClickListener(v -> {
            startActivity(new Intent(AdminHomeActivity.this, StudentListActivity.class));
            Toast.makeText(AdminHomeActivity.this, "Student", Toast.LENGTH_SHORT).show();
        });

        logoutButton.setOnClickListener(v -> {
            StudentManagementApp.sessionManager.logOut();
            finishAffinity();
            Toast.makeText(AdminHomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
        });
    }
}