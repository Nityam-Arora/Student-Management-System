package com.example.student_management_system.Login_and_Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student_management_system.Database.Models.StudentModel;
import com.example.student_management_system.Database.Models.TeacherModel;
import com.example.student_management_system.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    // UI components
    MaterialCardView studentCard, teacherCard;
    private String selectedRole = null;

    private FirebaseAuth mAuth;

    private TextInputEditText idText, emailText, passwordText;
    private Button signupButton;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Bind UI elements
        idText = findViewById(R.id.idText);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        signupButton = findViewById(R.id.signupButton);
        tvSignIn = findViewById(R.id.tvSignIn);

        studentCard = findViewById(R.id.studentCard);
        teacherCard = findViewById(R.id.teacherCard);

        // Setup Student/Teacher card clicks
        setupRoleSelection();

        // Handle Signup button click
        signupButton.setOnClickListener(v -> AttemptSignup());

        // Navigate to Sign In screen
        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });
    }

    /**
     * Validates inputs and attempts signup
     */
    private void AttemptSignup() {
        String id = idText.getText() != null ? idText.getText().toString().trim() : "";
        String email = emailText.getText() != null ? emailText.getText().toString().trim() : "";
        String password = passwordText.getText() != null ? passwordText.getText().toString().trim() : "";

        // Input validation
        if (id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedRole == null) {
            Toast.makeText(SignupActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Firebase User
        CreateUser(id, email, password);
    }

    /**
     * Create user in Firebase Authentication
     */
    private void CreateUser(String id, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get newly created user's UID
                        String uid = mAuth.getCurrentUser().getUid();
                        // Update Database with user details
                        CheckAndUpdateDatabase(id, uid, email);
                    } else {
                        Toast.makeText(SignupActivity.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Checks if entered Student/Teacher ID exists in DB
     * If found → update that record with UID, email, and registered = true
     */
    private void CheckAndUpdateDatabase(String id, String uid, String email) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference(selectedRole.equals("Student") ? "students" : "teachers");

        String childField = selectedRole.equals("Student") ? "studentId" : "teacherId";

        dbRef.orderByChild(childField).equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get Firebase-generated key of this record
                    String key = snapshot.getChildren().iterator().next().getKey();

                    if (selectedRole.equals("Student")) {
                        StudentModel studentModel = snapshot.getChildren().iterator().next().getValue(StudentModel.class);
                        if (studentModel != null) {
                            if (studentModel.isRegistered()) {
                                // Already registered -> stop here
                                Toast.makeText(SignupActivity.this, "This student is already registered!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            studentModel.setUid(uid);
                            studentModel.setEmail(email);
                            studentModel.setRegistered(true);

                            dbRef.child(key).setValue(studentModel)
                                    .addOnSuccessListener(unused -> Toast.makeText(SignupActivity.this, "Student registration complete!", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        TeacherModel teacherModel = snapshot.getChildren().iterator().next().getValue(TeacherModel.class);
                        if (teacherModel != null) {
                            if (teacherModel.isRegistered()) {
                                // Already registered -> stop here
                                Toast.makeText(SignupActivity.this, "This student is already registered!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            teacherModel.setUid(uid);
                            teacherModel.setEmail(email);
                            teacherModel.setRegistered(true);

                            dbRef.child(key).setValue(teacherModel)
                                    .addOnSuccessListener(unused -> Toast.makeText(SignupActivity.this, "Teacher registration complete!", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "ID not found in database!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignupActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Setup role selection UI
     */
    private void setupRoleSelection() {
        studentCard.setOnClickListener(v -> selectRole("Student", studentCard));
        teacherCard.setOnClickListener(v -> selectRole("Teacher", teacherCard));
    }

    /**
     * Handles role selection and highlights chosen card
     */
    private void selectRole(String role, MaterialCardView selectedCard) {
        // Reset colors
        studentCard.setCardBackgroundColor(getColor(R.color.white));
        teacherCard.setCardBackgroundColor(getColor(R.color.white));

        // Highlight selected card
        selectedCard.setCardBackgroundColor(Color.parseColor("#03DAC5"));

        selectedRole = role;
    }
}
