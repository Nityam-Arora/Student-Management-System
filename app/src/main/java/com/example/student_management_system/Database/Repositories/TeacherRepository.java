package com.example.student_management_system.Database.Repositories;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherRepository {
    private final DatabaseReference teachersRef;
    private final StudentRepository studentRepository;

    private static final String STUDENTS_NODE = "students";

    public TeacherRepository() {
        teachersRef = FirebaseDatabase.getInstance().getReference(STUDENTS_NODE);
        studentRepository = new StudentRepository();
    }
}
