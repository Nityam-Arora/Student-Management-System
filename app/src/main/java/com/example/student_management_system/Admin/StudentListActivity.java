package com.example.student_management_system.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_management_system.Adapters.StudentAdapter;
import com.example.student_management_system.Database.Models.StudentModel;
import com.example.student_management_system.Database.Repositories.StudentRepository;
import com.example.student_management_system.Database.FirebaseCallback;
import com.example.student_management_system.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_list);

        StudentRepository studentRepository = new StudentRepository();
        ArrayList<StudentModel> studentList = new ArrayList<>();
        StudentAdapter adapter = new StudentAdapter(this, studentList);

        TextView tvNoData = findViewById(R.id.tvNoData);
        RecyclerView rvStudents = findViewById(R.id.rvStudents);
        FloatingActionButton fabAddStudent = findViewById(R.id.fabAddStudent);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(adapter);

        studentRepository.getAllStudents(new FirebaseCallback<StudentModel>() {
            @Override
            public void onSuccess(List<StudentModel> data) {
                studentList.clear();
                studentList.addAll(data);
                adapter.notifyDataSetChanged();
                if (studentList.isEmpty()) {
                    tvNoData.setVisibility(View.VISIBLE);
                    rvStudents.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                    rvStudents.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(StudentListActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        fabAddStudent.setOnClickListener(v -> startActivity(new Intent(StudentListActivity.this, AddStudentActivity.class)));
    }
}