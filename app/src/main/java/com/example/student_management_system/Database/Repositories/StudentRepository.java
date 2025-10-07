package com.example.student_management_system.Database.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.student_management_system.Database.Models.StudentModel;
import com.example.student_management_system.Database.FirebaseCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private final DatabaseReference studentsRef;

    public StudentRepository() {
        studentsRef = FirebaseDatabase.getInstance().getReference("students");
    }

    public void addStudent(StudentModel studentModel) {
        String autoId = studentsRef.push().getKey();
        if (autoId != null) {
            studentModel.setId(autoId);
            studentsRef.child(autoId).setValue(studentModel)
                    .addOnSuccessListener(unused -> Log.d("Firebase", "Student added successfully"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Failed to add Student", e));
        } else {
            Log.e("Firebase", "Failed to generate unique key for student");
        }
    }

    public void updateStudent(StudentModel studentModel) {
        String id = studentModel.getId();
        studentsRef.child(id).setValue(studentModel)
                .addOnSuccessListener(unused -> Log.d("Firebase", "Student data update successfully"))
                .addOnFailureListener(e -> Log.e("Firebase", "Failed to update Student's data", e));
    }

    public void removeStudent(String id) {
        if (id != null) {
            studentsRef.child(id).removeValue()
                    .addOnSuccessListener(unused -> Log.d("Firebase", "Student removed successfully"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Failed to remove Student", e));
        } else {
            Log.e("Firebase", "Cannot remove student: ID is null");
        }
    }

    public void getAllStudents(FirebaseCallback<StudentModel> callback) {
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<StudentModel> students = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        StudentModel student = child.getValue(StudentModel.class);
                        if (student != null) {
                            student.setId(child.getKey());
                            students.add(student);
                        }
                    }
                }
                callback.onSuccess(students);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

}
