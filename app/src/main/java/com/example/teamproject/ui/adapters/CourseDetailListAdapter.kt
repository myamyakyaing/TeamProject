package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.models.Course
import com.example.teamproject.models.CourseDetail
import com.example.teamproject.ui.adapters.viewholders.CourseDetailViewHolder
import kotlinx.android.synthetic.main.activity_course_detail.view.*

class CourseDetailListAdapter : RecyclerView.Adapter<CourseDetailViewHolder>() {
    var courseDetailList: List<CourseDetail> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_course_detail, parent, false)
        return CourseDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseDetailList.size
    }

    override fun onBindViewHolder(holder: CourseDetailViewHolder, position: Int) {
        holder.setData(courseDetailList[position])
    }
    fun setCourseDetailListItems(courseDetailList: List<CourseDetail>){
        this.courseDetailList = courseDetailList
        notifyDataSetChanged()
    }

}