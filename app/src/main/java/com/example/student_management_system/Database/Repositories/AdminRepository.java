package com.example.student_management_system.Database.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.student_management_system.Database.Models.TeacherModel;
import com.example.student_management_system.Database.FirebaseCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRepository {
    private final DatabaseReference teachersRef;

    public AdminRepository() {
        teachersRef = FirebaseDatabase.getInstance().getReference("teachers");
    }

    /** Add teacher to the database */
    public void addTeacher(TeacherModel teacherModel) {
        String autoId = teachersRef.push().getKey();
        if (autoId != null) {
            teacherModel.setId(autoId);
            teachersRef.child(autoId).setValue(teacherModel)
                    .addOnSuccessListener(unused -> Log.d("Firebase", "Teacher added successfully"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Failed to add Teacher", e));
        } else {
            Log.e("Firebase", "Failed to generate unique key for teacher");
        }
    }

    /** Update the teacher data */
    public void updateTeacher(TeacherModel teacherModel) {
        String id = teacherModel.getId();
        if (id != null) {
            teachersRef.child(id).setValue(teacherModel)
                    .addOnSuccessListener(unused -> Log.d("Firebase", "Teacher data update successfully"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Failed to update teacher's data", e));
        } else {
            Log.e("Firebase", "Failed to generate unique key for student");
        }
    }

    /** Remove the teacher data */
    public void removeTeacher(String id) {
        if (id != null) {
            teachersRef.child(id).removeValue()
                    .addOnSuccessListener(unused -> Log.d("Firebase", "Teacher removal successfully"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Failed to remove Teacher", e));
        } else {
            Log.e("Firebase", "Cannot remove teacher: ID is null");
        }
    }

    /** Gets all teacher's data from the database */
    public void getAllTeachers(FirebaseCallback<TeacherModel> callback) {
        teachersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<TeacherModel> teachers = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        TeacherModel teacher = child.getValue(TeacherModel.class);
                        if (teacher != null) {
                            teacher.setId(child.getKey());
                            teachers.add(teacher);
                        }
                    }
                }
                callback.onSuccess(teachers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

}
