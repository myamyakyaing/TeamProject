package com.example.teamproject.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.R
import com.example.teamproject.models.Admin
import com.example.teamproject.models.Trainer
import com.example.teamproject.ui.adapters.viewholders.AdminViewHolder
import kotlinx.android.synthetic.main.activity_admin_user_list.view.*

class RecyclerAdminAdapter : RecyclerView.Adapter<AdminViewHolder>() {
    var adminList: List<Trainer> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_admin_list, parent, false)
        return AdminViewHolder(view)
    }

    override fun getItemCount(): Int {
        return adminList.size
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.setData(adminList[position])
    }
    fun setAdminListItems(adminList: List<Trainer>){
        this.adminList = adminList
        notifyDataSetChanged()
    }

}