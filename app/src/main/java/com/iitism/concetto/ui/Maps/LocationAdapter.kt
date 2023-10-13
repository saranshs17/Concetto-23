package com.iitism.concetto.ui.Maps

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R


class LocationAdapter(private val act : Context,private val locationList: List<LocationDataModel>): RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {

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
                Toast.makeText(holder.itemView.context, "Id = 1", Toast.LENGTH_SHORT).show()
            } else if (location.id == 2) {
                Toast.makeText(holder.itemView.context, "Id = 2", Toast.LENGTH_SHORT).show()
            } else if (location.id == 3) {
                Toast.makeText(holder.itemView.context, "Id = 3", Toast.LENGTH_SHORT).show()
            } else if (location.id == 4) {
                Toast.makeText(holder.itemView.context, "Id = 4", Toast.LENGTH_SHORT).show()
            } else if (location.id == 5) {
                Toast.makeText(holder.itemView.context, "Id = 5", Toast.LENGTH_SHORT).show()
            } else if (location.id == 6) {
                Toast.makeText(holder.itemView.context, "Id = 6", Toast.LENGTH_SHORT).show()
            } else if (location.id == 7) {
                Toast.makeText(holder.itemView.context, "Id = 7", Toast.LENGTH_SHORT).show()
            } else if (location.id == 8) {
                Toast.makeText(holder.itemView.context, "Id = 8", Toast.LENGTH_SHORT).show()
            } else if (location.id == 9) {
                Toast.makeText(holder.itemView.context, "Id = 9", Toast.LENGTH_SHORT).show()
            } else if (location.id == 10) {
                Toast.makeText(holder.itemView.context, "Id = 10", Toast.LENGTH_SHORT).show()
            } else if (location.id == 11) {
                Toast.makeText(holder.itemView.context, "Id = 11", Toast.LENGTH_SHORT).show()
            } else if (location.id == 12) {
                Toast.makeText(holder.itemView.context, "Id = 12", Toast.LENGTH_SHORT).show()
            }




            val intent  = Intent(holder.itemView.context,MapsActivity::class.java)
            intent.putExtra("destLat",location.latitude)
            intent.putExtra("destLng",location.longitude)
            intent.putExtra("locationName",location.locationname)
            holder.itemView.context.startActivity(intent)


        }
    }


    override fun getItemCount(): Int {
        return locationList.size
    }

}