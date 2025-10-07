package com.example.student_management_system.Admin;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.Database.Models.StudentModel;
import com.example.student_management_system.Database.Repositories.StudentRepository;
import com.example.student_management_system.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class AddStudentActivity extends AppCompatActivity {

    EditText studentName, studentId, studentPhone;
    MaterialAutoCompleteTextView studentYear, studentDepartment;
    String name, id, phone, department, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        studentName = findViewById(R.id.studentName);
        studentId = findViewById(R.id.studentId);
        studentPhone = findViewById(R.id.studentPhone);
        studentYear = findViewById(R.id.studentYear);
        studentDepartment = findViewById(R.id.studentDepartment);
        MaterialButton saveButton = findViewById(R.id.saveButton);

        String[] yearOptions = new String[]{
                "1st year", "2nd year", "3rd year"
        };

        String[] department = new String[]{
                "BCA", "BBA", "MCA", "MBA"
        };

        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, yearOptions);
        studentYear.setAdapter(adapterYear);
        studentYear.setText("1st year", false);
        studentYear.setKeyListener(null);

        ArrayAdapter<String> adapterDepartment = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, department);
        studentDepartment.setAdapter(adapterDepartment);
        studentDepartment.setText("BCA", false);
        studentDepartment.setKeyListener(null);

        saveButton.setOnClickListener(v -> {
            Validate();
            InsertData();
        });
    }

    private void Validate() {
        name = studentName.getText().toString();
        id = studentId.getText().toString();
        phone = studentPhone.getText().toString();
        department = studentDepartment.getText().toString();
        year = studentYear.getText().toString();

        if (name.isEmpty()) {
            studentName.setError("Empty");
            studentName.requestFocus();
        } else if (id.isEmpty()) {
            studentId.setError("Empty");
            studentId.requestFocus();
        } else if (phone.isEmpty()) {
            studentPhone.setError("Empty");
            studentPhone.requestFocus();
        }
    }

    private void InsertData() {
        StudentRepository studentRepository = new StudentRepository();
        StudentModel student = new StudentModel(name, id, phone, department, year);

        studentRepository.addStudent(student);
    }
}