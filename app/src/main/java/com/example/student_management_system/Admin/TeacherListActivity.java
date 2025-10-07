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

import com.example.student_management_system.Adapters.TeacherAdapter;
import com.example.student_management_system.Database.Models.TeacherModel;
import com.example.student_management_system.Database.Repositories.AdminRepository;
import com.example.student_management_system.Database.FirebaseCallback;
import com.example.student_management_system.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TeacherListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher_list);

        AdminRepository adminRepository = new AdminRepository();
        ArrayList<TeacherModel> teacherList = new ArrayList<>();
        TeacherAdapter adapter = new TeacherAdapter(this, teacherList);

        TextView tvNoData = findViewById(R.id.tvNoData);
        RecyclerView rvTeachers = findViewById(R.id.rvTeachers);
        FloatingActionButton fabAddTeacher = findViewById(R.id.fabAddTeacher);

        rvTeachers.setLayoutManager(new LinearLayoutManager(this));
        rvTeachers.setAdapter(adapter);

        adminRepository.getAllTeachers(new FirebaseCallback<TeacherModel>() {
            @Override
            public void onSuccess(List<TeacherModel> data) {
                teacherList.clear();
                teacherList.addAll(data);
                adapter.notifyDataSetChanged();
                if (teacherList.isEmpty()) {
                    tvNoData.setVisibility(View.VISIBLE);
                    rvTeachers.setVisibility(View.GONE);
                } else {
                    tvNoData.setVisibility(View.GONE);
                    rvTeachers.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(TeacherListActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        fabAddTeacher.setOnClickListener(v -> startActivity(new Intent(TeacherListActivity.this, AddTeacherActivity.class)));
    }
}