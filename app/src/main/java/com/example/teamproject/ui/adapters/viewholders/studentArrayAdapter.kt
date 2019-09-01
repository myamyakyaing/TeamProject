package com.example.teamproject.ui.adapters.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.text_spinner.view.*

class studentArrayAdapter  (var context: Context, var list:ArrayList<String>, var layout:Int): BaseAdapter() {
    override fun getView(p0: Int, p1: View?, vg: ViewGroup?): View {
        var inflater = LayoutInflater.from(context)
        var view = inflater.inflate(layout,vg,false)
        view.txtSpinner.text = list[p0]
        return view
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.count()
    }

}