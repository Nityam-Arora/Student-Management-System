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

import com.example.student_management_system.R;
import com.example.student_management_system.Core.StudentManagementApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    // UI Elements
    private MaterialCardView studentCard, teacherCard, adminCard;
    private TextView emailText, passwordText, tvSignUp;
    private Button loginButton;

    // Role selected by user
    private String selectedRole = null;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        /**
         * Directly takes to respective home screen based on previous login details
         */
        if (StudentManagementApp.sessionManager.isLoggedIn()) {
            String role = StudentManagementApp.sessionManager.getRole();

            switch (role) {
                case "Admin":
                    startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                    break;
                case "Teacher":
                    startActivity(new Intent(LoginActivity.this, TeacherHomeActivity.class));
                    break;
                case "Student":
                    startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
                    break;
            }
            finish();
            return;
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        tvSignUp = findViewById(R.id.tvSignUp);

        studentCard = findViewById(R.id.studentCard);
        teacherCard = findViewById(R.id.teacherCard);
        adminCard = findViewById(R.id.adminCard);

        // Setup role selection (Student / Teacher / Admin)
        setupRoleSelection();

        // Handle login button click
        loginButton.setOnClickListener(v -> AttemptLogin());

        // Redirect to Sign Up page
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });
    }

    /**
     * Handles the login process
     */
    private void AttemptLogin() {
        String email = emailText.getText() != null ? emailText.getText().toString().trim() : "";
        String password = passwordText.getText() != null ? passwordText.getText().toString().trim() : "";

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedRole == null) {
            Toast.makeText(LoginActivity.this, "Please select a role", Toast.LENGTH_SHORT).show();
            return;
        }

        // Special case: Admin login is hardcoded
        if (selectedRole.equals("Admin")) {
            HandleAdminLogin(email, password);
        } else {
            LoginUserWithFirebase(email, password);
        }
    }

    /**
     * Handles hardcoded admin login
     */
    private void HandleAdminLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            // Reference Admin node
                            DatabaseReference adminRef = FirebaseDatabase.getInstance()
                                    .getReference("admin")
                                    .child(uid);

                            adminRef.get().addOnSuccessListener(snapshot -> {
                                if (snapshot.exists()) {
                                    // Valid Admin
                                    Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                    StudentManagementApp.sessionManager.createLoginSession("Admin", email);
                                    startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                                    finish();
                                } else {
                                    // Not listed as Admin in DB
                                    Toast.makeText(LoginActivity.this, "Access denied! Not an admin account", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Uses Firebase Authentication for Student & Teacher login
     */
    private void LoginUserWithFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(LoginActivity.this, "Login Successful as " + selectedRole, Toast.LENGTH_SHORT).show();

                            StudentManagementApp.sessionManager.createLoginSession(selectedRole, email);
                            // Navigate to respective home activity
                            if (selectedRole.equals("Student")) {
                                startActivity(new Intent(LoginActivity.this, StudentHomeActivity.class));
                            } else if (selectedRole.equals("Teacher")) {
                                startActivity(new Intent(LoginActivity.this, TeacherHomeActivity.class));
                            }
                            finish(); // Close LoginActivity
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Setup role selection using card clicks
     */
    private void setupRoleSelection() {
        studentCard.setOnClickListener(v -> selectRole("Student", studentCard));
        teacherCard.setOnClickListener(v -> selectRole("Teacher", teacherCard));
        adminCard.setOnClickListener(v -> selectRole("Admin", adminCard));
    }

    /**
     * Highlights selected card and sets role
     */
    private void selectRole(String role, MaterialCardView selectedCard) {
        // Reset all cards to white
        studentCard.setCardBackgroundColor(getColor(R.color.white));
        teacherCard.setCardBackgroundColor(getColor(R.color.white));
        adminCard.setCardBackgroundColor(getColor(R.color.white));

        // Highlight selected card
        selectedCard.setCardBackgroundColor(Color.parseColor("#03DAC5"));

        // Save selected role
        selectedRole = role;
    }
}