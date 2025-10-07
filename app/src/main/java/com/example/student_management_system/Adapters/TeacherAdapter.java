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

import com.example.student_management_system.Database.Models.TeacherModel;
import com.example.student_management_system.Admin.ManageTeacherActivity;
import com.example.student_management_system.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private Context context;
    private List<TeacherModel> list;

    public TeacherAdapter(Context context, List<TeacherModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TeacherAdapter.TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teacher_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherAdapter.TeacherViewHolder holder, int position) {
        TeacherModel teacherModel = list.get(position);

        holder.name.setText("Name : " + teacherModel.getName());
        holder.id.setText("Id : " + teacherModel.getTeacherId());
        holder.phone.setText("Phone : " + teacherModel.getPhone());
        holder.department.setText("Department : " + teacherModel.getDepartment());

        Log.d("Adapter", "Teacher ID = " + teacherModel.getId());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Manage Teacher", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ManageTeacherActivity.class);
            intent.putExtra("id", teacherModel.getId());
            intent.putExtra("name", teacherModel.getName());
            intent.putExtra("teacherId", teacherModel.getTeacherId());
            intent.putExtra("phone", teacherModel.getPhone());
            intent.putExtra("department", teacherModel.getDepartment());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, phone, department;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            id = itemView.findViewById(R.id.tvId);
            phone = itemView.findViewById(R.id.tvPhone);
            department = itemView.findViewById(R.id.tvDepartment);
        }
    }
}
