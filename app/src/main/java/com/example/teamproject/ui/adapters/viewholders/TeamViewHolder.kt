package com.example.teamproject.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.Team
import kotlinx.android.synthetic.main.recycler_team_list.view.*

class TeamViewHolder(val view: View, private val onLongClick: (team: Team) -> Unit): RecyclerView.ViewHolder(view) {
    fun setData(team: Team) {
        view.apply {
            t_txtBatch.text = team.batch.batchName
            txtTeamName.text = team.teamName
            txtProjectName.text = team.projectName
            t_txtStartDate.text = team.startDate
            t_txtEndDate.text = team.endDate
            txtMember.text = team.student.name
        }
        view.setOnLongClickListener {
            onLongClick(team)
            true
        }
    }
}