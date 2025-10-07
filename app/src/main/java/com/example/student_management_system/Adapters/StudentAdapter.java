package com.example.student_management_system.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_management_system.Database.Models.StudentModel;
import com.example.student_management_system.Admin.ManageStudentActivity;
import com.example.student_management_system.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context context;
    private List<StudentModel> list;

    public StudentAdapter(Context context, List<StudentModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_student_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel studentModel = list.get(position);

        holder.name.setText("Name : " + studentModel.getName());
        holder.id.setText("Id : " + studentModel.getStudentId());
        holder.phone.setText("Phone : " + studentModel.getPhone());
        holder.department.setText("Department : " + studentModel.getDepartment());
        holder.year.setText("Year : " + studentModel.getYear());

        Log.d("Adapter", "Student ID = " + studentModel.getId());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Manage Student", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ManageStudentActivity.class);
            intent.putExtra("id", studentModel.getId());
            intent.putExtra("name", studentModel.getName());
            intent.putExtra("studentId", studentModel.getStudentId());
            intent.putExtra("phone", studentModel.getPhone());
            intent.putExtra("department", studentModel.getDepartment());
            intent.putExtra("year", studentModel.getYear());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, phone, department, year;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            id =  itemView.findViewById(R.id.tvId);
            phone = itemView.findViewById(R.id.tvPhone);
            department = itemView.findViewById(R.id.tvDepartment);
            year = itemView.findViewById(R.id.tvYear);
        }
    }
}
