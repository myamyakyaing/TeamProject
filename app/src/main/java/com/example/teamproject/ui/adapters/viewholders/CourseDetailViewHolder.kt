package com.example.teamproject.ui.adapters.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.Course
import com.example.teamproject.models.CourseDetail
import kotlinx.android.synthetic.main.recycler_course_detail.view.*
import kotlinx.android.synthetic.main.rv_course.view.*

class CourseDetailViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun setData(courseList: CourseDetail) {
        view.apply {
            txtTitle.text = courseList.courseName.toString()
            txtStartDate.text = courseList.startDate.toString()
            txtEndDate.text = courseList.endDate.toString()
            txtBatch.text = courseList.batch!!.batchName
            txtTeachingDay.text = courseList.teachingDay
        }
    }
}

