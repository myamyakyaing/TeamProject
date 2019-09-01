package com.example.teamproject.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.Evaluation
import kotlinx.android.synthetic.main.recycler_evaluation_list.view.*

class EvaluationViewHolder(
    val view: View, private val onClick: (evaluation: Evaluation) -> Unit,
    private val onLongClick: (evaluation: Evaluation) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun setData(evaluation: Evaluation) {
        view.apply {
            txt_ev_name.text = evaluation.student!!.name
            txt_ev_track.text = evaluation.track!!.trackName
            txt_ev_batch.text = evaluation.batch!!.batchName
            txt_ev_soft.text = evaluation.softskill
            txt_ev_hard.text = evaluation.hardskill
            txt_ev_rule.text = evaluation.rule
        }
        view.setOnClickListener { onClick(evaluation) }
        view.setOnLongClickListener {
            onLongClick(evaluation)
            true
        }
    }
}