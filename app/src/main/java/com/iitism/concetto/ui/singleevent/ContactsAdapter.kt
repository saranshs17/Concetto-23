package com.iitism.concetto.ui.singleevent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iitism.concetto.R

class ContactsAdapter(private val contactsArrayPair : ArrayList<Pair<String,String>>) : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val contactView = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_details,parent,false
        )
        return MyViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return  contactsArrayPair.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = contactsArrayPair[position]
        holder.contactName.text= currentItem.first
        holder.contactNumber.text= currentItem.second
    }

    class MyViewHolder(View : View) : RecyclerView.ViewHolder(View){
        val contactName : TextView = View.findViewById(R.id.tv_contactName)
        val contactNumber : TextView = View.findViewById(R.id.tv_ContactNumber)
    }
}