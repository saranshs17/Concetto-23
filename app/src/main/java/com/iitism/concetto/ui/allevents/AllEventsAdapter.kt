package com.iitism.concetto.ui.allevents

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iitism.concetto.R
import com.iitism.concetto.ui.allevents.retrofit.AllEventsDataModel
import com.iitism.concetto.ui.singleevent.ViewerActivity

// import org.chromium.net.NetworkException

class AllEventsAdapter: RecyclerView.Adapter<AllEventsAdapter.MyViewHolder>() {


    private var eventList = mutableListOf<AllEventsDataModel>()
    // private lateinit var context: Context
    //var organizerList = mutableListOf<AboutUsModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun seteventList(eventList: LiveData<List<AllEventsDataModel>?>){
        try {
            this.eventList = eventList.value!!.toMutableList()
            Log.d("tag","eventList --> ${this.eventList}")
            notifyDataSetChanged()
            Log.d("tag","notified the data set changed")
        }
        catch (e: Exception){
            Log.i("errorInRetrieval","error in retrieval")
        }

    }



    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var eventIV: ImageView =itemView.findViewById(R.id.event_img)
        var eventName: TextView =itemView.findViewById(R.id.event_tv)
        var clubname: TextView =itemView.findViewById(R.id.clubname_tv)
        var venue: TextView =itemView.findViewById(R.id.tv_venue)
        var prize: TextView =itemView.findViewById(R.id.tv_prize)
        val buttonViewMore = itemView.findViewById<Button>(R.id.btn_viewmore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.event_list_item, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = eventList[position]
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
            if(stages.isNotEmpty()) {
                holder.venue.text = stages[0].venue
            }

            holder.buttonViewMore.setOnClickListener {

                if(event._id != null) {
                    val intent = Intent(holder.itemView.context, ViewerActivity::class.java)
                    Log.i("id",event._id)
                    intent.putExtra("eventID", event._id)
                    holder.itemView.context.startActivity(intent)
                }
                else
                    Log.i("intent","No id")
            }
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun searchDataList(searchList: ArrayList<AllEventsDataModel>) {
        eventList = searchList
        notifyDataSetChanged()
    }


}