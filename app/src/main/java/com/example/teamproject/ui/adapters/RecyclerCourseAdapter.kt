package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.ui.adapters.viewholders.CourseViewholder
import com.example.teamproject.R
import com.example.teamproject.models.Course

class RecyclerCourseAdapter(var list: List<Course>, private val onClickItem : (course: Course) -> Unit):
    RecyclerView.Adapter<CourseViewholder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_course,parent,false)
        return CourseViewholder(view, onClickItem)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: CourseViewholder, position: Int) {
        holder.setData(list[position])
    }
}