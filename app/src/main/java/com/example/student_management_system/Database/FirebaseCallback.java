package com.example.student_management_system.Database;

import java.util.List;

public interface FirebaseCallback<T> {
    void onSuccess(List<T> data);
    void onError(Exception e);
}
