package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.models.Team
import com.example.teamproject.ui.adapters.viewholders.TeamViewHolder
import kotlinx.android.synthetic.main.activity_student_list.*

class TeamListAdapter(private val onLongClick: (team : Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {
    var cooperateList: List<Team> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_team_list, parent, false)
        return TeamViewHolder(view, onLongClick)
    }

    override fun getItemCount(): Int {
        return cooperateList.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.setData(cooperateList[position])
    }
    fun setTeamListItems(teamList: List<Team>){
        this.cooperateList = teamList
        notifyDataSetChanged()
    }

}