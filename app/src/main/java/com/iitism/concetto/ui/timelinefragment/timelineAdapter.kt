package com.iitism.concetto.ui.timelinefragment

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class timelineAdapter(private val dataList : List<timilineDataModel>): RecyclerView.Adapter<timelineAdapter.MyViewHolder>() {
    class MyViewHolder(timelineView: View): RecyclerView.ViewHolder(timelineView) {
        val timeOnLeft: TextView = timelineView.findViewById(R.id.time_onLeft)
        val timeOnRight : TextView = timelineView.findViewById(R.id.time_onRight)
        val nameOnLeft : TextView = timelineView.findViewById(R.id.name_onLeft)
        val nameOnRight : TextView = timelineView.findViewById(R.id.name_onRight)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val timelineView = LayoutInflater.from(parent.context).inflate(
            R.layout.timeline_card,parent,false
        )
        return MyViewHolder(timelineView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        val time = currentItem.time+ "\n" + currentItem.date
        val name = currentItem.event+ "\n-"+ currentItem.venue
        if(position%2==0){
            holder.timeOnRight.visibility = GONE
            holder.timeOnLeft.visibility= VISIBLE
            holder.timeOnLeft.text= time
            holder.nameOnLeft.visibility= GONE
            holder.nameOnRight.visibility= VISIBLE
            holder.nameOnRight.text = name
        }
        else{
            holder.nameOnRight.visibility= GONE
            holder.nameOnLeft.visibility = VISIBLE
            holder.nameOnLeft.text= name
            holder.timeOnLeft.visibility= GONE
            holder.timeOnRight.visibility= VISIBLE
            holder.timeOnRight.text= time
        }
    }
}