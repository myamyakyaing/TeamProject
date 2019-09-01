package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.models.Evaluation
import com.example.teamproject.ui.adapters.viewholders.EvaluationViewHolder

class EvaluationListAdapter(private val onClick: (evaluation: Evaluation) -> Unit, private val onLongClick: (evaluation: Evaluation) -> Unit)  : RecyclerView.Adapter<EvaluationViewHolder>() {
    var evaluateList: List<Evaluation> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvaluationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_evaluation_list, parent, false)
        return EvaluationViewHolder(view,onClick,onLongClick)
    }

    override fun getItemCount(): Int {
        return evaluateList.size
    }

    override fun onBindViewHolder(holder: EvaluationViewHolder, position: Int) {
        holder.setData(evaluateList[position])
    }
    fun setTeamListItems(evaluationList: List<Evaluation>){
        this.evaluateList = evaluationList
        notifyDataSetChanged()
    }

}