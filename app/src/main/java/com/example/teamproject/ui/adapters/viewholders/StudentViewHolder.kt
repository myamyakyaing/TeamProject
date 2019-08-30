package com.example.teamproject.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.StudentList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_student_profile.view.*
import kotlinx.android.synthetic.main.rv_student.view.*

class StudentViewHolder(
    val view: View,
    private val onClick: (student: StudentList) -> Unit,
    private val onLongClick: (student: StudentList) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun setData(student: StudentList) {
        view.apply {
            Picasso.get().load(student.imageLink).into(view.imgRvStudent)
            txtStudentName.text = student.name.toString()
            txtStudentTrack.text = student.track!!.trackName.toString()
        }
        view.setOnClickListener { onClick(student) }
        view.setOnLongClickListener {
            onLongClick(student)
            true
        }
    }
}


