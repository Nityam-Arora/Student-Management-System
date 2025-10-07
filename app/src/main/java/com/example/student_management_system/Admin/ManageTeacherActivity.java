package com.example.student_management_system.Admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.Database.Models.TeacherModel;
import com.example.student_management_system.Database.Repositories.AdminRepository;
import com.example.student_management_system.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class ManageTeacherActivity extends AppCompatActivity {

    EditText teacherName, teacherId, teacherPhone;
    MaterialAutoCompleteTextView teacherDepartment;
    String name, teachId, phone, department;

    String id;

    AdminRepository adminRepository = new AdminRepository();
    TeacherModel teacherModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_teacher);

        name = getIntent().getStringExtra("name");
        teachId = getIntent().getStringExtra("teacherId");
        phone = getIntent().getStringExtra("phone");
        department = getIntent().getStringExtra("department");

        id = getIntent().getStringExtra("id");
        Log.d("ManageTeacherActivity", "Id = " + id);

        teacherName = findViewById(R.id.teacherName);
        teacherId = findViewById(R.id.teacherId);
        teacherPhone = findViewById(R.id.teacherPhone);
        teacherDepartment = findViewById(R.id.teacherDepartment);
        MaterialButton updateButton = findViewById(R.id.updateButton);
        MaterialButton removeButton = findViewById(R.id.removeButton);

        teacherName.setText(name);
        teacherId.setText(teachId);
        teacherPhone.setText(phone);
        teacherDepartment.setText(department);

        String[] departmentOptions = new String[]{
                "BCA", "BBA", "MCA", "MBA"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, departmentOptions);
        teacherDepartment.setAdapter(adapter);
        teacherDepartment.setText(department, false);
        teacherDepartment.setKeyListener(null);

        updateButton.setOnClickListener(v -> {
            Validate();
            Update();
        });

        removeButton.setOnClickListener(v -> {
            adminRepository.removeTeacher(id);

        });
    }

    private void Validate() {
        String updatedName = teacherName.getText().toString();
        String updatedStudId = teacherId.getText().toString();
        String updatedPhone = teacherPhone.getText().toString();

        if (updatedName.isEmpty()) {
            teacherName.setError("Empty");
            teacherName.requestFocus();
        } else if (updatedStudId.isEmpty()) {
            teacherId.setError("Empty");
            teacherId.requestFocus();
        } else if (updatedPhone.isEmpty()) {
            teacherPhone.setError("Empty");
            teacherPhone.requestFocus();
        }
    }

    private void Update() {
        String updatedName = teacherName.getText().toString();
        String updatedTeachId = teacherId.getText().toString();
        String updatedPhone = teacherPhone.getText().toString();
        String updatedYear = teacherDepartment.getText().toString();

        teacherModel = new TeacherModel(updatedName, updatedTeachId, updatedPhone, updatedYear);
        teacherModel.setId(id);

        adminRepository.updateTeacher(teacherModel);
    }
}