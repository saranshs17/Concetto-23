package com.iitism.concetto.ui.singleevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class StagesAdapter(private  val StagesArrayPair: ArrayList<Pair<String,Pair<String,String>>>): RecyclerView.Adapter<StagesAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val stageView = LayoutInflater.from(parent.context).inflate(
            R.layout.stages_card,parent,false
        )
        return MyViewHolder(stageView)
    }

    override fun getItemCount(): Int {
        return StagesArrayPair.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = StagesArrayPair[position]
        holder.stageNo.text = "Stage ${position+1} :"
        holder.venueStage.text = currentItem.second.second
        holder.timing.text= currentItem.second.first
        holder.description.text = currentItem.first

    }

    class MyViewHolder(View : View) : RecyclerView.ViewHolder(View){
        val stageNo : TextView = View.findViewById(R.id.tv_stageNo)
        val description : TextView = View.findViewById(R.id.tv_StageDescription)
        val timing : TextView = View.findViewById(R.id.tv_stageTiming)
        val venueStage : TextView = View.findViewById(R.id.tv_StageVenue)
    }
}