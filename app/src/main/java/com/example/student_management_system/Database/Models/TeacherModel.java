package com.example.student_management_system.Database.Models;

public class TeacherModel {
    private String id;
    private String uid;
    private String name;
    private String teacherId;
    private String phone;
    private String department;
    private String email;
    private boolean isRegistered;

    // Default constructor required by Firebase
    public TeacherModel() {}

    public TeacherModel(String name, String teacherId, String phone, String department) {
        this.name = name;
        this.teacherId = teacherId;
        this.phone = phone;
        this.department = department;
        this.email = null;
        this.isRegistered = false;
        this.id = null;
        this.uid = null;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUid() { return uid; }
    public void  setUid(String uid) { this.uid = uid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId() { this.teacherId = teacherId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isRegistered() { return isRegistered; }
    public void setRegistered(boolean registered) { isRegistered = registered; }
}
