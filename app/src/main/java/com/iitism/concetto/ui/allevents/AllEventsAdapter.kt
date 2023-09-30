package com.iitism.concetto.ui.allevents

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.concetto.R
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.allevents.retrofit.Stage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.chromium.net.NetworkException
import retrofit2.Call
import retrofit2.Response

class AllEventsAdapter(private val eventList: MutableLiveData<List<AllEventsDataModel>?>): RecyclerView.Adapter<AllEventsAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var eventIV: ImageView =itemView.findViewById(R.id.event_img)
        var eventName: TextView =itemView.findViewById(R.id.event_tv)
        var clubname: TextView =itemView.findViewById(R.id.clubname_tv)
        var venue: TextView =itemView.findViewById(R.id.tv_venue)
        var prize: TextView =itemView.findViewById(R.id.tv_prize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.event_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = eventList.value?.get(position)
        obj.let {
            event ->

            Glide.with(holder.itemView.context)
                .load(event?.posterMobile) // Replace with the URL or resource for the image
                .placeholder(R.drawable.concetto_full_logo) // Optional placeholder drawable
                .error(R.drawable.concetto_full_logo) // Optional error drawable
                .into(holder.eventIV)

            holder.eventName.text = event?.name
            holder.clubname.text = event?.oraganizer
            holder.prize.text = event?.prizes
            val stages : List<com.iitism.concetto.ui.clubevents.Stage>? = event?.stages
            holder.venue.text = stages?.get(0)?.venue ?: "NLHC"
        }
    }

    override fun getItemCount(): Int {
        Log.i("size",(eventList.value?.size ?: 0).toString())
        return  eventList.value?.size ?: 0  // put size of number of events
    }
}

