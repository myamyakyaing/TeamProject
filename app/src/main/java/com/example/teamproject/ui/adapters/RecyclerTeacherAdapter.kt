package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.models.Trainer
import com.example.teamproject.ui.adapters.viewholders.TeacherViewholder

class RecyclerTeacherAdapter (private val onClickItem : (trainer:Trainer) -> Unit,private val onLongClick: (trainer: Trainer) -> Unit):
    RecyclerView.Adapter<TeacherViewholder>() {
    var trainerList : List<Trainer> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.rv_teacher, parent,false)
        return TeacherViewholder(view, onClickItem,onLongClick)
    }

    override fun getItemCount(): Int {
        return trainerList.count()
    }

    override fun onBindViewHolder(holder: TeacherViewholder, position: Int) {
        holder.setData(trainerList[position])
    }
    fun setTrainerListItems(trainerList: List<Trainer>){
        this.trainerList = trainerList
        notifyDataSetChanged()
    }


}