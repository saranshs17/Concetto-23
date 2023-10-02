package com.iitism.concetto.ui.singleevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class RulesAdapter(private val ruleList : ArrayList<String>) : RecyclerView.Adapter<RulesAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val eventView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_rules_fragment,
            parent,false
        )
        return MyViewHolder(eventView)
    }

    override fun getItemCount(): Int {
        return ruleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = ruleList[position]
        holder.rule.text = currentitem

    }

    class MyViewHolder(ruleView : View) : RecyclerView.ViewHolder(ruleView){

        val rule : TextView = ruleView.findViewById(R.id.tvRules)
    }
}


