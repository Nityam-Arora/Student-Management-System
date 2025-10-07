package com.example.student_management_system.Admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.Database.Models.TeacherModel;
import com.example.student_management_system.Database.Repositories.AdminRepository;
import com.example.student_management_system.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class AddTeacherActivity extends AppCompatActivity {

    EditText teacherName, teacherId, teacherPhone;
    MaterialAutoCompleteTextView teacherDepartment;
    String name, id, phone, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_teacher);

        teacherName = findViewById(R.id.teacherName);
        teacherId = findViewById(R.id.teacherId);
        teacherPhone = findViewById(R.id.teacherPhone);
        teacherDepartment = findViewById(R.id.teacheerDepartment);
        MaterialButton saveButton = findViewById(R.id.saveButton);

        String[] departmentOptions = new String[]{
                "BCA", "BBA", "MCA", "MBA"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, departmentOptions);
        teacherDepartment.setAdapter(adapter);
        teacherDepartment.setText("BCA", false);
        teacherDepartment.setKeyListener(null);

        saveButton.setOnClickListener(v -> {
            Validate();
            InsertData();
        });
    }

    private void Validate() {
        name = teacherName.getText().toString();
        id = teacherId.getText().toString();
        phone = teacherPhone.getText().toString();
        department = teacherDepartment.getText().toString();

        if (name.isEmpty()) {
            teacherName.setError("Empty");
            teacherName.requestFocus();
        } else if (id.isEmpty()) {
            teacherId.setError("Empty");
            teacherId.requestFocus();
        } else if (phone.isEmpty()) {
            teacherPhone.setError("Empty");
            teacherPhone.requestFocus();
        }
    }

    private void InsertData() {
        AdminRepository adminRepository = new AdminRepository();
        TeacherModel teacher = new TeacherModel(name, id, phone, department);

        adminRepository.addTeacher(teacher);
    }
}