package com.iitism.concetto.ui.singleevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class extraDetailsAdapter(private val extraDetailsArrayPair : ArrayList<Pair<ArrayList<String>,String>>) : RecyclerView.Adapter<extraDetailsAdapter.MyViewHolder>() {
    class MyViewHolder (View : View) : RecyclerView.ViewHolder(View){
        val extraKey : TextView = View.findViewById(R.id.tv_extraDetailsKey)
        val extraValue : TextView = View.findViewById(R.id.tv_extraDetailsValue)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): extraDetailsAdapter.MyViewHolder {
        val extraDetailView = LayoutInflater.from(parent.context).inflate(
            R.layout.extra_details_card,parent,false
        )
        return MyViewHolder(extraDetailView)

    }

    override fun onBindViewHolder(holder: extraDetailsAdapter.MyViewHolder, position: Int) {
        val currentItem = extraDetailsArrayPair[position]
        var integer = 1
        var text =""
        for(i in currentItem.first){
            text+=(integer++.toString()+")"+i+"\n")
        }
        holder.extraKey.text = currentItem.second + " :"
        holder.extraValue.text = text
    }

    override fun getItemCount(): Int {
        return extraDetailsArrayPair.size
    }

}