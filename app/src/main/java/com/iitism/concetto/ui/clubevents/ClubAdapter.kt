package com.iitism.concetto.ui.clubevents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.concetto.R
import java.util.Objects

class ClubAdapter(private val eventList:List<Club_dataclass>): RecyclerView.Adapter<ClubAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var eventIV:ImageView=itemView.findViewById(R.id.event_img)
        var eventName:TextView=itemView.findViewById(R.id.event_tv)
        var clubname:TextView=itemView.findViewById(R.id.clubname_tv)
        var venue:TextView=itemView.findViewById(R.id.tv_venue)
        var price:TextView=itemView.findViewById(R.id.tv_price)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.event_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = eventList[position]
        Glide.with(holder.itemView.context)
            .load(eventList[position].posterMobile) // Replace with the URL or resource for the image
            .placeholder(R.drawable.concetto_full_logo) // Optional placeholder drawable
            .error(R.drawable.concetto_full_logo) // Optional error drawable
            .into(holder.eventIV)

        holder.eventName.text=obj.name
        holder.clubname.text=obj.oraganizer
        holder.price.text=obj.prizes
    }

    override fun getItemCount(): Int {
        return  eventList.size  // put size of number of events
    }
}


