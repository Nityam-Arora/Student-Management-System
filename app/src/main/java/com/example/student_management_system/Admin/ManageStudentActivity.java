package com.example.student_management_system.Admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.Database.Models.StudentModel;
import com.example.student_management_system.Database.Repositories.StudentRepository;
import com.example.student_management_system.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class ManageStudentActivity extends AppCompatActivity {

    EditText studentName, studentId, studentPhone;
    MaterialAutoCompleteTextView studentYear, studentDepartment;
    String name, studId, phone, department, year;

    String id;

    StudentRepository studentRepository = new StudentRepository();
    StudentModel studentModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_student);

        name = getIntent().getStringExtra("name");
        studId = getIntent().getStringExtra("studentId");
        phone = getIntent().getStringExtra("phone");
        department = getIntent().getStringExtra("department");
        year = getIntent().getStringExtra("year");

        id = getIntent().getStringExtra("id");
        Log.d("ManageStudentActivity", "Id = " + id);

        studentName = findViewById(R.id.studentName);
        studentId = findViewById(R.id.studentId);
        studentPhone = findViewById(R.id.studentPhone);
        studentDepartment = findViewById(R.id.studentDepartment);
        studentYear = findViewById(R.id.studentYear);
        MaterialButton updateButton = findViewById(R.id.updateButton);
        MaterialButton removeButton = findViewById(R.id.removeButton);

        studentName.setText(name);
        studentId.setText(studId);
        studentPhone.setText(phone);
        studentDepartment.setText(department);
        studentYear.setText(year);

        String[] yearOptions = new String[]{
                "1st year", "2nd year", "3rd year"
        };

        String[] departmentOptions = new String[]{
                "BCA", "BBA", "MCA", "MBA"
        };

        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearOptions);
        studentYear.setAdapter(adapterYear);
        studentYear.setText(year, false);
        studentYear.setKeyListener(null);

        ArrayAdapter<String> adapterDepartment = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, departmentOptions);
        studentDepartment.setAdapter(adapterDepartment);
        studentDepartment.setText(department, false);
        studentDepartment.setKeyListener(null);

        updateButton.setOnClickListener(v -> {
            Validate();
            Update();
        });

        removeButton.setOnClickListener(v -> {
            studentRepository.removeStudent(id);
        });
    }

    private void Validate() {
        String updatedName = studentName.getText().toString();
        String updatedStudId = studentId.getText().toString();
        String updatedPhone = studentPhone.getText().toString();

        if (updatedName.isEmpty()) {
            studentName.setError("Empty");
            studentName.requestFocus();
        } else if (updatedStudId.isEmpty()) {
            studentId.setError("Empty");
            studentId.requestFocus();
        } else if (updatedPhone.isEmpty()) {
            studentPhone.setError("Empty");
            studentPhone.requestFocus();
        }
    }

    private void Update() {
        String updatedName = studentName.getText().toString();
        String updatedStudId = studentId.getText().toString();
        String updatedPhone = studentPhone.getText().toString();
        String updatedDepartment = studentDepartment.getText().toString();
        String updatedYear = studentYear.getText().toString();

        studentModel = new StudentModel(updatedName, updatedStudId, updatedPhone, updatedDepartment, updatedYear);
        studentModel.setId(id);

        studentRepository.updateStudent(studentModel);
    }
}