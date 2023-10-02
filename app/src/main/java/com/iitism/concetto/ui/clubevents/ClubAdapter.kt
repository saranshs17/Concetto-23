package com.iitism.concetto.ui.clubevents

import com.iitism.concetto.ui.allevents.AllEventsAdapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.concetto.R
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import java.util.Objects

class ClubAdapter: RecyclerView.Adapter<ClubAdapter.MyViewHolder>() {
    private var ceventList = mutableListOf<Club_dataclass>()


    @SuppressLint("NotifyDataSetChanged")
    fun seteventList(ceventList: LiveData<List<Club_dataclass>?>){
        this.ceventList = ceventList.value!!.toMutableList()
        Log.d("tag","eventList --> ${this.ceventList}")
        notifyDataSetChanged()
        Log.d("tag","notified the data set changed")
    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var eventIV: ImageView =itemView.findViewById(R.id.event_img)
        var eventName: TextView =itemView.findViewById(R.id.event_tv)
        var clubname: TextView =itemView.findViewById(R.id.clubname_tv)
        var venue: TextView =itemView.findViewById(R.id.tv_venue)
        var prize: TextView =itemView.findViewById(R.id.tv_prize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = ceventList[position]
        obj.let {
                event ->

            Glide.with(holder.itemView.context)
                .load(event.posterMobile) // Replace with the URL or resource for the image
                .placeholder(R.drawable.concetto_full_logo) // Optional placeholder drawable
                .error(R.drawable.concetto_full_logo) // Optional error drawable
                .centerCrop()
                .into(holder.eventIV)

            holder.eventName.text = event.name
            if(event.organizer != null)
                holder.clubname.text = event.organizer
            else
                holder.clubname.text = "Club Name"

            holder.prize.text = event.prizes
            val stages : List<com.iitism.concetto.ui.clubevents.Stage> = event.stages
            if(stages.size > 0)
                holder.venue.text = stages[0].venue
        }
    }

    override fun getItemCount(): Int {
        return ceventList.size
    }


}


