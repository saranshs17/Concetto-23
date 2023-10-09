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
        val nameOnLeft : TextView = timelineView.findViewById(R.id.event_onLeft)
        val nameOnRight : TextView = timelineView.findViewById(R.id.event_onRight)
        val dateOnRight : TextView = timelineView.findViewById(R.id.date_onRight)
        val dateOnLeft : TextView = timelineView.findViewById(R.id.date_onLeft)
        val venueOnLeft : TextView = timelineView.findViewById(R.id.venue_onLeft)
        val venueOnRight : TextView = timelineView.findViewById(R.id.venue_onRight)
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
        var venue = currentItem.venue
        val date = currentItem.date
        if(venue.isNotEmpty()) venue = "-"+ venue
        val time = currentItem.time
        val name = currentItem.event
        if(position%2==0){
            holder.timeOnRight.visibility = GONE
            holder.timeOnLeft.visibility= VISIBLE
            holder.timeOnLeft.text= time

            holder.nameOnLeft.visibility= GONE
            holder.nameOnRight.visibility= VISIBLE
            holder.nameOnRight.text = name

            holder.dateOnRight.visibility= GONE
            holder.dateOnLeft.visibility= VISIBLE
            holder.dateOnLeft.text=date

            holder.venueOnLeft.visibility= GONE
            holder.venueOnRight.visibility= VISIBLE
            holder.venueOnRight.text= venue
        }
        else{
            holder.nameOnRight.visibility= GONE
            holder.nameOnLeft.visibility = VISIBLE
            holder.nameOnLeft.text= name

            holder.timeOnLeft.visibility= GONE
            holder.timeOnRight.visibility= VISIBLE
            holder.timeOnRight.text= time

            holder.dateOnRight.visibility= VISIBLE
            holder.dateOnLeft.visibility= GONE
            holder.dateOnRight.text=date

            holder.venueOnLeft.visibility= VISIBLE
            holder.venueOnRight.visibility= GONE
            holder.venueOnLeft.text= venue
        }
    }
}