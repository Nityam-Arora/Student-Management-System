package com.example.student_management_system.Database.Models;

public class AdminModel {
    private String uid;
    private String name;
    private String phone;
    private String role;

    // Default constructor required by Firebase
    public AdminModel() {}

    public AdminModel(String uid, String name, String phone, String role) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
