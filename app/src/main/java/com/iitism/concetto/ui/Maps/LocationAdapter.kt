package com.iitism.concetto.ui.Maps

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R


class LocationAdapter(private val locationList: List<LocationDataModel>): RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val location_tv = itemView.findViewById<TextView>(R.id.location_tv)
        val button: Button = itemView.findViewById(R.id.btn_getlocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_list, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val location = locationList[position]
        holder.location_tv.text = location.locationname
        holder.button.setOnClickListener {
            if (location.id == 1) {

            } else if (location.id == 2) {

            } else if (location.id == 3) {

            } else if (location.id == 4) {

            } else if (location.id == 5) {

            } else if (location.id == 6) {

            } else if (location.id == 7) {

            } else if (location.id == 8) {

            } else if (location.id == 9) {

            } else if (location.id == 10) {

            } else if (location.id == 11) {

            } else if (location.id == 12) {

            }
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

}