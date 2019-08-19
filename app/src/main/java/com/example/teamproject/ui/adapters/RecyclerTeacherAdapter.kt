package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.ui.adapters.viewholders.TeacherViewholder
import com.example.teamproject.models.Teacher

class RecyclerTeacherAdapter (var list: List<Teacher>, private val onClickItem : (teacher:Teacher) -> Unit):
    RecyclerView.Adapter<TeacherViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_teacher, parent,false)
        return TeacherViewholder(view, onClickItem)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: TeacherViewholder, position: Int) {
        holder.setData(list[position])
    }

}