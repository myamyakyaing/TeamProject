package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.models.StudentList
import com.example.teamproject.ui.adapters.viewholders.StudentViewHolder

class StudentListAdapter(private val onClick: (student: StudentList) -> Unit,private val onLongClick: (student: StudentList) -> Unit)
    : RecyclerView.Adapter<StudentViewHolder>() {
    var studentList : List<StudentList> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_student,parent,false)
        return StudentViewHolder(view,onClick,onLongClick)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
       holder.setData(studentList[position])
    }

    fun setStudentListItems(studentList: List<StudentList>){
        this.studentList = studentList
        notifyDataSetChanged()
    }


}