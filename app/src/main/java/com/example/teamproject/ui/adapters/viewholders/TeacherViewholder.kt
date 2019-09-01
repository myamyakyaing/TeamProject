package com.example.teamproject.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.Trainer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_teacher.view.*

class TeacherViewholder(
    val view: View, private val onClick: (trainer: Trainer) -> Unit,
    private val onLongClick: (trainer: Trainer) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun setData(trainer: Trainer) {
        view.apply {
            Picasso.get().load(trainer.imageLink).into(view.imgRvTeacher)
            txtTeacherName.text = trainer.name
            txtTeacherPos.text = trainer.track!!.trackName
        }
        view.setOnClickListener { onClick(trainer) }
        view.setOnLongClickListener {
            onLongClick(trainer)
            true
        }
    }
}