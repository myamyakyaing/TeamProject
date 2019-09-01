package com.example.teamproject.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.CourseDetail
import kotlinx.android.synthetic.main.recycler_course_detail.view.*

class CourseDetailViewHolder(
    val view: View, private val onClick: (courseList: CourseDetail) -> Unit,
    private val onLongClick: (courseList: CourseDetail) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun setData(courseList: CourseDetail) {
        view.apply {
            txtTitle.text = courseList.courseName.toString()
            txtStartDate.text = courseList.startDate.toString()
            txtEndDate.text = courseList.endDate.toString()
            txtBatch.text = courseList.batch!!.batchName
            txtTeachingDay.text = courseList.teachingDay
        }
        view.setOnClickListener { onClick(courseList) }
        view.setOnLongClickListener {
            onLongClick(courseList)
            true
        }
    }
}

