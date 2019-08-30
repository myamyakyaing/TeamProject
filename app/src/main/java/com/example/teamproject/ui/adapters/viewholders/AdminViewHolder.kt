package com.example.teamproject.ui.adapters.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.models.Admin
import com.example.teamproject.models.Trainer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_admin_list.view.*

class AdminViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun setData(admin: Trainer) {
        view.apply {
            Picasso.get().load(admin.imageLink).into(view.imgAdminProfile)
            adminEmail.text = admin.email.toString()
            adminoffice.text = admin.company

        }
    }
}