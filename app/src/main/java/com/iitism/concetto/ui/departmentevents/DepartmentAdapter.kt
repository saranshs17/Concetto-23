package com.iitism.concetto.ui.departmentevents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class DepartmentAdapter(val posterMobile: String,
                        val posterWeb: String,
                        val name: String,
                        val mode: String,
                        val descriptionEvent: String,
                        val descriptionOrganizer: String,
                        val type: Int,
                        val organizer: String,
                        val rules: List<String>,
                        val prizes: String,
                        val contacts: List<String>,
                        val fees: Int,
                        val pdfLink: String,
                        val minTeamSize: Int,
                        val maxTeamSize: Int,
                        val problemStatements: String,
                        val extraDetails: List<String>,
                        val teams: List<String>,
                        val stages: List<String>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.event_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 40    // put size of number of events
    }
}

class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

}
